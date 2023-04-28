package com.ssafy.enjoytrip.attraction.controller;

import com.ssafy.enjoytrip.attraction.model.service.AttractionService;
import com.ssafy.enjoytrip.response.AttractionResponseDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // TODO
    @GetMapping("/search")
    public ResponseEntity<AttractionResponseDto> searchLocations(
            @PathVariable String keyword, @PathVariable(required = false) String sido,
            @PathVariable(required = false) String gugun, @PathVariable(required = false) int contentType)
            throws Exception {
        return null;
    }
}
