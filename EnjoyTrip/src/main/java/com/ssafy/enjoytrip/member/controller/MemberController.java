package com.ssafy.enjoytrip.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.member.dto.MemberDto;
import com.ssafy.enjoytrip.member.model.service.MemberService;
import com.ssafy.enjoytrip.response.MemberResponseDto;

@RestController
@RequestMapping("/member")
public class MemberController {
	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<MemberResponseDto> login(@RequestBody MemberDto member,HttpSession session, Model model) throws Exception{
		return memberService.login(member, session);
	}
	@GetMapping("/logout")
	public ResponseEntity<MemberResponseDto> login(HttpSession session) throws Exception{
		return memberService.logout(session);
	}
}
