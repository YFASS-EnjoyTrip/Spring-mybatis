package com.ssafy.enjoytrip.hotplace.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.hotplace.dto.HotplaceDto;
import com.ssafy.enjoytrip.hotplace.model.mapper.HotplaceMapper;
import com.ssafy.enjoytrip.response.MemberResponseDto;
import com.ssafy.enjoytrip.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class HotplaceServiceImpl implements HotplaceService {

	private final HotplaceMapper hotplaceMapper;
	@Override
	public ResponseEntity<ResponseDto> list() {
		ResponseDto res = new ResponseDto();
		try {
			List<HotplaceDto> list = hotplaceMapper.selectAllHotplace();
			log.info("service : list = {}",list);
			res.setStatus(HttpStatus.OK.value());
			res.setMessage("핫플레이스 목록 조회 정상적으로 수행");
			res.setResult(list);
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}catch (Exception e) {
			res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			res.setMessage("서버에 문제가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	@Override
	public ResponseEntity<ResponseDto> detail(String id) {
		ResponseDto res = new ResponseDto();
		try {
			HotplaceDto h = hotplaceMapper.selectDetail(id);
			log.info("service : detail = {}", h);
			res.setStatus(HttpStatus.OK.value());
			res.setMessage("핫플레이스 상세 조회 정상적으로 수행");
			res.setResult(h);
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}catch (Exception e) {
			res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			res.setMessage("서버에 문제가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	@Override
	public ResponseEntity<ResponseDto> write(HotplaceDto hotplace) {
		ResponseDto res = new ResponseDto();
		try {
			log.info("service : write = {}", hotplace);
			hotplaceMapper.insertHotplace(hotplace);
			res.setStatus(HttpStatus.OK.value());
			res.setMessage("핫플레이스 게시글 작성 정상적으로 수행");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}catch (Exception e) {
			res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			res.setMessage("서버에 문제가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	@Override
	public ResponseEntity<ResponseDto> edit(HotplaceDto hotplace) {
		ResponseDto res = new ResponseDto();
		try {
			log.info("service : edit = {}", hotplace);
			hotplaceMapper.updateHotplace(hotplace);
			res.setStatus(HttpStatus.OK.value());
			res.setMessage("핫플레이스 게시글 수정 정상적으로 수행");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}catch (Exception e) {
			res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			res.setMessage("서버에 문제가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
}
