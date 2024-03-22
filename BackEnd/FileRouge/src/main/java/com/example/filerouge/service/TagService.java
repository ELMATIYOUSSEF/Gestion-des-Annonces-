package com.example.filerouge.service;

import com.example.filerouge.domain.Tag;
import com.example.filerouge.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface TagService {
    List<Tag> getAllTags();

     Optional<Tag> getTagById(Long id);
    Optional<Tag> getTagByName(String name);

    Tag createTag(String tag) throws Exception;

    void deleteTag(Long id);
}
