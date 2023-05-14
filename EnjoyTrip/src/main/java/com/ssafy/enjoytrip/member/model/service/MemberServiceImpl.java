package com.ssafy.enjoytrip.member.model.service;

import java.sql.SQLException;
import java.util.*;

import javax.servlet.http.HttpSession;

import com.ssafy.enjoytrip.global.util.JwtTokenProvider;
import com.ssafy.enjoytrip.member.dto.MemberInfoDto;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.hotplace.dto.HotPlaceDto;
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
	private final JwtTokenProvider jwtTokenProvider;

	// TODO JWT 로직 추가 예정
	@Override
	public Map<String, String> login(MemberDto member) throws Exception {
		MemberDto m = mapper.findMemberByEmail(member.getEmail());
		if (m == null || !BCrypt.checkpw(member.getPassword(), m.getPassword())) {
			throw new IllegalArgumentException("이메일, 비밀번호를 확인 해주세요.");
		}

		// JWT 토큰 로직이 들어올 자리
		String token = jwtTokenProvider.generateToken(m.getEmail());
		log.info("token={}", token);
		Map<String, String> result = new HashMap<>();

		result.put("token", token);
		result.put("nickName", m.getNickname());
		result.put("profileImg", m.getProfileImg());

		return result;
	}

	// TODO JWT 도입 시, 수정
	@Override
	public ResponseEntity<ResponseDto> logout(HttpSession session) throws Exception {
		String msg = "로그아웃 정상적으로 수행";
		session.invalidate();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, null));
	}

	@Override
	public void signup(MemberDto member) throws Exception {
		if (mapper.selectMemberByCheck(member.getEmail()) != null) {
			throw new IllegalArgumentException("중복된 이메일이 존재합니다.");
		}

		member.setPassword(BCrypt.hashpw(member.getPassword(), BCrypt.gensalt()));
		mapper.insertMember(member);
	}

	@Override
	public void check(String check) throws Exception {
		MemberInfoDto member = mapper.selectMemberByCheck(check);
		if (member != null) {
			throw new IllegalArgumentException("이미 존재 하는 이메일 입니다");
		}
	}

	@Override
	public void secession(MemberDto member) throws Exception {
		mapper.deleteMember(member);
	}

	/****************************** MyPage *************************************/

	@Override
	public MemberInfoDto info(String email) throws Exception {
		return mapper.selectMemberByCheck(email);
	}

	@Override
	public List<HotPlaceDto> getHotPlace(String email) throws Exception {
		return mapper.selectHotPlaceByEmail(email);

	}

	@Override
	public void editPassword(Map<String, String> param) throws Exception {
		mapper.updateMemberPassword(param);
	}

	@Override
	public void editBio(Map<String, String> map) throws Exception {
		mapper.updateMemberBio(map);
	}

	@Override
	public void editProfileImg(Map<String, String> map) throws Exception {
		mapper.updateMemberProfileImg(map);
	}

	@Override
	public List<Map<String, String>> like(String email) throws Exception{
		return mapper.selectLike(email);
	}
}
