package ru.ifmo.insys1.service;

import ru.ifmo.insys1.response.ApplicationDTO;

import java.util.List;

public interface AdminService {

    void submitApplication();

    void acceptApplication(ApplicationDTO applicationDTO);

    List<ApplicationDTO> getAllApplications(int page, int size);
}
