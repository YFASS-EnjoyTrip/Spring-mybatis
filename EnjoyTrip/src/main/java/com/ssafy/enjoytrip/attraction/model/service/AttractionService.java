package com.ssafy.enjoytrip.attraction.model.service;

import com.ssafy.enjoytrip.attraction.dto.SearchDto;
import com.ssafy.enjoytrip.response.AttractionResponseDto;
import org.springframework.http.ResponseEntity;

public interface AttractionService {

    ResponseEntity<AttractionResponseDto> getLocations(String keyWord) throws Exception;

    ResponseEntity<AttractionResponseDto> searchLocations(SearchDto searchDto) throws Exception;
}
