package com.example.filerouge.web.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.filerouge.domain.AppPermission}
 */
@Value
public class AppPermissionDto implements Serializable {
    Long id;
    String name;
}
