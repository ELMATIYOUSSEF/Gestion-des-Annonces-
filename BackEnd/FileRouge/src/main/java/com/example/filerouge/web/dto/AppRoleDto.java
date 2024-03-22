package com.example.filerouge.web.dto;

import com.example.filerouge.web.dto.AppPermissionDto;
import lombok.Value;

import java.io.Serializable;
import java.util.Collection;

/**
 * DTO for {@link com.example.filerouge.domain.AppRole}
 */
@Value
public class AppRoleDto implements Serializable {
    Long id;
    String name;
    Collection<AppPermissionDto> permissions;
}
