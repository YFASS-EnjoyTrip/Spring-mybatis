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
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AttractionServiceImpl implements AttractionService {

    private final AttractionMapper mapper;
    private final LikeMapper likeMapper;

    @Override
    public List<AttractionDto> getLocations(Map<String, Integer> param) throws Exception {
            return mapper.getLocations(param);
    }

    @Override
    public List<AttractionDto> searchLocations(SearchDto searchDto) throws Exception{
        return mapper.searchLocations(searchDto);
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
    public List<ReviewDto> locationReviews(String contentId) throws Exception {
           return mapper.getLocationReviews(contentId);
    }

    /**
     * 여행지 리뷰 작성
     */
    @Override
    public List<ReviewDto> saveLocationReview(ReviewDto review) throws Exception {
        mapper.insertLocationReview(review); // 리뷰작성
        mapper.updateLocationRate(review); // 별점 갱신
        return locationReviews(String.valueOf(review.getContentId()));
    }

    /**
     * 여행지에 대하여 "좋아요" 처리
     */
    @Override
    public int saveLocationLike(Map<String, String> param) throws Exception {
        checkLike(param);
        return mapper.searchLocationDetail(param.get("contentId")).getLikeCount();
    }

    @Override
    public List<Map<String, String>> getGugunCode(String sido) throws Exception {
        return mapper.selectGugunCode(sido);
    }

    @Override
    public void removeLocationReview(Map<String, String> param) throws Exception {
        mapper.deleteLocationReview(param); // 리뷰 삭제
        mapper.deleteLocationRate(param); // 리뷰 점수 차감
    }

    @Override
    public List<AttractionDto> findBoundLocation(Map<String, String> param) throws Exception {
        List<AttractionDto> attractionDtos = mapper.selectBoundLocation(param);
        log.info("service={}", attractionDtos);
        return attractionDtos;
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
