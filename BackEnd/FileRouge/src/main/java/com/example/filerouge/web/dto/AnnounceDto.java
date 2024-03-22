package com.example.filerouge.web.dto;

import com.example.filerouge.domain.Enums.StatusAnnounce;
import lombok.Value;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * DTO for {@link com.example.filerouge.domain.Announce}
 */
@Value
public class AnnounceDto implements Serializable {
    @NotNull
    private String title;

    @NotNull
    private String description;

    @Positive
    private Double price;

    @NotNull
    private Long publisherId;

    @NotNull
    private InformationDTO information;

    private Collection<@NotNull Long> tagIds = new ArrayList<>();

    private Collection<@NotNull Long> featureIds = new ArrayList<>();

    @NotNull
    private Long locationId;

    @NotNull
    private Long subCategoryId;

    @NotNull
    private Long categoryId;
}
