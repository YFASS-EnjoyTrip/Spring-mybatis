package com.ssafy.enjoytrip.attraction.model.service;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.attraction.dto.ReviewDto;
import com.ssafy.enjoytrip.attraction.dto.SearchDto;
import com.ssafy.enjoytrip.response.AttractionResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AttractionService {

    ResponseEntity<AttractionResponseDto> getLocations(String keyWord) throws Exception;

    ResponseEntity<AttractionResponseDto> searchLocations(SearchDto searchDto) throws Exception;

    ResponseEntity<AttractionResponseDto> searchLocationDetail(String contentId) throws Exception;

    ResponseEntity<AttractionResponseDto> locationReviews(String contentId) throws Exception;

    ResponseEntity<AttractionResponseDto> saveLocationReview(ReviewDto review) throws Exception;

    ResponseEntity<AttractionResponseDto> saveLocationLike(Map<String, String> param) throws Exception;
}
