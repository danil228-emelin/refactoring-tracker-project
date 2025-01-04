package ru.ifmo.insys1.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifmo.insys1.entity.LocationType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {

    private Integer id;

    private String address;

    private LocationType type;

    private String name;
}
