package com.ssafy.enjoytrip.global.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.Map;

@Mapper
public interface LikeMapper {
    void insertLike(Map<String, String> param) throws SQLException;

    int selectLike(Map<String, String> param) throws SQLException;

    void deleteLike(Map<String, String> param) throws SQLException;
}
