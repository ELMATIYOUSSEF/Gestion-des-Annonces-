package com.example.filerouge.web;

import com.example.filerouge.domain.Category;
import com.example.filerouge.domain.Tag;
import com.example.filerouge.handler.response.ResponseMessage;
import com.example.filerouge.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categorys")
public class CategoryController {
    private  final CategoryService categoryService;
    @GetMapping
    public ResponseEntity<ResponseMessage> getAllCategory() {
        List<Category> allCats = categoryService.getAllCategories();
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .data(allCats)
                        .message("List categorys Retrieved")
                        .status(200)
                        .build());
    }
    @PostMapping
    public ResponseEntity<ResponseMessage> CreateCategory(@RequestParam String category) throws Exception {
        Category CategorySaved = categoryService.createCategory(category);
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .data(CategorySaved)
                        .message("Category Created Successfully")
                        .status(200)
                        .build());
    }
}
