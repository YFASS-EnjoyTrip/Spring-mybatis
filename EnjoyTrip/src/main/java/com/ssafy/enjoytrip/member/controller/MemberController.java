package com.ssafy.enjoytrip.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ssafy.enjoytrip.global.service.FileService;
import com.ssafy.enjoytrip.global.util.JwtTokenProvider;
import com.ssafy.enjoytrip.hotplace.dto.HotPlaceDto;
import com.ssafy.enjoytrip.member.dto.MemberInfoDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final FileService fileService;
	private final JwtTokenProvider jwtService;
	private String AUTH_HEADER = "Authorization";

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
	public ResponseEntity<ResponseDto> login(@RequestBody MemberDto member) throws Exception {
		Map<String, String> result = memberService.login(member);
		String jwtToken = result.get("token");

		// AccessToken Header
		HttpHeaders headers = new HttpHeaders();
		headers.set(AUTH_HEADER, "Bearer " + jwtToken);

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

	@GetMapping("/mypage/info")
	public ResponseEntity<ResponseDto> info(HttpServletRequest request) throws Exception {
		String email = jwtService.getEmail(request.getHeader(AUTH_HEADER));
		MemberInfoDto info = memberService.info(email);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto(HttpStatus.OK.value(), "회원정보 조회 성공", info));
	}

	@GetMapping("/mypage/hotplace")
	public ResponseEntity<ResponseDto> hotPlace(HttpServletRequest request) throws Exception {
		String email = jwtService.getEmail(request.getHeader(AUTH_HEADER));
		List<HotPlaceDto> result = memberService.getHotPlace(email);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto(HttpStatus.OK.value(), "게시물 조회 성공", result));
	}

	@PutMapping("/mypage/edit/password")
	public ResponseEntity<ResponseDto> editPassword(@RequestBody Map<String, String> param, HttpServletRequest request) throws Exception {
		String email = jwtService.getEmail(request.getHeader(AUTH_HEADER));

		Map<String, String> map = new HashMap<>();
		map.put("email", email);
		map.put("password", param.get("password"));

		memberService.editPassword(map);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto(HttpStatus.OK.value(), "비밀번호가 성공적으로 변경 되었습니다.", null));
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
