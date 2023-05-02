package com.ssafy.enjoytrip.plan.model.service;

import com.ssafy.enjoytrip.plan.dto.PlanForm;
import org.springframework.stereotype.Service;


public interface PlanService {

    void savePlan(PlanForm form) throws Exception;
}
