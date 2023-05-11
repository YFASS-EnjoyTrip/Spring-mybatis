package com.ssafy.enjoytrip.member.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.member.dto.MemberInfoDto;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.hotplace.dto.HotPlaceDto;
import com.ssafy.enjoytrip.member.dto.MemberDto;

@Mapper
public interface MemberMapper {

	MemberDto selectMember(MemberDto member) throws SQLException;

	void insertMember(MemberDto member) throws SQLException;

	MemberInfoDto selectMemberByCheck(String check) throws SQLException;

	void deleteMember(MemberDto member) throws SQLException;

	List<HotPlaceDto> selectHotPlaceByEmail(String nickname) throws SQLException;

	void updateMemberPassword(Map<String, String> map) throws SQLException;

	void updateMemberBio(Map<String, String> map) throws SQLException;

	void updateMemberProfileImg(Map<String, String> map) throws SQLException;

	List<Map<String, String>> selectLike(String nickname) throws SQLException;

	MemberDto findMemberByEmail(String email) throws SQLException;
}
