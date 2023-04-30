package com.ssafy.enjoytrip.attraction.model.service;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.attraction.dto.ReviewDto;
import com.ssafy.enjoytrip.attraction.dto.SearchDto;
import com.ssafy.enjoytrip.attraction.model.mapper.AttractionMapper;
import com.ssafy.enjoytrip.response.AttractionResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class AttractionServiceImpl implements AttractionService{

    private final AttractionMapper mapper;
    private final AttractionResponseDto responseDto;

    @Override
    public ResponseEntity<AttractionResponseDto> getLocations(String keyWord) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto.successGetLocations(mapper.getLocations(keyWord)));
    }

    @Override
    public ResponseEntity<AttractionResponseDto> searchLocations(SearchDto searchDto) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto.successSearchLocations(mapper.searchLocations(searchDto)));
    }

    @Override
    public ResponseEntity<AttractionResponseDto> searchLocationDetail(String contentId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto.successSearchLocationDetail(mapper.searchLocationDetail(contentId)));
    }

    @Override
    public ResponseEntity<AttractionResponseDto> locationReviews(String contentId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto.successLocationReviews(mapper.getLocationReviews(contentId)));
    }

    @Override
    public ResponseEntity<AttractionResponseDto> saveLocationReview(ReviewDto review) throws Exception {
        mapper.insertLocationReview(review);
        return locationReviews(String.valueOf(review.getContentId()));
    }

    @Override
    public ResponseEntity<AttractionResponseDto> saveLocationLike(Map<String, String> param) {

        try {
            mapper.insertLocationLike(param);
            String result = String.valueOf(param.get("id"));
            log.info("id={}", result);
//            Integer result = Integer.parseInt(param.get("id"));
//            log.info("id={}", result);

            if ("0".equals(result) || "null".equals(result)) {
                throw new RuntimeException();
            }

            mapper.updateLocationLike(param.get("contentId"));
        } catch (Exception e) {
            log.info("error={}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(responseDto.failResponse());
        }

        int status = HttpStatus.OK.value();
        String message = "요청을 정상적으로 수행 했습니다.";
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto.successResponse(status, message, null));
    }

}
