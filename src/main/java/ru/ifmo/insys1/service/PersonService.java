package ru.ifmo.insys1.service;

import ru.ifmo.insys1.request.PersonRequest;
import ru.ifmo.insys1.response.PersonResponse;

import java.util.List;

public interface PersonService {

    PersonResponse getPerson(Long id);

    List<PersonResponse> getAllPersons(int page, int size);

    PersonResponse createPerson(PersonRequest person);

    PersonResponse updatePerson(Long id, PersonRequest person);

    void deletePerson(Long id);
}
