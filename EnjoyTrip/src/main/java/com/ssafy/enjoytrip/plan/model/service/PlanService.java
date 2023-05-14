package com.ssafy.enjoytrip.plan.model.service;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.plan.dto.DayForm;
import com.ssafy.enjoytrip.plan.dto.PlanForm;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public interface PlanService {

    void savePlan(PlanForm form) throws Exception;

    void savePlanDays(DayForm form) throws Exception;

    List<PlanForm> findPlans(int memberId) throws Exception;

    List<Map<String, Object>> findPlanDetail(int planId) throws Exception;

    Map<String, String> findPlanInfo(int planId) throws Exception;

    void updatePlan(PlanForm form) throws Exception;

    void deletePlan(int planId) throws Exception;

    void updatePlanDetail(Map<String, Object> form) throws Exception;

    List<Map<String, String>> createPlan(Map<String, Object> param) throws Exception;
}
