package com.ssafy.enjoytrip.plan.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PlanDto {
    private int planId;
    private int memberId;
    private String image;
    private String title;
    private String startDate;
    private String endDate;
    private List<DayDto> planDetail;
}
