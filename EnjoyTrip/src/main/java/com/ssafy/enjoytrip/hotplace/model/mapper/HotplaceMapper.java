package com.ssafy.enjoytrip.hotplace.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.hotplace.dto.HotPlaceDto;

@Mapper
public interface HotplaceMapper {

	List<HotPlaceDto> selectAllHotplace() throws SQLException;

	HotPlaceDto selectDetail(String id) throws SQLException;

	void insertHotplace(HotPlaceDto hotplace) throws SQLException;

	void updateHotplace(HotPlaceDto hotplace) throws SQLException;

	void deleteHotplace(String id) throws SQLException;

	void updateViewCount(String id) throws SQLException;
	
	@Transactional
	void updateHotplaceLike(Map<String, String> map) throws SQLException;

	void updateContentImg(Map<String, String> img) throws SQLException;

}
