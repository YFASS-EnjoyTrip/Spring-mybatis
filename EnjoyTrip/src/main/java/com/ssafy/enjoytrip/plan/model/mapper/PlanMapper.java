package com.ssafy.enjoytrip.plan.model.mapper;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.plan.dto.PlanForm;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface PlanMapper {
    void createPlan(PlanForm form) throws SQLException;
    void createPlanDays(Map<String, Object> param) throws SQLException;

    List<PlanForm> selectAllPlans(int memberId) throws SQLException;

    List<Map<String, Object>> selectPlanDetail(int planId) throws SQLException;

    Map<String, String> selectPlanInfo(int planId) throws SQLException;
}
