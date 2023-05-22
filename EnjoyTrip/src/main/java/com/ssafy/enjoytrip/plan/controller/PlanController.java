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
        String memberId = jwtService.getMemberId(request.getHeader(AUTH_HEADER));
        param.put("memberId", memberId);
        String result = planService.createPlan(param);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED.value(), "플랜 생성 성공", result));
    }


    @PostMapping("/reroll")
    public ResponseEntity<ResponseDto> reCreatePlan(@RequestBody Map<String, Object> param, HttpServletRequest request) throws Exception {
        String memberId = jwtService.getMemberId(request.getHeader(AUTH_HEADER));
        param.put("memberId", memberId);

        String result = planService.reCreatePlan(param);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED.value(), "플랜 생성 성공", result));
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDto> getPlans(HttpServletRequest request) throws Exception {
        String memberId = jwtService.getMemberId(request.getHeader(AUTH_HEADER));
        log.info("memberId={}", memberId);
        List<PlanForm> result = planService.findPlans(Integer.parseInt(memberId));
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
    public ResponseEntity<ResponseDto> getPlanDetail(@PathVariable String planId, HttpServletRequest request) throws Exception {
        String memberId = jwtService.getMemberId(request.getHeader(AUTH_HEADER));

        Map<String, String> param = new HashMap<>();
        param.put("memberId", memberId);
        param.put("planId", planId);

        Map<String, Object> result = new LinkedHashMap<>();
        Map<String, String> planInfo = planService.findPlanInfo(param);
        result.put("planInfo", planInfo);

        log.info("result={}", result);
        param.put("startDate", String.valueOf(planInfo.get("startDate")));
        param.put("endDate", String.valueOf(planInfo.get("endDate")));

        result.put("dayInfo", planService.findPlanDetail(param));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK.value(), "여행 상세 조회 완료", result));

    }

    //TODO
    /**
     * 플래너 기본정보 수정
     */
    @PutMapping("/update/planInfo")
    public ResponseEntity<ResponseDto> updatePlanInfo(@RequestPart("data") PlanForm form, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {

            // JWT 토큰 도입 시, memberId 뽑아내는 로직 필요
            String memberId = jwtService.getMemberId(request.getHeader(AUTH_HEADER));

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
    @DeleteMapping("/{planId}")
    public ResponseEntity<ResponseDto> removePlan(@PathVariable String planId, HttpServletRequest request) throws Exception {
        String memberId = jwtService.getMemberId(request.getHeader(AUTH_HEADER));
        Map<String, String> param = new HashMap<>();

        try {
            param.put("memberId", memberId);
            param.put("planId", planId);
            planService.deletePlan(param);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ResponseDto(HttpStatus.NO_CONTENT.value(), "정상적으로 삭제 되었습니다.", null));
    }

    /**
     * 플랜너 상세 일정 수정
     */
    @PutMapping("/update/{planId}")
    public ResponseEntity<ResponseDto> updatePlanDetail(@PathVariable String planId,
                                                        @RequestBody Map<String, List<List<DayForm>>> form,
                                                        HttpServletRequest request) throws Exception
    {
        String memberId = jwtService.getMemberId(request.getHeader(AUTH_HEADER));
        Map<String, Object> param = new HashMap<>();
        param.put("memberId", memberId);
        param.put("planId", planId);

        planService.updatePlanDetail(param, form);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK.value(), "일정 수정 완료", null));
    }
}
