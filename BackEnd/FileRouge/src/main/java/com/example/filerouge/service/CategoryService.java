package com.example.filerouge.service;

import com.example.filerouge.domain.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface CategoryService {
    List<Category> getAllCategories();

    Optional<Category> getCategoryById(Long id);

    Category createCategory(String category) throws Exception;

    void deleteCategory(Long id);
}
