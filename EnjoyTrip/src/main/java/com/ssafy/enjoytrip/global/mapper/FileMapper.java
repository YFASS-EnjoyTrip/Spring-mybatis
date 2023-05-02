package com.ssafy.enjoytrip.global.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.global.dto.FileDto;

import java.sql.SQLException;
import java.util.Map;

@Mapper
public interface FileMapper {

	void insertFileInfo(FileDto fileDto) throws SQLException;

	void insertHotplaceFileMap(Map<String, Object> map) throws SQLException;

	String selectFileIdByHotplaceId(String hotplacePK) throws SQLException;

    void deletePlanImage(int planId) throws SQLException;
}
