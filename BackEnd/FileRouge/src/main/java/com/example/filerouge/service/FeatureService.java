package com.example.filerouge.service;

import com.example.filerouge.domain.Feature;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface FeatureService {
    List<Feature> getAllFeatures();

    Optional<Feature> getFeatureById(Long id);

    Feature createFeature(String feature) throws Exception;

    Feature updateFeature(Long id, Feature feature);

    void deleteFeature(Long id);
}
