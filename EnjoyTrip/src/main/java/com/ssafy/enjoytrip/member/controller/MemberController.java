package com.ssafy.enjoytrip.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ssafy.enjoytrip.global.service.FileService;
import com.ssafy.enjoytrip.global.util.JwtToken;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.member.dto.MemberDto;
import com.ssafy.enjoytrip.member.model.service.MemberService;
import com.ssafy.enjoytrip.response.ResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/member")
@AllArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final FileService fileService;

	// Email / Nickname Check
	@GetMapping("/check/{check}")
	public ResponseEntity<ResponseDto> check(@PathVariable String check) throws Exception {
		memberService.check(check);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto(HttpStatus.OK.value(), "사용 가능 합니다", null));
	}

	@PostMapping("/signup")
	public ResponseEntity<ResponseDto> signup(@RequestBody MemberDto member) throws Exception {
		memberService.signup(member);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(HttpStatus.CREATED.value(), "회원가입 성공", null));
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseDto> login(@RequestBody MemberDto member, HttpServletResponse response) throws Exception {
		Map<String, String> result = memberService.login(member);
		String jwtToken = result.get("token");

		// AccessToken Header
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + jwtToken);

		// RefreshToken Cookie
//		Cookie refreshTokenCookie = new Cookie("refreshToken", jwtToken.getRefreshToken());
//		refreshTokenCookie.setHttpOnly(true);
//		refreshTokenCookie.setMaxAge(60 * 60 * 24); // 1일 만료
//		refreshTokenCookie.setPath("/"); // Set the cookie path
//		response.addCookie(refreshTokenCookie);

		result.remove("token");

		return ResponseEntity
				.status(HttpStatus.OK)
				.headers(headers)
				.body(new ResponseDto(HttpStatus.OK.value(), "로그인 성공", result));
	}


	@GetMapping("/logout")
	public ResponseEntity<ResponseDto> logout(HttpSession session) throws Exception {
		return memberService.logout(session);
	}

	@DeleteMapping("/secession")
	public ResponseEntity<ResponseDto> secession(@RequestBody MemberDto member) throws Exception {
		memberService.secession(member);
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.body(new ResponseDto(HttpStatus.NO_CONTENT.value(), "회원탈퇴 성공", null));
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
	public ResponseEntity<ResponseDto> editProfileImg(@PathVariable String nickname, @RequestPart(value = "file") MultipartFile file) throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("nickname", nickname);

		String imageUrl = fileService.uploadFile(file);
		map.put("image", imageUrl);
		log.info("controller : mypage-editProfileImg = {}", map);
		return memberService.editProfileImg(map);
	}
	
	@GetMapping("/mypage/{nickname}/like")
	public ResponseEntity<ResponseDto> like(@PathVariable String nickname) throws Exception {
		log.info("controller : mypage-like");
		return memberService.like(nickname);
	} 

}
