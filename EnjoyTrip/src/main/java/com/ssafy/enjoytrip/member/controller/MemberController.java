package com.ssafy.enjoytrip.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.member.dto.MemberDto;
import com.ssafy.enjoytrip.member.model.service.MemberService;
import com.ssafy.enjoytrip.response.MemberResponseDto;
import com.ssafy.enjoytrip.response.ResponseDto;

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
	public ResponseEntity<MemberResponseDto> logout(HttpSession session) throws Exception {
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
	public ResponseEntity<ResponseDto> info(@PathVariable String nickname) throws Exception {
		log.info("controller : mypage-info = {}", nickname);
		return memberService.info(nickname);
	}

	@GetMapping("/mypage/{nickname}/hotplace")
	public ResponseEntity<ResponseDto> hotplace(@PathVariable String nickname) throws Exception {
		log.info("controller : mypage-hotplace = {}", nickname);
		return memberService.hotplace(nickname);
	}
	
	@PutMapping("/mypage/{nickname}/edit/password")
	public ResponseEntity<ResponseDto> editPassword(@PathVariable String nickname, @RequestBody Map<String, String> pwd) throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("nickname", nickname);
		map.put("password", pwd.get("password"));
		log.info("controller : mypage-editPassword password = {}", map);
		return memberService.editPassword(map);
	}
	@PutMapping("/mypage/{nickname}/edit/bio")
	public ResponseEntity<ResponseDto> editBio(@PathVariable String nickname, @RequestBody Map<String, String> bio) throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("nickname", nickname);
		map.put("bio", bio.get("bio"));
		log.info("controller : mypage-editBio = {}", map);
		return memberService.editBio(map);
	}
	@PutMapping("/mypage/{nickname}/edit/profile-img")
	public ResponseEntity<ResponseDto> editProfileImg(@PathVariable String nickname, @RequestBody Map<String, String> img) throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("nickname", nickname);
		map.put("profileImg", img.get("profileImg"));
		log.info("controller : mypage-editProfileImg = {}", map);
		return memberService.editProfileImg(map);
	}

}
