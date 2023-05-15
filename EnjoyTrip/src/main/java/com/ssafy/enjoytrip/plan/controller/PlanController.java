package com.ssafy.enjoytrip.plan.controller;

import com.ssafy.enjoytrip.global.service.FileService;
import com.ssafy.enjoytrip.global.util.JwtTokenProvider;
import com.ssafy.enjoytrip.member.dto.MemberInfoDto;
import com.ssafy.enjoytrip.member.model.service.MemberService;
import com.ssafy.enjoytrip.plan.dto.DayForm;
import com.ssafy.enjoytrip.plan.dto.PlanForm;
import com.ssafy.enjoytrip.plan.model.service.PlanService;
import com.ssafy.enjoytrip.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/planner")
@RequiredArgsConstructor
@CrossOrigin
public class PlanController {

    private final PlanService planService;
    private final MemberService memberService;
    private final JwtTokenProvider jwtService;
    private final FileService fileService;

    private String AUTH_HEADER = "Authorization";
    /**
     * 여행 플래너 랜덤 생성
     */
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> addPlan(@RequestBody Map<String, Object> param, HttpServletRequest request) throws Exception {
        String email = jwtService.getEmail(request.getHeader(AUTH_HEADER));
        int memberId = memberService.findMemberIdByEmail(email);

        param.put("memberId", memberId);
        List<Map<String, Object>> result = planService.createPlan(param);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK.value(), "플랜 생성 성공", result));
    }

    /**
     * 여행 플래너 생성
     * 23.05.12 요구사항 변경으로 폐쇄
     */
//    @PostMapping("/write")
//    public ResponseEntity<ResponseDto> addPlan(@RequestPart("data") PlanForm form, @RequestParam("file") MultipartFile file) {
//        try {
//
//            // JWT 토큰 도입 시, memberId 뽑아내는 로직 필요
//            // 했다치고
//            String imageUrl = fileService.uploadFile(file);
//            log.info(imageUrl);
//            form.setImage(imageUrl);
//            planService.savePlan(form);
//
//            Map<String, Object> result = new HashMap<>();
//            result.put("planId", form.getPlanId());
//
//            return ResponseEntity.status(HttpStatus.CREATED)
//                    .body(new ResponseDto(HttpStatus.CREATED.value(), "플랜 생성 완료", result));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDto> getPlans() throws Exception {
        // JWT 토큰 받았다 치고 회원ID 1로 테스트
        int memberId = 4;
        List<PlanForm> result = planService.findPlans(memberId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK.value(), "여행 플래너 조회 완료", result));
    }

    /**
     * 여행 상세 일정 생성
     */
    @PostMapping("/write/day")
    public ResponseEntity<ResponseDto> addPlanDays(@RequestBody DayForm form) {
        try {
            planService.savePlanDays(form);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED.value(), "여행 일정이 정상적으로 처리 되었습니다", null));
    }

    /**
     * 여행 상세 일정 조회
     */
    @GetMapping("/list/{planId}")
    public ResponseEntity<ResponseDto> getPlanDetail(@PathVariable int planId) throws Exception {
        //회원권한 검사 했다 치고
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("planInfo", planService.findPlanInfo(planId));
        result.put("dayInfo", planService.findPlanDetail(planId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK.value(), "여행 상세 조회 완료", result));

    }

    //TODO
    /**
     * 플래너 기본정보 수정
     */
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updatePlanInfo(@RequestPart("data") PlanForm form, @RequestParam("file") MultipartFile file) {
        try {

            // JWT 토큰 도입 시, memberId 뽑아내는 로직 필요
            // 했다치고

            // 파일 insert 후, file_id 가져오기

            planService.updatePlan(form);

            Map<String, Object> result = new HashMap<>();
            result.put("planId", form.getPlanId());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(HttpStatus.OK.value(), "플랜 수정 완료", result));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 플래너 삭제
     */
    @DeleteMapping("/delete/{planId}")
    public ResponseEntity<ResponseDto> removePlan(@PathVariable int planId) {
        try {
            planService.deletePlan(planId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ResponseDto(HttpStatus.NO_CONTENT.value(), "정상적으로 삭제 되었습니다.", null));
    }

    /**
     * 플랜너 상세 일정 수정
     */
    @PutMapping("/update/day")
    public ResponseEntity<ResponseDto> updatePlanDetail(@RequestBody Map<String, Object> form) {
        try {
            log.info("form={}", form.toString());
//            planService.updatePlanDetail(form);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK.value(), "일정 수정 완료", null));
    }
}
