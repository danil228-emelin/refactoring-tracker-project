package ru.ifmo.insys1.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifmo.insys1.entity.LocationType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {

    private String address;

    private LocationType type;

    private String name;
}
