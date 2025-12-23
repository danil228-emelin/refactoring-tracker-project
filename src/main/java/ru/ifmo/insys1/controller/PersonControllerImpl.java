package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import ru.ifmo.insys1.api.PersonController;
import ru.ifmo.insys1.request.PersonRequest;
import ru.ifmo.insys1.response.PersonResponse;
import ru.ifmo.insys1.service.MovieService;
import ru.ifmo.insys1.service.PersonService;

import java.util.List;

import static jakarta.ws.rs.core.Response.Status.CREATED;

@ApplicationScoped
@Slf4j
@Tag(name = "Persons", description = "Управление персонами (режиссёры, операторы и т.д.)")
public class PersonControllerImpl implements PersonController {

    @Inject
    private PersonService personService;

    @Inject
    private MovieService movieService;

    @Override
    @Operation(summary = "Получить персону по ID")
    @APIResponse(responseCode = "200")
    @APIResponse(responseCode = "404", description = "Персона не найдена")
    public Response getPerson(Long id) {
        PersonResponse personDTO = personService.getPerson(id);

        return Response.ok(personDTO).build();
    }

    @Override
    @Operation(summary = "Получить список персон (пагинация)")
    @APIResponse(responseCode = "200")
    public Response getAllPersons(int page, int size) {
        List<PersonResponse> persons = personService.getAllPersons(page, size);

        return Response.ok(persons).build();
    }

    @Override
    @Operation(summary = "Создать персону")
    @APIResponse(responseCode = "201")
    public Response createPerson(PersonRequest person) {
        PersonResponse createdPerson = personService.createPerson(person);

        return Response.status(CREATED)
                .entity(createdPerson)
                .build();
    }

    @Override
    @Operation(summary = "Обновить персону")
    @APIResponse(responseCode = "200")
    @APIResponse(responseCode = "404", description = "Персона не найдена")
    public Response updatePerson(Long id, PersonRequest person) {
        PersonResponse updated = personService.updatePerson(id, person);

        return Response.ok(updated).build();
    }

    @Override
    @Operation(summary = "Удалить персону")
    @APIResponse(responseCode = "204")
    @APIResponse(responseCode = "404", description = "Персона не найдена")
    public Response deletePerson(Long id) {
        personService.deletePerson(id);

        return Response.noContent()
                .build();
    }

    @Override
    @Operation(summary = "Получить операторов без Оскара")
    @APIResponse(responseCode = "200")
    public Response getOperatorsWithoutOscar() {
        List<PersonResponse> operatorsWithoutOscar = movieService.getOperatorsWithoutOscar();

        log.info("Operators without oscar: {}", operatorsWithoutOscar);

        return Response.ok(operatorsWithoutOscar)
                .build();
    }
}
