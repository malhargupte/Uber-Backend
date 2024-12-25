package com.guptem.UberBackend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@Getter
@Setter
public class PointDto {

    private double[] coordinates;
    private String type = "Point";

    public PointDto(double[] coordinates) {
        this.coordinates = coordinates;
    }

}
