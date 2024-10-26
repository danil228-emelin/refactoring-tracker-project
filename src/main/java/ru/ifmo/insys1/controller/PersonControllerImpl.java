package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.api.PersonController;
import ru.ifmo.insys1.request.PersonRequest;
import ru.ifmo.insys1.response.PersonResponse;
import ru.ifmo.insys1.service.MovieService;
import ru.ifmo.insys1.service.PersonService;

import java.util.List;

import static jakarta.ws.rs.core.Response.Status.CREATED;

@ApplicationScoped
public class PersonControllerImpl implements PersonController {

    @Inject
    private PersonService personService;

    @Inject
    private MovieService movieService;

    @Override
    public Response getPerson(Long id) {
        PersonResponse personDTO = personService.getPerson(id);

        return Response.ok(personDTO).build();
    }

    @Override
    public Response getAllPersons(int page, int size) {
        List<PersonResponse> persons = personService.getAllPersons(page, size);

        return Response.ok(persons).build();
    }

    @Override
    public Response createPerson(PersonRequest person) {
        PersonResponse createdPerson = personService.createPerson(person);

        return Response.status(CREATED)
                .entity(createdPerson)
                .build();
    }

    @Override
    public Response updatePerson(Long id, PersonRequest person) {
        PersonResponse updated = personService.updatePerson(id, person);

        return Response.ok(updated).build();
    }

    @Override
    public Response deletePerson(Long id) {
        personService.deletePerson(id);

        return Response.noContent()
                .build();
    }

    @Override
    public Response getOperatorsWithoutOscar() {
        List<PersonResponse> operatorsWithoutOscar = movieService.getOperatorsWithoutOscar();

        return Response.ok(operatorsWithoutOscar)
                .build();
    }
}
