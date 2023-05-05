package com.ssafy.enjoytrip.member.model.service;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.global.dto.FileDto;
import com.ssafy.enjoytrip.global.mapper.FileMapper;
import com.ssafy.enjoytrip.hotplace.dto.HotplaceDto;
import com.ssafy.enjoytrip.member.dto.MemberDto;
import com.ssafy.enjoytrip.member.model.mapper.MemberMapper;
import com.ssafy.enjoytrip.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberMapper mapper;
	private final PasswordEncoder encoder;

	// JWT 토큰을 위한 로직
	public UserDetails findMemberByEmail(String email) throws Exception {
		MemberDto member = mapper.findMemberByEmail(email);
		if (member == null) {
			throw new UsernameNotFoundException("User Not Found");
		}

		return new User(member.getEmail(), member.getPassword(), null);
	}

	// TODO JWT 로직 추가 예정
	@Override
	public Map<String, String> login(MemberDto member) throws Exception {

		MemberDto m = mapper.findMemberByEmail(member.getEmail());

		if (m == null || !encoder.matches(member.getPassword(), m.getPassword())) {
			throw new BadCredentialsException("이메일, 비밀번호를 확인 해주세요.");
		}

		// JWT 토큰 로직이 들어올 자리

		Map<String, String> result = new HashMap<>();
		result.put("nickName", m.getNickname());
		result.put("profileImg", m.getProfileImg());
		result.put("bio", m.getBio());
		result.put("gender", m.getGender());

		return result;
	}


	// TODO JWT 도입 시, 수정
	@Override
	public ResponseEntity<ResponseDto> logout(HttpSession session) throws Exception {
		log.info("Service : logout = {}", (MemberDto) session.getAttribute("memberInfo"));
		String msg = "로그아웃 정상적으로 수행";
		session.invalidate();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, null));
	}

	@Override
	public void signup(MemberDto member) throws Exception {

		log.info("Service : signup = {}", member);
		if (mapper.selectMemberByCheck(member.getEmail()) != null) {
			throw new IllegalArgumentException("중복된 이메일이 존재합니다.");
		}

		member.setPassword(encoder.encode(member.getPassword()));
		mapper.insertMember(member);
	}

	@Override
	public void check(String check) throws Exception {
		log.info("Service check Email or Nickname = {}", check);
		MemberDto isExist = mapper.selectMemberByCheck(check);
		if (isExist != null) {
			throw new IllegalArgumentException("이미 존재 하는 이메일 입니다");
		}
	}

	@Override
	public void secession(MemberDto member) throws Exception {
		log.info("Service : secession = {}", member);
		mapper.deleteMember(member);
	}

	/****************************** MyPage *************************************/

	@Override
	public ResponseEntity<ResponseDto> info(String nickname) {
		log.info("Service : mypage-info = {}", nickname);
		String msg;
		try {
			MemberDto m = mapper.selectMemberByCheck(nickname);
			log.info("Service result : mypage-info = {}", mapper.selectMemberByCheck(nickname));
			Map<String, String> map = new HashMap<String, String>();
			map.put("nickname", m.getNickname());
			map.put("email", m.getEmail());
			map.put("bio", m.getBio());
			map.put("gender", m.getGender());
			map.put("profileImage", m.getProfileImg());

			msg = "회원정보 조회가 정상적으로 이루어졌습니다.";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, map));
		} catch (Exception e) {
			msg = "서버에 문제가 발생했습니다.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null));
		}
	}

	@Override
	public ResponseEntity<ResponseDto> hotplace(String nickname) {
		String msg;
		log.info("Service : mypage-hotplace = {}", nickname);
		try {
			List<HotplaceDto> list = mapper.selectHotplaceByNickname(nickname);
			log.info("Service result : mypage-hotplace = {}", list);
			msg = "작성한 핫플레이스 조회가 정상적으로 이루어졌습니다.";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, list));
		} catch (Exception e) {
			msg = "서버에 문제가 발생했습니다.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null));
		}
	}

	@Override
	public ResponseEntity<ResponseDto> editPassword(Map<String, String> map) {
		String msg;
		log.info("Service : mypage-editPassword = {}", map);
		try {
			mapper.updateMemberPassword(map);
			msg = "비밀번호 수정이 정상적으로 이루어졌습니다.";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, null));
		} catch (Exception e) {
			msg = "서버에 문제가 발생했습니다.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null));
		}
	}

	@Override
	public ResponseEntity<ResponseDto> editBio(Map<String, String> map) {
		String msg;
		log.info("Service : mypage-editBio = {}", map);
		try {
			mapper.updateMemberBio(map);
			msg = "한줄소개 수정이 정상적으로 이루어졌습니다.";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, null));
		} catch (Exception e) {
			msg = "서버에 문제가 발생했습니다.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null));
		}
	}

	@Override
	public ResponseEntity<ResponseDto> editProfileImg(Map<String, String> map) throws SQLException {
		String msg;
		mapper.updateMemberProfileImg(map);
		log.info("Service : mypage-editProfileImg = {}", map);
		msg = "프로필 사진 변경 정상적으로 수행";
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, null));
	}

	@Override
	public ResponseEntity<ResponseDto> like(String nickname) {
		String msg;
		log.info("Service : mypage-like = {}", nickname);
		try {
			List<Map<String, String>> map = mapper.selectLike(nickname);
			msg = "좋아요한 목록 조회가 정상적으로 이루어졌습니다.";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, map));
		} catch (Exception e) {
			e.printStackTrace();
			msg = "서버에 문제가 발생했습니다.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null));
		}
	}
}
