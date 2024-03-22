package com.example.filerouge.service.impl;

import com.example.filerouge.domain.*;
import com.example.filerouge.domain.Enums.StatusAnnounce;
import com.example.filerouge.exception.ResourceNotFoundException;
import com.example.filerouge.repository.AnnounceRepository;
import com.example.filerouge.repository.ImageRepository;
import com.example.filerouge.repository.InformationRepository;
import com.example.filerouge.service.*;
import com.example.filerouge.utils.FileUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.domain.PageRequest.of;
@Service
@RequiredArgsConstructor
public class AnnounceServiceImpl implements AnnounceService {

    private final AnnounceRepository announceRepository;
    private final InformationRepository informationRepository;
    private final FeatureService featureService;
    private final TagService tagservice;
    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;
    private final ImageRepository imageRepository;
    private final FileUtils fileUtils;
    private final UserService userService;

    @Override
    public Page<Announce> getAllAnnounces(int page , int size) {
        return announceRepository.findAll(of(page,size));
    }

    @Override
    public Announce getAnnounceById(Long id) throws ClassNotFoundException {
        Optional<Announce> announce = announceRepository.findById(id);
        if(announce.isEmpty())
            throw new ClassNotFoundException();
        return announce.get();
    }

    @Transactional
    public Announce saveAnnounce(Announce announce, List<MultipartFile> images, Information information,
                                 Collection<Long> featureIds, Collection<Long> tagIds,
                                 Long subCategoryId, Long categoryId) throws Exception {
        // Fetching entities
        Collection<Feature> features = featureIds.stream()
                .map(id -> {
                    try {
                        return featureService.getFeatureById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Feature with id not found: " + id));
                    } catch (ResourceNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());

        Collection<Tag> tags = tagIds.stream()
                .map(id -> {
                    try {
                        return tagservice.getTagById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Tag with id not found: " + id));
                    } catch (ResourceNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());

        Category category = categoryService.getCategoryById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id not found: " + categoryId));

        SubCategory subCategory = subCategoryService.getSubCategoryById(subCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("SubCategory with id not found: " + subCategoryId));

        // Set the relations
        announce.setStatus(StatusAnnounce.PENDING);
        announce.setFeatures(features);
        announce.setTags(tags);
        announce.setPublisher(userService.getCurrentUser());
        announce.setSubCategories(subCategory);
        announce.setCategoryId(categoryId);

        // Save the announce first to generate its ID
        Announce savedAnnounce = announceRepository.save(announce);


        information.setAnnounce(savedAnnounce);
        informationRepository.save(information);

        uploadPropertyImages(savedAnnounce, images);

        return savedAnnounce;
    }


    @Override
    public void deleteAnnounce(Long id) {
        announceRepository.deleteById(id);
    }
    private void uploadPropertyImages(Announce announce, List<MultipartFile> images) {
        //List<Image> ImagesList = new ArrayList<>();
        for (MultipartFile image : images) {
            try {
                String fileName = fileUtils.storeFile(image);
                Image AnnounceImage = Image.builder()
                        .path(fileName)
                        .announce(announce)
                        .build();
                imageRepository.save(AnnounceImage);
                //ImagesList.add(AnnounceImage);
            } catch (IOException e) {
                System.out.println("Error to store image name:" + image.getName());
            }
        }
        // Save all property images
      // imageRepository.saveAll(ImagesList);
    }

}
