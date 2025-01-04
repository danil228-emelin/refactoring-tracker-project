package ru.ifmo.insys1.service;

import ru.ifmo.insys1.response.LabelResponse;

public interface LabelsService {

    LabelResponse getLabel(Integer id);

    LabelResponse createLabel();
}
