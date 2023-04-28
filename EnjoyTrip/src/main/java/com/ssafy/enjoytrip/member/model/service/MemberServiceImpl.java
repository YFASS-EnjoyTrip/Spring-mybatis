package com.ssafy.enjoytrip.member.model.service;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.member.dto.MemberDto;
import com.ssafy.enjoytrip.member.model.mapper.MemberMapper;
import com.ssafy.enjoytrip.response.MemberResponseDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberMapper mapper;
	private final MemberResponseDto responseDto;

	@Override
	public ResponseEntity<MemberResponseDto> login(MemberDto member, HttpSession session) throws Exception {
		MemberDto m = mapper.selectMember(member);
		if (m != null) {
			session.setAttribute("memberInfo", m);
			return ResponseEntity.status(HttpStatus.OK).body(responseDto.successLogin(m));
		}
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto.failLogin());
	}

	@Override
	public ResponseEntity<MemberResponseDto> logout(HttpSession session) throws Exception {
		session.invalidate();
		return ResponseEntity.status(HttpStatus.OK).body(responseDto.successLogout());
	}
}
