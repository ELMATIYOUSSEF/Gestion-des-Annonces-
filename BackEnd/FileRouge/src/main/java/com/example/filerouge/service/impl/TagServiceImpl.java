package com.example.filerouge.service.impl;

import com.example.filerouge.domain.Tag;
import com.example.filerouge.exception.ResourceNotFoundException;
import com.example.filerouge.repository.TagRepository;
import com.example.filerouge.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;



    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    public Optional<Tag> getTagByName(String name){
        return tagRepository.findByName(name);
    }

    @Override
    public Tag createTag(String tag) throws Exception {
        Optional<Tag> tagByName = this.getTagByName(tag);
        Tag build = Tag.builder().name(tag).build();
        if(tagByName.isEmpty()) {
          return tagRepository.save(build);
        }
        throw new Exception("This Tag is Already Exist");
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
