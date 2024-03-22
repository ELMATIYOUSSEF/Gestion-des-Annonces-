package com.example.filerouge.service.impl;

import com.example.filerouge.domain.AppRole;
import com.example.filerouge.repository.RoleRepository;
import com.example.filerouge.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository ;
    @Override
    public AppRole FindByName(String name) {
       return roleRepository.findByName(name).orElseThrow(() -> new UsernameNotFoundException("No Role with this name"));
    }
}
