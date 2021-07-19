package one.digitalinovation.personapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;

import one.digitalinovation.personapi.dto.PersonDTO;
import one.digitalinovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinovation.personapi.entity.Person;
import one.digitalinovation.personapi.exception.PersonNotFoundException;
import one.digitalinovation.personapi.mapper.PersonMapper;
import one.digitalinovation.personapi.repository.PersonRepository;

@Service
public class PersonService {
	
	private PersonRepository personRepository;
	private final PersonMapper personMapper = PersonMapper.INSTANCE;
	 
	@Autowired
	public PersonService(PersonRepository personRepository) {	
		this.personRepository = personRepository;
	}	
		
	public MessageResponseDTO createPerson(PersonDTO personDTO) {
		Person personToSave = personMapper.toModel(personDTO);
		Person savePerson = personRepository.save(personToSave);
		return MessageResponseDTO
				.builder()
				.message("Created person with id "+ savePerson.getId())
				.build();
				
	}
	
	public List<PersonDTO> listAll(){
		List<Person> allPeople = personRepository.findAll();
		return allPeople.stream()
				.map(personMapper::toDTO)
				.collect(Collectors.toList());
	}

	public PersonDTO findById(long id) throws PersonNotFoundException {
		Person person = verifyIfExists(id);
			return personMapper.toDTO(person);
		}

	public void deleteById(long id) throws PersonNotFoundException {
		verifyIfExists(id);
		personRepository.deleteById(id);
		
	}
	
	private Person verifyIfExists(long id) throws PersonNotFoundException{
		return personRepository.findById(id)
				.orElseThrow(()-> new PersonNotFoundException(id));
				
				
	}
}
