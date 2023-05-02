package com.ssafy.enjoytrip.plan.model.mapper;

import com.ssafy.enjoytrip.plan.dto.PlanForm;
import org.apache.ibatis.jdbc.SQL;

import java.sql.SQLException;

public interface PlanMapper {
    void createPlan(PlanForm form) throws SQLException;
}
