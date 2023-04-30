package com.ssafy.enjoytrip.attraction.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private int reviewId;
    private int contentId;
    private int memberId;
    private String nickName;
    private int rate;
    private String content;
    private String createdAt;
}
