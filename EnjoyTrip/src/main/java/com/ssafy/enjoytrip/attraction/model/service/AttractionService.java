package com.ssafy.enjoytrip.attraction.model.service;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.attraction.dto.ReviewDto;
import com.ssafy.enjoytrip.attraction.dto.SearchDto;
import com.ssafy.enjoytrip.response.AttractionResponseDto;
import com.ssafy.enjoytrip.response.ResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AttractionService {

    List<AttractionDto> getLocations(Map<String, Integer> param) throws Exception;

    List<AttractionDto> searchLocations(SearchDto searchDto) throws Exception;

    ResponseEntity<ResponseDto> searchLocationDetail(String contentId);

    List<ReviewDto> locationReviews(String contentId) throws Exception ;

    List<ReviewDto> saveLocationReview(ReviewDto review) throws Exception;

    int saveLocationLike(Map<String, String> param) throws Exception;

    List<Map<String, String>> getGugunCode(String sido) throws Exception;
}
