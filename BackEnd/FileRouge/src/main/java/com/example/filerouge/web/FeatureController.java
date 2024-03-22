package com.example.filerouge.web;

import com.example.filerouge.domain.Category;
import com.example.filerouge.domain.Feature;
import com.example.filerouge.handler.response.ResponseMessage;
import com.example.filerouge.service.CategoryService;
import com.example.filerouge.service.FeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/features")
public class FeatureController {
    private final FeatureService featureService;
    @GetMapping
    public ResponseEntity<ResponseMessage> getAllFeatures() {
        List<Feature> allFeatures = featureService.getAllFeatures();
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .data(allFeatures)
                        .message("List Features Retrieved")
                        .status(200)
                        .build());
    }
    @PostMapping
    public ResponseEntity<ResponseMessage> CreateFeatures(@RequestParam String feature) throws Exception {
        Feature featureSaved = featureService.createFeature(feature);
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .data(featureSaved)
                        .message("Feature Created Successfully")
                        .status(200)
                        .build());
    }
}
