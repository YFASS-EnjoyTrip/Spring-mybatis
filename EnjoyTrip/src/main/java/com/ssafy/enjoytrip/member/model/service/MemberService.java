package com.ssafy.enjoytrip.member.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.ssafy.enjoytrip.hotplace.dto.HotPlaceDto;
import com.ssafy.enjoytrip.member.dto.MemberInfoDto;
import org.springframework.http.ResponseEntity;

import com.ssafy.enjoytrip.member.dto.MemberDto;
import com.ssafy.enjoytrip.response.ResponseDto;

public interface MemberService {

	Map<String, String> login(MemberDto member) throws Exception;

	ResponseEntity<ResponseDto> logout(HttpSession session) throws Exception;

	void signup(MemberDto member) throws Exception;

	void check(String check) throws Exception;

	void secession(MemberDto member) throws Exception;

	MemberInfoDto info(String email) throws Exception;

	List<HotPlaceDto> getHotPlace(String email) throws Exception;

	void editPassword(Map<String, String> map) throws Exception;

	ResponseEntity<ResponseDto> editBio(Map<String, String> map);

	ResponseEntity<ResponseDto> editProfileImg(Map<String, String> map) throws SQLException;

	ResponseEntity<ResponseDto> like(String nickname);

}
