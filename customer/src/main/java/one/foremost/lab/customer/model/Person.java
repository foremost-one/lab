/**
 * 
 */
package one.foremost.lab.customer.model;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author marcelo
 *
 */
public class Person {
	
	private final UUID id;
	@NotBlank
	private final String name;
	
	public Person (@JsonProperty("id") UUID id,
			@JsonProperty("name") String name) {
		this.id = id;
		this.name = name;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
