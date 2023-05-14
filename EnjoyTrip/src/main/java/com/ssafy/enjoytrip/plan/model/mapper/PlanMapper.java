package com.ssafy.enjoytrip.plan.model.mapper;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.plan.dto.PlanForm;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface PlanMapper {
    List<Map<String, String>> createPlan(Map<String, Object> param) throws SQLException;

    void createPlanDays(Map<String, Object> param) throws SQLException;

    List<PlanForm> selectAllPlans(int memberId) throws SQLException;

    List<Map<String, Object>> selectPlanDetail(int planId) throws SQLException;

    Map<String, String> selectPlanInfo(int planId) throws SQLException;

    void updatePlan(PlanForm form) throws SQLException;

    void deletePlan(int planId) throws SQLException;

    void deletePlanDay(Map<String, Object> form) throws SQLException;

    void insertPlanDay(Map<String, Object> form) throws SQLException;
}
