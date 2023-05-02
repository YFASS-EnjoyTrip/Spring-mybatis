package com.ssafy.enjoytrip.member.model.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;

import com.ssafy.enjoytrip.member.dto.MemberDto;
import com.ssafy.enjoytrip.response.ResponseDto;

public interface MemberService {

	ResponseEntity<ResponseDto> login(MemberDto member, HttpSession session) throws Exception;

	ResponseEntity<ResponseDto> logout(HttpSession session) throws Exception;

	ResponseEntity<ResponseDto> signup(MemberDto member);

	ResponseEntity<ResponseDto> check(String check) throws Exception;

	ResponseEntity<ResponseDto> secession(MemberDto member);

	ResponseEntity<ResponseDto> info(String nickname);

	ResponseEntity<ResponseDto> hotplace(String nickname);

	ResponseEntity<ResponseDto> editPassword(Map<String, String> map);

	ResponseEntity<ResponseDto> editBio(Map<String, String> map);

	ResponseEntity<ResponseDto> editProfileImg(Map<String, Object> map);

	ResponseEntity<ResponseDto> like(String nickname);

}
