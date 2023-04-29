package com.ssafy.enjoytrip.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.member.dto.MemberDto;
import com.ssafy.enjoytrip.member.model.service.MemberService;
import com.ssafy.enjoytrip.response.MemberResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/member")
public class MemberController {
	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	// Email / Nickname Check
	@GetMapping("/check/{check}")
	public ResponseEntity<MemberResponseDto> check(@PathVariable String check) throws Exception {
		log.info("controller : check Email or Nickname = {}", check);
		return memberService.check(check);
	}

	@PostMapping("/signup")
	public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberDto member) throws Exception {
		log.info("controller : signup = {}", member);
		return memberService.signup(member);
	}

	@PostMapping("/login")
	public ResponseEntity<MemberResponseDto> login(@RequestBody MemberDto member, HttpSession session)
			throws Exception {
		log.info("controller : login = {}", member);
		return memberService.login(member, session);
	}

	@GetMapping("/logout")
	public ResponseEntity<MemberResponseDto> login(HttpSession session) throws Exception {
		log.info("controller : logout = {}", (MemberDto) session.getAttribute("memberInfo"));
		return memberService.logout(session);
	}
	
	@DeleteMapping("/secession")
	public ResponseEntity<MemberResponseDto> secession(@RequestBody MemberDto member) throws Exception {
		log.info("controller : secession = {}", member);
		return memberService.secession(member);
	}
	
	/****************************** MyPage *************************************/
	@GetMapping("/mypage/{nickname}/info")
	public ResponseEntity<MemberResponseDto> info(@PathVariable String nickname) throws Exception {
		log.info("controller : mypage-info = {}", nickname);
		return memberService.info(nickname);
	}
	
	
	
	
	
	
}
