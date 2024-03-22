package com.example.filerouge.web;

import com.example.filerouge.domain.SubCategory;
import com.example.filerouge.handler.response.ResponseMessage;
import com.example.filerouge.service.SubCategoryService;
import com.example.filerouge.web.dto.GlobalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subcategorys")
public class SubCategoryController {
    private final SubCategoryService subCategoryService;
    @GetMapping
    public ResponseEntity<ResponseMessage> getAllCategory() {
        List<SubCategory> allCats = subCategoryService.getAllSubCategories();
        List<GlobalDto> list = allCats.stream().map(this::subToDTO).toList();
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .data(list)
                        .message("List SubCategory Retrieved")
                        .status(200)
                        .build());
    }
    private GlobalDto subToDTO(SubCategory category ){
        return  GlobalDto.builder().id(category.getId()).name(category.getName()).build();

    }
    @PostMapping
    public ResponseEntity<ResponseMessage> CreateCategory(@RequestParam String subCategory,@RequestParam Long id) throws Exception {
        SubCategory CategorySaved = subCategoryService.createSubCategory(subCategory, id);
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .data(CategorySaved)
                        .message("SubCategory Created Successfully")
                        .status(200)
                        .build());
    }
}
