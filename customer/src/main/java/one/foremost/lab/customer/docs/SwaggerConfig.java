package one.foremost.lab.customer.docs;

import static com.google.common.collect.Lists.newArrayList;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseEntity;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Customer API Service")
                .description("")
                .version("1.0")
                .contact(new Contact("Marcelo Bellezo", "https://lab.foremost.you", "mbellezo@foremost.you"))
                .license("GPL-3.0")
                .licenseUrl("https://github.com/foremost-one/lab/blob/master/LICENSE")
                .build();
    }

    @Bean
    public Docket CustomerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData())
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules( 
                        AlternateTypeRules.newRule(
                            typeResolver.resolve(Map.class, String.class,
                                typeResolver.resolve(Map.class, String.class, typeResolver.resolve(List.class, String.class))),
                            typeResolver.resolve(Map.class, String.class, WildcardType.class), Ordered.HIGHEST_PRECEDENCE))                                
                .useDefaultResponseMessages(false)
//                .globalOperationParameters(Collections.singletonList(new ParameterBuilder()
//                        .name("Authorization")
//                        .description("Bearer token")
//                        .modelRef(new ModelRef("string"))
//                        .parameterType("header")
//                        .required(true)
//                        .build()))
                .securitySchemes(newArrayList(apiKey()))
                .securityContexts(newArrayList(securityContext()))
                .enableUrlTemplating(true)
                .tags(new Tag("Customer API Service", "All apis relating to customers"));
        //          .additionalModels(typeResolver.resolve(AdditionalModel.class)) 
        //          ;
    }

    @Autowired
    private TypeResolver typeResolver;

    private ApiKey apiKey() {
        return new ApiKey("mykey", "api_key", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/anyPath.*"))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
        = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference("mykey", authorizationScopes));
    }
}
