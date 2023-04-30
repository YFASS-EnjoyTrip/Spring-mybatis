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
import com.ssafy.enjoytrip.response.MemberResponseDto;
import com.ssafy.enjoytrip.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberMapper mapper;
	private final MemberResponseDto responseDto;

	@Override
	public ResponseEntity<MemberResponseDto> login(MemberDto member, HttpSession session) throws Exception {
		MemberDto m = mapper.selectMember(member);
		log.info("Service : login = {}", m);
		if (m != null) {
			session.setAttribute("memberInfo", m);
			return ResponseEntity.status(HttpStatus.OK).body(responseDto.successLogin(m));
		} else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto.failLogin());
	}

	@Override
	public ResponseEntity<MemberResponseDto> logout(HttpSession session) throws Exception {
		log.info("Service : logout = {}", (MemberDto) session.getAttribute("memberInfo"));
		session.invalidate();
		return ResponseEntity.status(HttpStatus.OK).body(responseDto.successLogout());
	}

	@Override
	public ResponseEntity<MemberResponseDto> signup(MemberDto member) {
		log.info("Service : signup = {}", member);
		try {
			mapper.insertMember(member);
			return ResponseEntity.status(HttpStatus.OK).body(responseDto.successSignup());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto.failSignup());
		}
	}

	@Override
	public ResponseEntity<MemberResponseDto> check(String check) throws Exception {
		log.info("Service check Email or Nickname = {}", check);
		MemberDto isExist = mapper.selectMemberByCheck(check);
		if (isExist != null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto.unavailable(check));
		} else
			return ResponseEntity.status(HttpStatus.OK).body(responseDto.available(check));
	}

	@Override
	public ResponseEntity<MemberResponseDto> secession(MemberDto member) {
		log.info("Service : secession = {}", member);
		try {
			mapper.deleteMember(member);
			return ResponseEntity.status(HttpStatus.OK).body(responseDto.successSecession());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto.failSecession());
		}
	}

	/****************************** MyPage *************************************/
	
	@Override
	public ResponseEntity<ResponseDto> info(String nickname) {
		ResponseDto res = new ResponseDto();
		log.info("Service : mypage-info = {}", nickname);
		try {
			MemberDto m = mapper.selectMemberByCheck(nickname);
			log.info("Service result : mypage-info = {}", mapper.selectMemberByCheck(nickname));
			Map<String, String> map = new HashMap<String, String>();
			map.put("nickname", m.getNickname());
			map.put("email", m.getEmail());
			map.put("bio", m.getBio());
			map.put("gender", m.getGender());
			res.setStatus(HttpStatus.OK.value());
			res.setMessage("회원정보 조회가 정상적으로 이루어졌습니다.");
			res.setResult(map);
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} catch (Exception e) {
			res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			res.setMessage("서버에 문제가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}

	@Override
	public ResponseEntity<ResponseDto> hotplace(String nickname) {
		ResponseDto res = new ResponseDto();
		log.info("Service : mypage-hotplace = {}", nickname);
		try {
			List<HotplaceDto> list = mapper.selectHotplaceByNickname(nickname);
			log.info("Service result : mypage-hotplace = {}", list);
			res.setStatus(HttpStatus.OK.value());
			res.setMessage("작성한 핫플레이스 조회가 정상적으로 이루어졌습니다.");
			res.setResult(list);
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} catch (Exception e) {
			res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			res.setMessage("서버에 문제가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}

	@Override
	public ResponseEntity<ResponseDto> editPassword(Map<String, String> map) {
		ResponseDto res = new ResponseDto();
		log.info("Service : mypage-editPassword = {}", map);
		try {
			mapper.updateMemberPassword(map);
			res.setStatus(HttpStatus.OK.value());
			res.setMessage("비밀번호 수정이 정상적으로 이루어졌습니다.");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} catch (Exception e) {
			res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			res.setMessage("서버에 문제가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}

	@Override
	public ResponseEntity<ResponseDto> editBio(Map<String, String> map) {
		ResponseDto res = new ResponseDto();
		log.info("Service : mypage-editBio = {}", map);
		try {
			mapper.updateMemberBio(map);
			res.setStatus(HttpStatus.OK.value());
			res.setMessage("한줄소개 수정이 정상적으로 이루어졌습니다.");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} catch (Exception e) {
			res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			res.setMessage("서버에 문제가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
}
