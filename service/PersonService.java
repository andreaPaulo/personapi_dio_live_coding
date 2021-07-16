package one.digitalinovation.personapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import one.digitalinovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinovation.personapi.entity.Person;
import one.digitalinovation.personapi.repository.PersonRepository;

@Service
public class PersonService {
	
	private PersonRepository personRepository;
	
	@Autowired
	public PersonService(PersonRepository personRepository) {	
		this.personRepository = personRepository;
	}	
		
	public MessageResponseDTO createPerson(Person person) {
		Person savePerson = personRepository.save(person);
		return MessageResponseDTO
				.builder()
				.message("Created person with id "+ savePerson.getId())
				.build();
				
	}
}
