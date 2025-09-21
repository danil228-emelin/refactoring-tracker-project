package ru.ifmo.insys1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import ru.ifmo.insys1.dao.PersonDAO;
import ru.ifmo.insys1.entity.Location;
import ru.ifmo.insys1.entity.Person;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.request.PersonRequest;
import ru.ifmo.insys1.response.PersonResponse;
import ru.ifmo.insys1.security.SecurityManager;
import ru.ifmo.insys1.service.LocationService;
import ru.ifmo.insys1.service.PersonService;

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

    @Inject
    private LocationService locationService;

    @Override
    public PersonResponse getPerson(Long id) {
        Optional<Person> optionalPerson = personDAO.findById(id);

        if (optionalPerson.isEmpty()) {
            throw new ServiceException(NOT_FOUND, "Person not found");
        }

        return modelMapper.map(optionalPerson.get(), PersonResponse.class);
    }

    @Override
    public List<PersonResponse> getAllPersons(int page, int size) {
        List<Person> allPersons = personDAO.findAll(page, size);

        return allPersons.stream()
                .map(p -> modelMapper.map(p, PersonResponse.class))
                .toList();
    }

    @Override
    @Transactional
    public PersonResponse createPerson(PersonRequest personDTO) {
        Person person = modelMapper.map(personDTO, Person.class);

        Location location = modelMapper.map(
                locationService.getLocation(personDTO.getLocation()),
                Location.class
        );
        person.setLocation(location);

        personDAO.save(person);

        return modelMapper.map(person, PersonResponse.class);
    }

    @Override
    @Transactional
    public PersonResponse updatePerson(Long id, PersonRequest personDTO) {
        Optional<Person> optionalPerson = personDAO.findById(id);

        if (optionalPerson.isEmpty()) {
            throw new ServiceException(NOT_FOUND, "Person not found");
        }

        Person person = optionalPerson.get();

        securityManager.throwIfUserHasNotAccessToResource(person.getCreatedBy());

        modelMapper.map(personDTO, person);

        if (personDTO.getLocation() != null) {
            Location location = modelMapper.map(
                    locationService.getLocation(personDTO.getLocation()),
                    Location.class
            );
            person.setLocation(location);
        }

        personDAO.update(person);

        return modelMapper.map(person, PersonResponse.class);
    }

    @Override
    @Transactional
    public void deletePerson(Long id) {
        personDAO.delete(id);
    }

}
