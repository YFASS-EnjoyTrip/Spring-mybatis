package com.ssafy.enjoytrip.member.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

	@Override
	public ResponseEntity<ResponseDto> login(MemberDto member, HttpSession session) throws Exception {
		MemberDto m = mapper.selectMember(member);
		String msg;
		log.info("Service : login = {}", m);
		if (m != null) {
			session.setAttribute("memberInfo", m);
			msg = "로그인 정상적으로 수행";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, member.getNickname()));
		} else {
			msg = "서버에 에러가 발생했습니다.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(HttpStatus.OK.value(), msg, null));
		}
	}

	@Override
	public ResponseEntity<ResponseDto> logout(HttpSession session) throws Exception {
		log.info("Service : logout = {}", (MemberDto) session.getAttribute("memberInfo"));
		String msg = "로그아웃 정상적으로 수행";
		session.invalidate();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, null));
	}

	@Override
	public ResponseEntity<ResponseDto> signup(MemberDto member) {
		log.info("Service : signup = {}", member);
		String msg;
		try {
			mapper.insertMember(member);
			msg="회원가입 정상적으로 수행";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, null));
		} catch (Exception e) {
			msg="서버에 에러가 발생했습니다.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null));
		}
	}

	@Override
	public ResponseEntity<ResponseDto> check(String check) throws Exception {
		log.info("Service check Email or Nickname = {}", check);
		MemberDto isExist = mapper.selectMemberByCheck(check);
		String msg;
		if (isExist != null) {
			msg=check + " 은 이미 존재합니다.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null));
		} else
			msg=check + " 은 사용 가능합니다.";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, null));
	}

	@Override
	public ResponseEntity<ResponseDto> secession(MemberDto member) {
		log.info("Service : secession = {}", member);
		String msg;
		try {
			mapper.deleteMember(member);
			msg="회원 탈퇴가 정상적으로 이루어졌습니다.";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, null));
		} catch (Exception e) {
			msg="서버에 문제가 생겼습니다.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null));
		}
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
			msg = "회원정보 조회가 정상적으로 이루어졌습니다.";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, map));
		} catch (Exception e) {
			msg="서버에 문제가 발생했습니다.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null));
		}
	}

	@Override
	public ResponseEntity<ResponseDto> hotplace(String nickname) {
		String msg;
		log.info("Service : mypage-hotplace = {}", nickname);
		try {
			List<HotplaceDto> list = mapper.selectHotplaceByNickname(nickname);
			log.info("Service result : mypage-hotplace = {}", list);
			msg="작성한 핫플레이스 조회가 정상적으로 이루어졌습니다.";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, list));
		} catch (Exception e) {
			msg="서버에 문제가 발생했습니다.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null));
		}
	}

	@Override
	public ResponseEntity<ResponseDto> editPassword(Map<String, String> map) {
		String msg;
		log.info("Service : mypage-editPassword = {}", map);
		try {
			mapper.updateMemberPassword(map);
			msg="비밀번호 수정이 정상적으로 이루어졌습니다.";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, null));
		} catch (Exception e) {
			msg="서버에 문제가 발생했습니다.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null));
		}
	}

	@Override
	public ResponseEntity<ResponseDto> editBio(Map<String, String> map) {
		String msg;
		log.info("Service : mypage-editBio = {}", map);
		try {
			mapper.updateMemberBio(map);
			msg="한줄소개 수정이 정상적으로 이루어졌습니다.";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, null));
		} catch (Exception e) {
			msg="서버에 문제가 발생했습니다.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null));
		}
	}

	@Override
	public ResponseEntity<ResponseDto> editProfileImg(Map<String, String> map) {
		String msg;
		log.info("Service : mypage-editProfileImg = {}", map);
		try {
			mapper.updateMemberProfileImg(map);
			msg="프로필사진 수정이 정상적으로 이루어졌습니다.";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null));
		} catch (Exception e) {
			msg="서버에 문제가 발생했습니다.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null));
		}
	}
}
