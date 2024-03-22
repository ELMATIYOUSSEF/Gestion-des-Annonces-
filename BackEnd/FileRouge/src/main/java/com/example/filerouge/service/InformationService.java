package com.example.filerouge.service;

import com.example.filerouge.domain.Information;

import java.util.List;
import java.util.Optional;

public interface InformationService {
    List<Information> getAllInformation();

    Optional<Information> getInformationById(Long id);

    Information createInformation(Information information);

    void deleteInformation(Long id);
}
