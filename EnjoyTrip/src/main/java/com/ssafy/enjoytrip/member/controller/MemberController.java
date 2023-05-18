package com.ssafy.enjoytrip.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ssafy.enjoytrip.global.service.FileService;
import com.ssafy.enjoytrip.global.util.JwtTokenProvider;
import com.ssafy.enjoytrip.hotplace.dto.HotPlaceDto;
import com.ssafy.enjoytrip.member.dto.MemberInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.member.dto.MemberDto;
import com.ssafy.enjoytrip.member.model.service.MemberService;
import com.ssafy.enjoytrip.response.ResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {
	private final MemberService memberService;
	private final FileService fileService;
	private final JwtTokenProvider jwtService;
	private String AUTH_HEADER = "Authorization";
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	// Email / Nickname Check
	@GetMapping("/check/{check}")
	public ResponseEntity<ResponseDto> check(@PathVariable String check) throws Exception {
		try{
			memberService.check(check);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(HttpStatus.CONFLICT.value(), "이미 사용중입니다", null));
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto(HttpStatus.OK.value(), "사용 가능 합니다", null));
	}

	@PostMapping("/signup")
	public ResponseEntity<ResponseDto> signup(@RequestBody MemberDto member) throws Exception {
		log.info("member={}", member);
		memberService.signup(member);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(HttpStatus.CREATED.value(), "회원가입 성공", null));
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseDto> login(@RequestBody MemberDto member, HttpServletResponse response) throws Exception {
		String message;
		MemberDto loginMember;
		try {
			loginMember = memberService.login(member);
			if (loginMember == null) {
				message = "이메일 또는 비밀번호를 확인해주세요.";
				return ResponseEntity
						.status(HttpStatus.UNAUTHORIZED)
						.body(new ResponseDto(HttpStatus.UNAUTHORIZED.value(), message, null));
			} else {
				String accessToken = jwtService.createAccessToken("memberId", loginMember.getMemberId());
				String refreshToken = jwtService.createRefreshToken("memberId", loginMember.getMemberId());

				memberService.saveRefreshToken(member.getEmail(), refreshToken);
				message = "로그인이 정상적으로 처리되었습니다.";
				HttpHeaders headers = new HttpHeaders();
				headers.add(AUTH_HEADER, accessToken);

				Cookie token = new Cookie("refreshToken", refreshToken);
				token.setHttpOnly(true);
				token.setMaxAge(7 * 24 * 60 * 60);
				token.setPath("/");

				ResponseCookie responseCookie = ResponseCookie.from("refreshToken", refreshToken)
						.httpOnly(true)
						.sameSite("None")
						.secure(true)
						.maxAge(7 * 24 * 60 * 60)
						.path("/")
						.build();

				headers.add(HttpHeaders.SET_COOKIE, responseCookie.toString());

				return ResponseEntity
						.status(HttpStatus.OK)
						.headers(headers)
						.body(new ResponseDto(HttpStatus.OK.value(), message, refreshToken));
			}

		} catch (Exception e) {
			message = e.getMessage();
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null));
		}
	}

	@GetMapping("/info/{memberId}")
	public ResponseEntity<ResponseDto> getInfo(@PathVariable String memberId, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			try {
				MemberInfoDto memberInfo = memberService.findMemberInfoById(memberId);
				resultMap.put("memberInfo", memberInfo);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			resultMap.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}

		return ResponseEntity.status(status)
				.body(new ResponseDto(status.value(), null, resultMap));
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

	@PutMapping("/mypage/edit/bio")
	public ResponseEntity<ResponseDto> editBio(@RequestBody Map<String, String> bio, HttpServletRequest request) throws Exception {
		String email = jwtService.getEmail(request.getHeader(AUTH_HEADER));

		Map<String, String> map = new HashMap<>();
		map.put("email", email);
		map.put("bio", bio.get("bio"));

		memberService.editBio(map);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto(HttpStatus.OK.value(), "자기소개가 정상적으로 변경 되었습니다.", null));
	}

	@PutMapping("/mypage/edit/profile-img")
	public ResponseEntity<ResponseDto> editProfileImg(@RequestPart(value = "file") MultipartFile file, HttpServletRequest request) throws Exception {
		String email = jwtService.getEmail(request.getHeader(AUTH_HEADER));

		Map<String, String> map = new HashMap<>();
		String imageUrl = fileService.uploadFile(file);

		map.put("image", imageUrl);
		map.put("email", email);

		memberService.editProfileImg(map);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto(HttpStatus.OK.value(), "프로필 사진이 정상적으로 변경 되었습니다", null));
	}

	@GetMapping("/mypage/like")
	public ResponseEntity<ResponseDto> like(HttpServletRequest request) throws Exception {
		String email = jwtService.getEmail(request.getHeader(AUTH_HEADER));
		memberService.like(email);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto(HttpStatus.OK.value(), "정상적으로 불러왔습니다", null));
	}

}
