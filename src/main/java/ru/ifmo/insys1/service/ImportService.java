package ru.ifmo.insys1.service;

import ru.ifmo.insys1.request.ImportRequest;
import ru.ifmo.insys1.response.ImportResponse;

import java.util.List;

public interface ImportService {

    void persist(ImportRequest request);

    List<ImportResponse> getAll();
}
