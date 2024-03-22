package com.example.filerouge.web;

import com.example.filerouge.domain.Announce;
import com.example.filerouge.domain.Information;
import com.example.filerouge.exception.ResourceNotFoundException;
import com.example.filerouge.handler.response.ResponseMessage;
import com.example.filerouge.service.AnnounceService;
import com.example.filerouge.web.dto.AnnounceDto;
import com.example.filerouge.web.mapper.AnnounceMapper;
import com.example.filerouge.web.mapper.InformationMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/announces")
public class AnnounceController {
    private final AnnounceService announceService;
    private final ObjectMapper objectMapper;
    private final AnnounceMapper announceMapper;
    private final InformationMapper informationMapper;
    @GetMapping
    public ResponseEntity<ResponseMessage> getAllAnnounces(@RequestParam Optional<Integer> page,
                                          @RequestParam Optional<Integer> size) {
        Page<Announce> allAnnounces = announceService.getAllAnnounces(page.orElse(0), size.orElse(10));
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .data(allAnnounces)
                        .message("Pages Announces Retrieved")
                        .status(200)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getAnnounceById(@PathVariable Long id) throws ClassNotFoundException {
        Announce announceById = announceService.getAnnounceById(id);
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .data(announceById)
                        .message("Announce Retrieved")
                        .status(200)
                        .build());
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> saveAnnounce(@RequestParam("announceInformation") String info,
                                                        @RequestParam("images") List<MultipartFile> images) throws Exception {
        AnnounceDto announceDto = objectMapper.readValue(info, AnnounceDto.class);
        Information information = informationMapper.ToEntity(announceDto.getInformation());
        Collection<Long> featureIds = announceDto.getFeatureIds();
        Collection<Long> tagIds = announceDto.getTagIds();
        Long subCat = announceDto.getSubCategoryId();
        Long category = announceDto.getCategoryId();
        Long publisher = announceDto.getPublisherId();
        Announce announce = announceService.saveAnnounce(
                announceMapper.AnnounceDtoToAnnounce(announceDto),
                images ,information , featureIds , tagIds ,subCat , category );
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .data(announceMapper.AnnounceToAnnounceDto(announce))
                        .message("Announce Created Successfully")
                        .status(200)
                        .build());
    }

    @DeleteMapping("/{id}")
    public void deleteAnnounce(@PathVariable Long id) {
        announceService.deleteAnnounce(id);
    }
}
