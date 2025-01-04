package ru.ifmo.insys1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.modelmapper.ModelMapper;
import ru.ifmo.insys1.dao.LabelsDAO;
import ru.ifmo.insys1.entity.Label;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.response.LabelResponse;
import ru.ifmo.insys1.service.LabelsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@ApplicationScoped
public class LabelsServiceImpl implements LabelsService {

    @Inject
    private LabelsDAO labelDAO;

    @Inject
    private ModelMapper mapper;

    private static final String COMPANY_PREFIX = "1234567";

    @Override
    public LabelResponse getLabel(Integer id) {
        return labelDAO.findById(id)
                .map(l -> mapper.map(l, LabelResponse.class))
                .orElseThrow(
                        () -> new ServiceException(Response.Status.NOT_FOUND, "Label not found")
                );
    }

    @Override
    @Transactional
    public LabelResponse createLabel() {
        var labelEntity = new Label();
        labelEntity.setGenerationDate(LocalDateTime.now());
        labelEntity.setSsccCode(generateSsccCode());
        return mapToResponse(labelDAO.save(labelEntity));
    }

    private LabelResponse mapToResponse(Label save) {
        var response = new LabelResponse();
        response.setSsccCode(save.getSsccCode());
        response.setId(save.getId());
        response.setGenerationDate(save.getGenerationDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        return response;
    }

    private String generateSsccCode() {
        int extensionDigit = new Random().nextInt(10);
        String serialNumber = String.format("%09d", new Random().nextInt(1_000_000_000));
        String baseSscc = extensionDigit + COMPANY_PREFIX + serialNumber;
        int checkDigit = calculateCheckDigit(baseSscc);
        return baseSscc + checkDigit;
    }

    private int calculateCheckDigit(String baseSscc) {
        int sum = 0;
        for (int i = 0; i < baseSscc.length(); i++) {
            int digit = Character.getNumericValue(baseSscc.charAt(i));
            sum += (i % 2 == 0) ? digit * 3 : digit;
        }
        int remainder = sum % 10;
        return (remainder == 0) ? 0 : 10 - remainder;
    }
}
