package com.ssafy.enjoytrip.hotplace.model.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.ssafy.enjoytrip.hotplace.dto.HotplaceDto;
import com.ssafy.enjoytrip.response.ResponseDto;

public interface HotplaceService {

	ResponseEntity<ResponseDto> list();

	ResponseEntity<ResponseDto> detail(String id);

	ResponseEntity<ResponseDto> write(HotplaceDto hotplace);

	ResponseEntity<ResponseDto> edit(HotplaceDto hotplace);

	ResponseEntity<ResponseDto> delete(String id);

	ResponseEntity<ResponseDto> like(Map<String, String> map);

}
