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

	MemberDto login(MemberDto member) throws Exception;

	void signup(MemberDto member) throws Exception;

	void check(String check) throws Exception;

	void secession(MemberDto member) throws Exception;

	MemberInfoDto info(String email) throws Exception;

	List<HotPlaceDto> getHotPlace(String email) throws Exception;

	void editPassword(Map<String, String> map) throws Exception;

	void editBio(Map<String, String> map) throws Exception;

	void editProfileImg(Map<String, String> map) throws Exception;

	List<Map<String, String>> like(Map<String, String> param) throws Exception;

    String findMemberIdByEmail(String email) throws Exception;

    void saveRefreshToken(String email, String refreshToken) throws Exception;

	MemberInfoDto findMemberInfoById(String memberId) throws Exception;

	void deleRefreshToken(String memberId) throws Exception;
}
