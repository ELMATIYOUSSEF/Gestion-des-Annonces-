package com.example.filerouge.web.mapper.MapperConfig;


import com.example.filerouge.web.mapper.AnnounceMapper;
import com.example.filerouge.web.mapper.InformationMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public AnnounceMapper produitMapper() {
        return AnnounceMapper.INSTANCE;
    }
    @Bean
    public InformationMapper InformationMapper() {
        return InformationMapper.INSTANCE;
    }
}

