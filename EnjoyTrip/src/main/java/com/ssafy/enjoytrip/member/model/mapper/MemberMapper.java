package com.ssafy.enjoytrip.member.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

	void updateMemberPassword(Map<String, String> map) throws SQLException;

	void updateMemberBio(Map<String, String> map) throws SQLException;

	void updateMemberProfileImg(Map<String, String> map) throws SQLException;

	Map<String, String> selectLike(String nickname) throws SQLException;

}
