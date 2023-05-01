package com.ssafy.enjoytrip.global.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {
	void insertLike(Map<String, String> param) throws SQLException;

	int selectLike(Map<String, String> param) throws SQLException;

	void deleteLike(Map<String, String> param) throws SQLException;
}
