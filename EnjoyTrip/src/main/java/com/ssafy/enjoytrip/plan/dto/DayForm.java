package com.ssafy.enjoytrip.plan.dto;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class DayForm {
    private String planId;
    private List<DayDto> items;
}
