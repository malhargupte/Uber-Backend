package com.guptem.UberBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto {

    private Long id;
    private UserDto user; // field names should be same in dto and entity
    private Double rating;
    private boolean available;
    private String vehicleId;

}
