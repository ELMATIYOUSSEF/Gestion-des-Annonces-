package com.example.filerouge.web;

import com.example.filerouge.domain.Announce;
import com.example.filerouge.domain.Tag;
import com.example.filerouge.handler.response.ResponseMessage;
import com.example.filerouge.service.TagService;
import com.example.filerouge.web.dto.GlobalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagsController {
    private final TagService tagService;
    @GetMapping
    public ResponseEntity<ResponseMessage> getAllTags() {
        List<Tag> allTags = tagService.getAllTags();
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .data(allTags)
                        .message("List Tags Retrieved")
                        .status(200)
                        .build());
    }
    @PostMapping
    public ResponseEntity<ResponseMessage> CreateTags(@RequestParam String tag) throws Exception {
        Tag tagSaved = tagService.createTag(tag);
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .data(tagSaved)
                        .message("Tags Created Successfully")
                        .status(200)
                        .build());
    }
}
