package ru.ifmo.insys1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import ru.ifmo.insys1.dao.ImportDAO;
import ru.ifmo.insys1.entity.Import;
import ru.ifmo.insys1.request.ImportRequest;
import ru.ifmo.insys1.response.ImportResponse;
import ru.ifmo.insys1.service.ImportService;

import java.util.List;

@ApplicationScoped
public class ImportServiceImpl implements ImportService {

    @Inject
    private ImportDAO importDAO;

    @Inject
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public void persist(ImportRequest request) {
        importDAO.persist(request);
    }

    @Override
    public List<ImportResponse> getAll() {
        return importDAO.getAll()
                .stream()
                .map(this::mapToModel)
                .toList();
    }

    private ImportResponse mapToModel(Import anImport) {
        return modelMapper.map(anImport, ImportResponse.class);
    }
}
