package ru.ifmo.insys1.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifmo.insys1.entity.IncidentType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentResponse {

    private String description;
    private String sscc;
    private String clientUsername;
    private IncidentType incidentType;
    private String incidentDate;
}
