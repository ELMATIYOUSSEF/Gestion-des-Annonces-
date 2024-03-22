package com.example.filerouge.service;

import com.example.filerouge.domain.Announce;
import com.example.filerouge.domain.Information;
import com.example.filerouge.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Service
public interface AnnounceService{
    Page<Announce> getAllAnnounces(int page , int size);

    Announce getAnnounceById(Long id) throws ClassNotFoundException;

    Announce saveAnnounce(Announce announce , List<MultipartFile> images , Information information , Collection<Long> featureIds ,Collection<Long> tagsId,Long subCat , Long category ) throws Exception;

    void deleteAnnounce(Long id);
}
