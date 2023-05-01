package com.ssafy.enjoytrip.attraction.model.service;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.attraction.dto.ReviewDto;
import com.ssafy.enjoytrip.attraction.dto.SearchDto;
import com.ssafy.enjoytrip.response.AttractionResponseDto;
import com.ssafy.enjoytrip.response.ResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AttractionService {

    ResponseEntity<ResponseDto> getLocations(String keyWord);

    ResponseEntity<ResponseDto> searchLocations(SearchDto searchDto) ;

    ResponseEntity<ResponseDto> searchLocationDetail(String contentId);

    ResponseEntity<ResponseDto> locationReviews(String contentId) ;

    ResponseEntity<ResponseDto> saveLocationReview(ReviewDto review);

    ResponseEntity<ResponseDto> saveLocationLike(Map<String, String> param) ;
}
