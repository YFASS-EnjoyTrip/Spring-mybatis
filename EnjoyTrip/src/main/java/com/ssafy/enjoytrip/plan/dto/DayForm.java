package com.ssafy.enjoytrip.plan.dto;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class DayForm {
    private String contentId;
    private String type;
    private String title;
    private String overView;
    private String image;
    private String lat;
    private String lng;
    private String rate;
    private String likeCount;

    private int day;
    private int order;
}
