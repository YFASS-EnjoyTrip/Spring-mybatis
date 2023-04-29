package com.ssafy.enjoytrip.attraction.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private int reviewId;
    private String nickName;
    private int rate;
    private String content;
}
