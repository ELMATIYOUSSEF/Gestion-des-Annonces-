package com.example.filerouge.service.impl;

import com.example.filerouge.domain.Feature;
import com.example.filerouge.repository.FeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class FeatureServiceImpl implements com.example.filerouge.service.FeatureService {
    private final FeatureRepository featureRepository;
    @Override
    public List<Feature> getAllFeatures() {
        return featureRepository.findAll();
    }

    @Override
    public Optional<Feature> getFeatureById(Long id) {
        return featureRepository.findById(id);
    }

    @Override
    public Feature createFeature(String feature) throws Exception {
        Optional<Feature> feature1 = this.getFeatureByName(feature);
        Feature build = Feature.builder().name(feature).build();
        if(feature1.isEmpty()) {
            return featureRepository.save(build);
        }
        throw new Exception("This Feature is Already Exist");
    }

    public Optional<Feature> getFeatureByName(String name){
        return featureRepository.findByName(name);
    }
    @Override
    public Feature updateFeature(Long id, Feature feature) {
        if (featureRepository.existsById(id)) {
            feature.setId(id);
            return featureRepository.save(feature);
        }
        return null; // Or throw an exception indicating the entity wasn't found
    }

    @Override
    public void deleteFeature(Long id) {
        featureRepository.deleteById(id);
    }
}
