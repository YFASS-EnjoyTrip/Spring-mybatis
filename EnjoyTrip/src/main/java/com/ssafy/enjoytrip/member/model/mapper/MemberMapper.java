package com.ssafy.enjoytrip.member.model.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.member.dto.MemberDto;

@Mapper
public interface MemberMapper {

	MemberDto selectMember(MemberDto member) throws SQLException;

}
