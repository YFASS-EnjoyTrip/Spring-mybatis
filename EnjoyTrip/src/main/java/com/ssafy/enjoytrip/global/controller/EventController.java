package com.ssafy.enjoytrip.global.controller;


import com.ssafy.enjoytrip.global.dto.Item;
import com.ssafy.enjoytrip.global.service.EventService;
import com.ssafy.enjoytrip.global.util.JwtTokenProvider;
import com.ssafy.enjoytrip.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class EventController {

    private final JwtTokenProvider jwtService;
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<ResponseDto> event(HttpServletRequest request) throws Exception {
//        String memberId = jwtService.getMemberId(request.getHeader("Authorization"));
        log.info("OK");
        // 1. memberId 검증 로직 추가

        // 2.
        Item item = eventService.play();
        log.info("item={}", item);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK.value(), item.getValue(), null));
    }
}
