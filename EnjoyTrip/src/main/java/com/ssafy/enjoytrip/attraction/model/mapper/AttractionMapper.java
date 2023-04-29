package com.ssafy.enjoytrip.attraction.model.mapper;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.attraction.dto.ReviewDto;
import com.ssafy.enjoytrip.attraction.dto.SearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface AttractionMapper {

    List<AttractionDto> getLocations(String keyWord) throws SQLException;

    List<AttractionDto> searchLocations(SearchDto searchDto) throws SQLException;

    AttractionDto searchLocationDetail(String contentId);

    List<ReviewDto> getLocationReviews(String contentId);
}
