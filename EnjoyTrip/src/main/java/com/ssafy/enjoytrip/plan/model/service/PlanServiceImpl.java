package com.ssafy.enjoytrip.plan.model.service;

import com.ssafy.enjoytrip.plan.dto.DayDto;
import com.ssafy.enjoytrip.plan.dto.DayForm;
import com.ssafy.enjoytrip.plan.dto.PlanForm;
import com.ssafy.enjoytrip.plan.model.mapper.PlanMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanMapper planMapper;

    /**
     * 여행 플래너 생성
     */
    @Override
    public void savePlan(PlanForm form) throws Exception {
        planMapper.createPlan(form);
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
}
