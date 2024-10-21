package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.api.PersonController;
import ru.ifmo.insys1.dto.PersonDTO;
import ru.ifmo.insys1.service.PersonService;

import java.util.List;

import static jakarta.ws.rs.core.Response.Status.CREATED;

@ApplicationScoped
public class PersonControllerImpl implements PersonController {

    @Inject
    private PersonService personService;

    @Override
    public Response getPerson(Long id) {
        PersonDTO personDTO = personService.getPerson(id);

        return Response.ok(personDTO).build();
    }

    @Override
    public Response getAllPersons(int page, int size) {
        List<PersonDTO> persons = personService.getAllPersons(page, size);

        return Response.ok(persons).build();
    }

    @Override
    public Response createPerson(PersonDTO person) {
        PersonDTO createdPerson = personService.createPerson(person);

        return Response.status(CREATED)
                .entity(createdPerson)
                .build();
    }

    @Override
    public Response updatePerson(Long id, PersonDTO person) {
        personService.updatePerson(id, person);

        return Response.ok()
                .build();
    }

    @Override
    public Response deletePerson(Long id) {
        personService.deletePerson(id);

        return Response.noContent()
                .build();
    }
}
