package com.ssafy.enjoytrip.attraction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BoundSearchDto {
    private String neLat;
    private String neLng;
    private String swLat;
    private String swLng;
    private List<Integer> contentType;
}
