package com.ssafy.enjoytrip.plan.controller;

import com.ssafy.enjoytrip.plan.dto.PlanForm;
import com.ssafy.enjoytrip.plan.model.service.PlanService;
import com.ssafy.enjoytrip.plan.model.service.PlanServiceImpl;
import com.ssafy.enjoytrip.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/planner")
@AllArgsConstructor
public class PlanController {

    private final PlanService planService;
    @PostMapping("/write")
    public ResponseEntity<ResponseDto> addPlan(@RequestBody PlanForm form) {
        try {

            // JWT 토큰 도입 시, memberId 뽑아내는 로직 필요

            // 파일 insert 후, file_id 가져오기
            int fileId = 1;
            form.setImage(fileId);
            planService.savePlan(form);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDto(HttpStatus.CREATED.value(), "플랜 생성 완료", form.getPlanId()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
