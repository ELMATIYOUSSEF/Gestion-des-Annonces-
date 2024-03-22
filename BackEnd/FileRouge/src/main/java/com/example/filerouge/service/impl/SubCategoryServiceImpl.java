package com.example.filerouge.service.impl;

import com.example.filerouge.domain.Category;
import com.example.filerouge.domain.SubCategory;
import com.example.filerouge.domain.Tag;
import com.example.filerouge.exception.ResourceNotFoundException;
import com.example.filerouge.repository.CategoryRepository;
import com.example.filerouge.repository.SubCategoryRepository;
import com.example.filerouge.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SubCategoryServiceImpl implements com.example.filerouge.service.SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }

    @Override
    public Optional<SubCategory> getSubCategoryById(Long id) {
        return subCategoryRepository.findById(id);
    }

    @Override
    public SubCategory createSubCategory(String subCategory, Long id) throws Exception {
        Optional<Category> categoryById = categoryRepository.findById(id);
        if (categoryById.isEmpty()) throw new ResourceNotFoundException("NO Category with this name");
        Optional<SubCategory> SubCategoryByName = this.getSubCategoryByName(subCategory);
        SubCategory build = SubCategory.builder().name(subCategory).categories(categoryById.get()).build();
        if(SubCategoryByName.isEmpty()) {
            return subCategoryRepository.save(build);
        }
        throw new Exception("This SubCategory is Already Exist");
    }

    @Override
    public SubCategory updateSubCategory(Long id, SubCategory subCategory) {
        if (subCategoryRepository.existsById(id)) {
            subCategory.setId(id);
            return subCategoryRepository.save(subCategory);
        }
        return null;
    }

    @Override
    public void deleteSubCategory(Long id) {
        subCategoryRepository.deleteById(id);
    }

    public Optional<SubCategory> getSubCategoryByName(String name){
        return subCategoryRepository.findByName(name);
    }


}
