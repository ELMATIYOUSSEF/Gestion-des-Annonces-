package com.example.filerouge.service;

import com.example.filerouge.domain.SubCategory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface SubCategoryService {
    List<SubCategory> getAllSubCategories();

    Optional<SubCategory> getSubCategoryById(Long id);

    SubCategory createSubCategory(String subCategory , Long id) throws Exception;

    SubCategory updateSubCategory(Long id, SubCategory subCategory);

    void deleteSubCategory(Long id);
    Optional<SubCategory> getSubCategoryByName(String name);
}
