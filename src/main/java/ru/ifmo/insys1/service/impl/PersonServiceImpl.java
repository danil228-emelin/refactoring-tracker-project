package ru.ifmo.insys1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import ru.ifmo.insys1.dao.PersonDAO;
import ru.ifmo.insys1.dto.PersonDTO;
import ru.ifmo.insys1.entity.Person;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.security.SecurityManager;
import ru.ifmo.insys1.service.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@ApplicationScoped
public class PersonServiceImpl implements PersonService {

    @Inject
    private PersonDAO personDAO;

    @Inject
    private SecurityManager securityManager;

    @Inject
    private ModelMapper modelMapper;

    @Override
    public PersonDTO getPerson(Long id) {
        Optional<Person> optionalPerson = personDAO.findById(id);

        if (optionalPerson.isEmpty()) {
            throw new ServiceException(NOT_FOUND, "Person not found");
        }

        return modelMapper.map(optionalPerson.get(), PersonDTO.class);
    }

    @Override
    public List<PersonDTO> getAllPersons(int page, int size) {
        List<Person> allPersons = personDAO.findAll(page, size);

        return allPersons.stream()
                .map(p -> modelMapper.map(p, PersonDTO.class))
                .toList();
    }

    @Override
    @Transactional
    public PersonDTO createPerson(PersonDTO personDTO) {
        Person person = modelMapper.map(personDTO, Person.class);

        personDAO.save(person);

        return modelMapper.map(person, PersonDTO.class);
    }

    @Override
    @Transactional
    public void updatePerson(Long id, PersonDTO personDTO) {
        Optional<Person> optionalPerson = personDAO.findById(id);

        if (optionalPerson.isEmpty()) {
            throw new ServiceException(NOT_FOUND, "Person not found");
        }

        Person person = optionalPerson.get();

        securityManager.throwIfUserHasNotAccessToResource(person.getCreatedBy());

        modelMapper.map(personDTO, person);

        personDAO.update(person);
    }

    @Override
    @Transactional
    public void deletePerson(Long id) {
        personDAO.delete(id);
    }
}
