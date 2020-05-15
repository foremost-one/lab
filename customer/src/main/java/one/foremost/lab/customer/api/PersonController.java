/**
 * 
 */
package one.foremost.lab.customer.api;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import one.foremost.lab.customer.model.Person;
import one.foremost.lab.customer.service.PersonService;

/**
 * @author marcelo
 *
 */
@RequestMapping("api/v1/persons")
@RestController
@Api(value = "Endpoints to manage Person")
public class PersonController {

	private PersonService personService;

	@Autowired
	public PersonController(PersonService personService) {
		this.personService = personService;
	}

	/*
	 * @param person The person object containing the Person information to be inserted into the application
	 */
	@PostMapping
	public void addPerson(@Valid @NonNull @RequestBody Person person) {
		this.personService.addPerson(person);
	}

	/**
	 * @return
	 */
	@ApiOperation( value = "Return a list of people", response = Person[].class)
	@GetMapping
	public List<Person> getAllPeople() {
		return this.personService.getAllPeople();
	}

	/**
	 * @param id
	 * @return
	 */
	@GetMapping(path = "{id}")
	public Person getPersonById(@PathVariable("id") UUID id) {
		return this.personService.getPersonById(id).orElse(null);
	}

	/**
	 * @param id
	 */
	@DeleteMapping(path = "{id}")
	public void deletePersonById(@PathVariable("id") UUID id) {
		this.personService.deletePerson(id);
	}

	/**
	 * @param id
	 * @param personToUpdate
	 */
	@PutMapping(path = "{id}")
	public void updatePerson(@PathVariable("id") UUID id, @Valid @NonNull @RequestBody Person personToUpdate) {
		this.personService.updatePerson(id, personToUpdate);
	}

}
