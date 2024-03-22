package com.example.filerouge.service.impl;

import com.example.filerouge.domain.Category;
import com.example.filerouge.domain.SubCategory;
import com.example.filerouge.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements com.example.filerouge.service.CategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category createCategory(String category) throws Exception {
        Optional<Category> CategoryByName = this.getCategoryByName(category);
        Category build = Category.builder().name(category).build();
        if(CategoryByName.isEmpty()) {
            return categoryRepository.save(build);
        }
        throw new Exception("This Category is Already Exist");
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
    public Optional<Category> getCategoryByName(String name){
        return categoryRepository.findByName(name);
    }

}
