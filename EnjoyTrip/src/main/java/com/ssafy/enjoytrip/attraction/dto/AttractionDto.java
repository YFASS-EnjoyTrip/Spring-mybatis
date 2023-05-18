package com.ssafy.enjoytrip.attraction.dto;

import lombok.Getter;

@Getter
public class AttractionDto {
    private int contentId;
    private int contentType;
    private String title;
    private String addr1;
    private String addr2;
    private String zipCode;
    private String tel;
    private String image;
    private int viewCount; // 확장 대비
    private int sidoCode;
    private int gugunCode;
    private Double lat;
    private Double lng;
    private int likeCount;
    private String overview;
}
