package com.ssafy.enjoytrip.attraction.controller;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.attraction.dto.SearchDto;
import com.ssafy.enjoytrip.attraction.model.service.AttractionService;
import com.ssafy.enjoytrip.response.AttractionResponseDto;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
@AllArgsConstructor
public class AttractionController {
    private final AttractionService service;


    /**
     * Default 조회, "서울" 을 기준으로 모든 여행지 조회
     * @return
     */
    @GetMapping
    public ResponseEntity<AttractionResponseDto> locations() throws Exception {
        String keyWord = "서울%"; // 디폴트 값
        return service.getLocations(keyWord);
    }

    /**
     * 키워드, 조건 검색
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<AttractionResponseDto> searchLocations(@RequestParam String keyword,
                                                                 @RequestParam(required = false) String sido,
                                                                 @RequestParam(required = false) String gugun,
                                                                 @RequestParam(required = false) List<Integer> contentType) throws Exception {
        return service.searchLocations(new SearchDto(keyword, sido, gugun, contentType));
    }

    /**
     * 여행지 상세조회
     */
    @GetMapping("/detail")
    public ResponseEntity<AttractionResponseDto> searchLocationDetail(@RequestParam String contentId) throws Exception {
        return service.searchLocationDetail(contentId);
    }

    /**
     * 여행지 리뷰조회
     */
    @GetMapping("/detail/{contentId}/reviews")
    public ResponseEntity<AttractionResponseDto> locationReviews(@PathVariable("contentId") String contentId) throws Exception {
        return service.locationReviews(contentId);
    }
}
