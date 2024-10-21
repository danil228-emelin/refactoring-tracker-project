package ru.ifmo.insys1.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifmo.insys1.dto.LocationDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllLocations {

    private List<LocationDTO> locations;
}
