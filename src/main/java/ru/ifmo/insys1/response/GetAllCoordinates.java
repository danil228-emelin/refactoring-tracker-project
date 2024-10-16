package ru.ifmo.insys1.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifmo.insys1.dto.CoordinatesDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCoordinates {

    private List<CoordinatesDTO> coordinates;
}
