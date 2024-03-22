package com.example.filerouge.web.mapper;

import com.example.filerouge.domain.Announce;
import com.example.filerouge.domain.Information;
import com.example.filerouge.web.dto.AnnounceDto;
import com.example.filerouge.web.dto.InformationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InformationMapper {
    InformationMapper INSTANCE = Mappers.getMapper( InformationMapper.class );
    InformationDTO ToDto(Information information);
    Information ToEntity(InformationDTO informationDTO);
}
