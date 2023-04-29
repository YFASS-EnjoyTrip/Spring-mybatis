package com.ssafy.enjoytrip.hotplace.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.hotplace.dto.HotplaceDto;
import com.ssafy.enjoytrip.member.dto.MemberDto;

@Mapper
public interface HotplaceMapper {

	List<HotplaceDto> selectAllHotplace() throws SQLException;

}
