package com.ssafy.enjoytrip.member.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.hotplace.dto.HotplaceDto;
import com.ssafy.enjoytrip.member.dto.MemberDto;

@Mapper
public interface MemberMapper {

	MemberDto selectMember(MemberDto member) throws SQLException;

	void insertMember(MemberDto member) throws SQLException;

	MemberDto selectMemberByCheck(String check) throws SQLException;

	void deleteMember(MemberDto member) throws SQLException;

	List<HotplaceDto> selectHotplaceByNickname(String nickname) throws SQLException;

}
