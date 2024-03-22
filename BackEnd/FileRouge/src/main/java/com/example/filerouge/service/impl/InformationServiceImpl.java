package com.example.filerouge.service.impl;

import com.example.filerouge.domain.Information;
import com.example.filerouge.repository.InformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InformationServiceImpl implements com.example.filerouge.service.InformationService {

    private final InformationRepository informationRepository;
    @Override
    public List<Information> getAllInformation() {
        return informationRepository.findAll();
    }
    @Override
    public Optional<Information> getInformationById(Long id) {
        return informationRepository.findById(id);
    }
    @Override
    public Information createInformation(Information information) {
        return informationRepository.save(information);
    }
    @Override
    public void deleteInformation(Long id) {
        informationRepository.deleteById(id);
    }
}
