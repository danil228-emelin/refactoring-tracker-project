package ru.ifmo.insys1.service;

import ru.ifmo.insys1.dto.PersonDTO;

import java.util.List;

public interface PersonService {

    PersonDTO getPerson(Long id);

    List<PersonDTO> getAllPersons(int page, int size);

    PersonDTO createPerson(PersonDTO person);

    void updatePerson(Long id, PersonDTO person);

    void deletePerson(Long id);
}
