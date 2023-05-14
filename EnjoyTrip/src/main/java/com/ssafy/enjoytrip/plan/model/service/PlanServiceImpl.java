package com.ssafy.enjoytrip.plan.model.service;

import com.ssafy.enjoytrip.global.mapper.FileMapper;
import com.ssafy.enjoytrip.plan.dto.DayDto;
import com.ssafy.enjoytrip.plan.dto.DayForm;
import com.ssafy.enjoytrip.plan.dto.PlanForm;
import com.ssafy.enjoytrip.plan.model.mapper.PlanMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PlanServiceImpl implements PlanService {

    private final PlanMapper planMapper;
    private final FileMapper fileMapper;

    /**
     * 여행 플래너 생성
     */
    @Override
    public void savePlan(PlanForm form) throws Exception {
//        planMapper.createPlan(form);
    }

    @Override
    public void savePlanDays(DayForm form) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("planId", form.getPlanId());

        for (DayDto item : form.getItems()) {
            param.put("item", item);
            log.info("day={}", item.getDay());
            planMapper.createPlanDays(param);
        }
    }

    @Override
    public List<PlanForm> findPlans(int memberId) throws Exception {
        return planMapper.selectAllPlans(memberId);
    }

    @Override
    public List<Map<String, Object>> findPlanDetail(int planId) throws Exception {
        return planMapper.selectPlanDetail(planId);
    }

    @Override
    public Map<String, String> findPlanInfo(int planId) throws Exception {
        return planMapper.selectPlanInfo(planId);
    }

    @Override
    public void updatePlan(PlanForm form) throws Exception {
        planMapper.updatePlan(form);
    }

    // TODO DELETE 시 file_info 테이블의 image도 삭제해야합니다
    @Override
    public void deletePlan(int planId) throws Exception {
        planMapper.deletePlan(planId);
//        fileMapper.deletePlanImage(planId);
    }

    @Override
    public void updatePlanDetail(Map<String, Object> form) throws Exception {
        planMapper.deletePlanDay(form);
        planMapper.insertPlanDay(form);
    }

    @Override
    public List<Map<String ,String>> createPlan(Map<String, Object> param) throws Exception {
        LocalDate startDate = LocalDate.parse((CharSequence) param.get("startDate"));
        LocalDate endDate = LocalDate.parse((CharSequence) param.get("endDate"));

        String days = String.valueOf(ChronoUnit.DAYS.between(startDate, endDate));
        param.put("day1", days);
        param.put("day2", Integer.parseInt(days) * 2);
        // 1. days 만큼 관광지2, 밥2, 숙소1 뽑기, 마지막 날은 밥2, 관광지2
        List<Map<String, String>> plan = planMapper.createPlan(param);
        return plan;
        // 2. List<DayForm> 배치 해주기

        // 3. 화면에 출력 전, DB PUSH 후 return 해주기

//        planMapper.createPlan(param);
    }
}
