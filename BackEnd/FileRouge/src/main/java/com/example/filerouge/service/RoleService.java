package com.example.filerouge.service;

import com.example.filerouge.domain.AppRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    AppRole FindByName(String name ) ;
}
