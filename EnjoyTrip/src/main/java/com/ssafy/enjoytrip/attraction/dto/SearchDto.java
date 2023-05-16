package com.ssafy.enjoytrip.attraction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchDto{
    private String keyword;
    private String sido;
    private String gugun;
    private int pageSize;
    private int offset;
    private List<Integer> contentType;
}
