package com.ssafy.enjoytrip.plan.controller;

import com.ssafy.enjoytrip.global.util.FileUtil;
import com.ssafy.enjoytrip.plan.dto.DayDto;
import com.ssafy.enjoytrip.plan.dto.DayForm;
import com.ssafy.enjoytrip.plan.dto.PlanForm;
import com.ssafy.enjoytrip.plan.model.service.PlanService;
import com.ssafy.enjoytrip.plan.model.service.PlanServiceImpl;
import com.ssafy.enjoytrip.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/planner")
@AllArgsConstructor
public class PlanController {

    private final PlanService planService;
    private final FileUtil fileUtil;

    @PostMapping("/write")
    public ResponseEntity<ResponseDto> addPlan(@RequestPart("data") PlanForm form, @RequestParam("file") MultipartFile file) {
        try {

            // JWT 토큰 도입 시, memberId 뽑아내는 로직 필요
            // 했다치고

            // 파일 insert 후, file_id 가져오기
            int fileId = fileUtil.upload(file);
            if (fileId != 0) form.setImage(fileId);

            planService.savePlan(form);

            Map<String, Object> result = new HashMap<>();
            result.put("planId", form.getPlanId());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDto(HttpStatus.CREATED.value(), "플랜 생성 완료", result));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @PostMapping("/write/day")
    public ResponseEntity<ResponseDto> addPlanDays(@RequestBody DayForm form) {
        for (DayDto item : form.getItems()) {
            log.info("item={}", item.getContentId());
        }

        return null;
    }

}
