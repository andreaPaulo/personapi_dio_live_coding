package one.digitalinovation.personapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import one.digitalinovation.personapi.dto.PersonDTO;
import one.digitalinovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinovation.personapi.exception.PersonNotFoundException;
import one.digitalinovation.personapi.service.PersonService;

@RestController
@RequestMapping("/api/v1/people")
public class PersonController {
	
	PersonService personService;
	
	@Autowired
	public PersonController(PersonService personService) {	
		this.personService = personService;
	}
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MessageResponseDTO createPerson(@RequestBody @Valid PersonDTO personDTO) {
		return personService.createPerson(personDTO);
		
	}
	@GetMapping
	public List<PersonDTO> listAll(){
		return personService.listAll();
	}
	@GetMapping("/{id}")
	public PersonDTO findById(@PathVariable long id) throws PersonNotFoundException {
		return personService.findById(id);		
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable long id) throws PersonNotFoundException{
		 personService.deleteById(id);
	}
	@PutMapping("/{id}")
	public MessageResponseDTO upDateById(@PathVariable long id, @RequestBody @Valid PersonDTO personDTO) throws PersonNotFoundException {
		return personService.upDateById(id, personDTO);
		
	}
	
	
	
}