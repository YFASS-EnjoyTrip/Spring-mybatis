package com.ssafy.enjoytrip.attraction.model.service;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.attraction.dto.ReviewDto;
import com.ssafy.enjoytrip.attraction.dto.SearchDto;
import com.ssafy.enjoytrip.attraction.model.mapper.AttractionMapper;
import com.ssafy.enjoytrip.global.mapper.LikeMapper;
import com.ssafy.enjoytrip.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionMapper mapper;
    private final LikeMapper likeMapper;

    @Override
    public ResponseEntity<ResponseDto> getLocations(String keyWord) {
        String message;
        try {
            List<AttractionDto> result = mapper.getLocations(keyWord);
            message = "여행지 조회 요청 성공했습니다.";
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(HttpStatus.OK.value(), message, result));

        } catch (Exception e) {

            log.info("error={}", e);
            message = "여행지 조회 요청에 실패했습니다.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null));
        }
    }

    @Override
    public ResponseEntity<ResponseDto> searchLocations(SearchDto searchDto)  {
        String message;
        try {
            List<AttractionDto> result = mapper.searchLocations(searchDto);

            message = "여행지 검색 요청 성공했습니다.";
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(HttpStatus.OK.value(), message, result));
        } catch (Exception e) {

            log.info("error={}", e);
            message = "여행지 검색 요청에 실패했습니다.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null));
        }
    }

    @Override
    public ResponseEntity<ResponseDto> searchLocationDetail(String contentId) {
        String message;
        try {
            AttractionDto result = mapper.searchLocationDetail(contentId);

            message = "여행지 상세조회 요청 성공했습니다.";
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(HttpStatus.OK.value(), message, result));
        } catch (Exception e) {
            log.info("error={}", e);
            message = "여행지 상세조회 요청에 실패했습니다.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null));
        }
    }

    @Override
    public ResponseEntity<ResponseDto> locationReviews(String contentId) {
        String message;
        try {
            List<ReviewDto> result = mapper.getLocationReviews(contentId);

            message = "여행지 리뷰 조회 요청 성공했습니다.";
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(HttpStatus.OK.value(), message, result));
        } catch (Exception e) {
            log.info("error={}", e);
            message = "여행지 리뷰 조회 요청에 실패했습니다.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null));
        }
    }

    @Override
    public ResponseEntity<ResponseDto> saveLocationReview(ReviewDto review) throws Exception {
        mapper.insertLocationReview(review);
        return locationReviews(String.valueOf(review.getContentId()));
    }

    /**
     * 여행지에 대하여 "좋아요" 처리
     */
    @Override
    public ResponseEntity<ResponseDto> saveLocationLike(Map<String, String> param) {
        String message;
        try {
            checkLike(param);
        } catch (Exception e) {
            log.info("error={}", e);
            message = "좋아요 요청에 실패했습니다.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null));
        }

        int status = HttpStatus.OK.value();
        message = "좋아요 요청을 정상적으로 수행 했습니다.";
        ResponseDto res = new ResponseDto(status, message, null);

        return ResponseEntity.status(HttpStatus.OK)
                .body(res);
    }

    @Override
    public List<Map<String, String>> getGugunCode(String sido) throws Exception {
        return mapper.selectGugunCode(sido);
    }

    private void checkLike(Map<String, String> param) throws SQLException {
        int result = likeMapper.selectLike(param);

        log.info("result={}", result);
        if (result == 0) {
            likeMapper.insertLike(param);
            param.put("flag", "plus");
            mapper.updateLocationLike(param);
        } else {
            likeMapper.deleteLike(param);
            param.put("flag", "minus");
            mapper.updateLocationLike(param);
        }
    }
}
