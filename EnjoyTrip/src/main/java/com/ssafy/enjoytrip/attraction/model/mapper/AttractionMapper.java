package com.ssafy.enjoytrip.attraction.model.mapper;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.attraction.dto.ReviewDto;
import com.ssafy.enjoytrip.attraction.dto.SearchDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.jdbc.SQL;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface AttractionMapper {

    List<AttractionDto> getLocations(String keyWord) throws SQLException;

    List<AttractionDto> searchLocations(SearchDto searchDto) throws SQLException;

    AttractionDto searchLocationDetail(String contentId) throws SQLException;

    List<ReviewDto> getLocationReviews(String contentId) throws SQLException;

    void insertLocationReview(ReviewDto review) throws SQLException;

    void insertLocationLike(Map<String, String> param) throws SQLException;

    void updateLocationLike(String contentId) throws SQLException;
}
