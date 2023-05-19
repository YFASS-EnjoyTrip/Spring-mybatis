package com.ssafy.enjoytrip.attraction.controller;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.attraction.dto.ReviewDto;
import com.ssafy.enjoytrip.attraction.dto.SearchDto;
import com.ssafy.enjoytrip.attraction.model.service.AttractionService;
import com.ssafy.enjoytrip.global.util.JwtTokenProvider;
import com.ssafy.enjoytrip.response.AttractionResponseDto;
import com.ssafy.enjoytrip.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
@CrossOrigin
public class AttractionController {
    private final AttractionService service;
    private final JwtTokenProvider jwtService;
    private String AUTH_HEADER = "Authorization";


    /**
     * Default 조회, 모든 여행지 조회
     * @return
     */
    @GetMapping
    public ResponseEntity<ResponseDto> locations(@RequestParam int page, @RequestParam int pageSize) throws Exception {
        Map<String, Integer> param = new HashMap<>();
        param.put("pageSize", pageSize);
        param.put("offset", (page - 1) * pageSize);

        List<AttractionDto> result = service.getLocations(param);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK.value(), "여행지 조회 성공", result));
    }

    /**
     * 키워드, 조건 검색
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<ResponseDto> searchLocations(@RequestParam(required = false) String keyword,
                                                       @RequestParam(required = false) String sido,
                                                       @RequestParam(required = false) String gugun,
                                                       @RequestParam int page,
                                                       @RequestParam int pageSize,
                                                       @RequestParam(required = false) List<Integer> contentType) throws Exception {
        int offset = (page - 1) * pageSize;
        List<AttractionDto> result = service.searchLocations(new SearchDto(keyword, sido, gugun, pageSize, offset, contentType));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK.value(), "요청을 성공적으로 수행", result));
    }

    /**
     * sido 코드로 gugun 코드 전체 return
     */
    @GetMapping("/search/gugun")
    public ResponseEntity<ResponseDto> searchGugunCode(@RequestParam String sido) throws Exception{
        List<Map<String, String>> result = service.getGugunCode(sido);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK.value(), "조회 완료 했습니다", result));
    }

    /**
     * 여행지 상세조회
     */
    @GetMapping("/detail")
    public ResponseEntity<ResponseDto> searchLocationDetail(@RequestParam String contentId) throws Exception {
        return service.searchLocationDetail(contentId);
    }

    /**
     * 여행지 리뷰 작성
     */
    @PostMapping("/detail/reviews")
    public ResponseEntity<ResponseDto> addLocationReview(@RequestBody ReviewDto review, HttpServletRequest request) throws Exception {
        int memberId = Integer.parseInt(jwtService.getMemberId(request.getHeader(AUTH_HEADER)));
        review.setMemberId(memberId);

        return service.saveLocationReview(review);
    }

    /**
     * 여행지 리뷰조회
     */
    @GetMapping("/detail/{contentId}/reviews")
    public ResponseEntity<ResponseDto> locationReviews(@PathVariable String contentId) throws Exception {
        log.info(contentId);
        return service.locationReviews(contentId);
    }

    @PostMapping("/{contentId}/like")
    public ResponseEntity<ResponseDto> addLocationLike(@PathVariable String contentId,
                                                       HttpServletRequest request) throws Exception {
        String memberId = jwtService.getMemberId(request.getHeader(AUTH_HEADER));

        Map<String, String> param = new HashMap<>();
        param.put("contentId", contentId);
        param.put("type", "A");
        param.put("memberId", memberId);
        return service.saveLocationLike(param);
    }
}
