package com.ssafy.enjoytrip.plan.dto;

import lombok.Data;

@Data
public class PlanForm {
    private int memberId;
    private int planId;
    private String title;
    private int image;
    private String startDate;
    private String endDate;
}
