package com.ssafy.enjoytrip.plan.dto;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import lombok.Getter;

import java.util.List;

@Getter
public class DayDto {
    private String planId;
    private String contentId;
    private int day;
    private int order;
    private List<AttractionDto> items;
}
