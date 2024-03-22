package com.example.filerouge.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformationDTO {
    @NotNull
    private String phone;

    @NotNull
    private Boolean visiblePhone;

    @Positive
    private Integer numChambers;

    @Positive
    private Integer numSalons;

    @Positive
    private Integer numBain;

    @Positive
    private Integer numCuisine;

    @PositiveOrZero
    private Integer floor;

    @PositiveOrZero
    private Double livingSurface;

    @PositiveOrZero
    private Double totalSurface;

    @PositiveOrZero
    private Double propertyAge;

    @PositiveOrZero
    private Double syndicFees;

    @PositiveOrZero
    private Double cleaningFees;
}
