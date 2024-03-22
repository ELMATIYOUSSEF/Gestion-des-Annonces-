package com.example.filerouge.web.mapper;

import com.example.filerouge.domain.AppRole;
import com.example.filerouge.domain.AppUser;
import com.example.filerouge.web.dto.AppUserDto;
import com.example.filerouge.web.dto.UserResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppUserMapper {


    private AppUserMapper() {
    }

    public static AppUser toEntity(AppUserDto userDto) {
        List<AppRole> roles = new ArrayList<>();
        if(userDto.authorities() != null){
            for (String role : userDto.authorities()) {
                roles.add(AppRole.builder().name(role).build());
            }
        }
        return AppUser.builder()
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .email(userDto.email())
                .password(userDto.password())
                .roles(roles)
                .build();
    }

    public static UserResponseDto toDto(AppUser user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .authorities(user.getRoles().stream().map(AppRole::getName).toList())
                .build();
    }
}
