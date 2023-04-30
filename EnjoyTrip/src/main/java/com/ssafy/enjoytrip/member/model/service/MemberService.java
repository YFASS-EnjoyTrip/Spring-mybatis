package com.ssafy.enjoytrip.member.model.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;

import com.ssafy.enjoytrip.member.dto.MemberDto;
import com.ssafy.enjoytrip.response.MemberResponseDto;
import com.ssafy.enjoytrip.response.ResponseDto;

public interface MemberService {

	ResponseEntity<MemberResponseDto> login(MemberDto member, HttpSession session) throws Exception;

	ResponseEntity<MemberResponseDto> logout(HttpSession session) throws Exception;

	ResponseEntity<MemberResponseDto> signup(MemberDto member);

	ResponseEntity<MemberResponseDto> check(String check) throws Exception;

	ResponseEntity<MemberResponseDto> secession(MemberDto member);

	ResponseEntity<ResponseDto> info(String nickname);

	ResponseEntity<ResponseDto> hotplace(String nickname);

	ResponseEntity<ResponseDto> editPassword(Map<String, String> map);

	ResponseEntity<ResponseDto> editBio(Map<String, String> map);

	ResponseEntity<ResponseDto> editProfileImg(Map<String, String> map);

}
