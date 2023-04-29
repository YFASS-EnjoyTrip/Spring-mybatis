package com.ssafy.enjoytrip.member.model.service;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.member.dto.MemberDto;
import com.ssafy.enjoytrip.response.MemberResponseDto;

public interface MemberService {

	ResponseEntity<MemberResponseDto> login(MemberDto member, HttpSession session) throws Exception;

	ResponseEntity<MemberResponseDto> logout(HttpSession session) throws Exception;

	ResponseEntity<MemberResponseDto> signup(MemberDto member);

	ResponseEntity<MemberResponseDto> check(String check) throws Exception;

	ResponseEntity<MemberResponseDto> secession(MemberDto member);

	ResponseEntity<MemberResponseDto> info(String nickname);

}
