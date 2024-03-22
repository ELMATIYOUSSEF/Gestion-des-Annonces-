package com.example.filerouge.web.mapper;

import com.example.filerouge.domain.Announce;
import com.example.filerouge.domain.Feature;
import com.example.filerouge.domain.Tag;
import com.example.filerouge.service.FeatureService;
import com.example.filerouge.service.TagService;
import com.example.filerouge.web.dto.AnnounceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.stream.Collectors;

@Mapper
public interface AnnounceMapper {

    AnnounceMapper INSTANCE = Mappers.getMapper(AnnounceMapper.class);


    AnnounceDto AnnounceToAnnounceDto(Announce announce);

    Announce AnnounceDtoToAnnounce(AnnounceDto announceDto);

    /*@Named("tagToLong")
    default Collection<Long> tagToLong(Collection<Tag> tags) {
        return tags == null ? null : tags.stream().map(Tag::getId).collect(Collectors.toList());
    }

    @Named("longToTag")
    default Collection<Tag> longToTag(Collection<Long> tagIds, TagService tagService) {
        return tagIds == null ? null : tagIds.stream()
                .map(id -> tagService.getTagById(id).orElseThrow(() -> new RuntimeException("Tag not found for id: " + id)))
                .collect(Collectors.toList());
    }

    @Named("featureToLong")
    default Collection<Long> featureToLong(Collection<Feature> features) {
        return features == null ? null : features.stream().map(Feature::getId).collect(Collectors.toList());
    }

    @Named("longToFeature")
    default Collection<Feature> longToFeature(Collection<Long> featureIds, FeatureService featureService) {
        return featureIds == null ? null : featureIds.stream()
                .map(id -> featureService.getFeatureById(id).orElseThrow(() -> new RuntimeException("Feature not found for id: " + id)))
                .collect(Collectors.toList());
    }*/
}

