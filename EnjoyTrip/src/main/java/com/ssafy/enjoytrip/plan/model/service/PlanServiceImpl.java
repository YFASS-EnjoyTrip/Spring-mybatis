package com.ssafy.enjoytrip.plan.model.service;

import com.ssafy.enjoytrip.plan.dto.PlanForm;
import com.ssafy.enjoytrip.plan.model.mapper.PlanMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

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
}
