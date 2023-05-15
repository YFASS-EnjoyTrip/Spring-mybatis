package com.ssafy.enjoytrip.attraction.controller;

import com.ssafy.enjoytrip.attraction.dto.ReviewDto;
import com.ssafy.enjoytrip.attraction.dto.SearchDto;
import com.ssafy.enjoytrip.attraction.model.service.AttractionService;
import com.ssafy.enjoytrip.global.util.JwtTokenProvider;
import com.ssafy.enjoytrip.member.model.service.MemberService;
import com.ssafy.enjoytrip.response.AttractionResponseDto;
import com.ssafy.enjoytrip.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
@CrossOrigin
public class AttractionController {
    private final AttractionService service;
    private final JwtTokenProvider jwtService;
    private final MemberService memberService;
    private String AUTH_HEADER = "Authorization";
    /**
     * Default 조회, "서울" 을 기준으로 모든 여행지 조회
     * @return
     */
    @GetMapping
    public ResponseEntity<ResponseDto> locations() throws Exception {
        String keyWord = "서울%"; // 디폴트 값
        return service.getLocations(keyWord);
    }

    /**
     * 키워드, 조건 검색
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<ResponseDto> searchLocations(@RequestParam String keyword,
                                                       @RequestParam(required = false) String sido,
                                                       @RequestParam(required = false) String gugun,
                                                       @RequestParam(required = false) List<Integer> contentType) throws Exception {
        return service.searchLocations(new SearchDto(keyword, sido, gugun, contentType));
    }

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
    @PostMapping("/detail/{contentId}/reviews")
    public ResponseEntity<ResponseDto> addLocationReview(@RequestBody ReviewDto review) throws Exception {
        return service.saveLocationReview(review);
    }

    /**
     * 여행지 리뷰조회
     */
    @GetMapping("/detail/{contentId}/reviews")
    public ResponseEntity<ResponseDto> locationReviews(@PathVariable String contentId) throws Exception {
        return service.locationReviews(contentId);
    }

    @PostMapping("/{contentId}/like")
    public ResponseEntity<ResponseDto> addLocationLike(@PathVariable String contentId,
                                                       HttpServletRequest request) throws Exception {
        String email = jwtService.getEmail(request.getHeader(AUTH_HEADER));
        String memberId = String.valueOf(memberService.findMemberIdByEmail(email));

        Map<String, String> param = new HashMap<>();
        param.put("contentId", contentId);
        param.put("type", "A");
        param.put("memberId", memberId);
        return service.saveLocationLike(param);
    }
}
