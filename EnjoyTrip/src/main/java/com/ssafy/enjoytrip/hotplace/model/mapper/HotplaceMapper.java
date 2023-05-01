package com.ssafy.enjoytrip.hotplace.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.hotplace.dto.HotplaceDto;

@Mapper
public interface HotplaceMapper {

	List<HotplaceDto> selectAllHotplace() throws SQLException;

	HotplaceDto selectDetail(String id) throws SQLException;

	void insertHotplace(HotplaceDto hotplace) throws SQLException;

	void updateHotplace(HotplaceDto hotplace) throws SQLException;

	void deleteHotplace(String id) throws SQLException;

	void updateViewCount(String id) throws SQLException;
	
	@Transactional
	void updateHotplaceLike(Map<String, String> map) throws SQLException;

}
