package one.digitalinovation.personapi.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import one.digitalinovation.personapi.dto.PersonDTO;
import one.digitalinovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinovation.personapi.entity.Person;
import one.digitalinovation.personapi.exception.PersonNotFoundException;
import one.digitalinovation.personapi.mapper.PersonMapper;
import one.digitalinovation.personapi.repository.PersonRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {
	
	private PersonRepository personRepository;
	private final PersonMapper personMapper = PersonMapper.INSTANCE;
	
		
	public MessageResponseDTO createPerson(PersonDTO personDTO) {
		Person personToSave = personMapper.toModel(personDTO);
		Person savePerson = personRepository.save(personToSave);
		return createMessageResponse(savePerson.getId(), "Saved person with id ");
				
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
	

	public MessageResponseDTO upDateById(long id, @Valid PersonDTO personDTO) throws PersonNotFoundException {
		verifyIfExists(id);
		Person personToUpdate = personMapper.toModel(personDTO);
		Person updatePerson = personRepository.save(personToUpdate);
		return createMessageResponse(updatePerson.getId(), "Update person with id ");
	}
	
	private Person verifyIfExists(long id) throws PersonNotFoundException{
		return personRepository.findById(id)
				.orElseThrow(()-> new PersonNotFoundException(id));
				
				
	}

	private MessageResponseDTO createMessageResponse(long id , String message) {
		return MessageResponseDTO
				.builder()
				.message(message + id)
				.build();
	}
}
