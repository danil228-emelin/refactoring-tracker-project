package ru.ifmo.insys1.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifmo.insys1.entity.IncidentType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidentRequest {

    private String ssccCode;
    private String description;
    private IncidentType type;
}
