package com.ssafy.enjoytrip.hotplace.model.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.hotplace.dto.HotPlaceDto;
import com.ssafy.enjoytrip.response.ResponseDto;

public interface HotplaceService {

	ResponseEntity<ResponseDto> list();

	ResponseEntity<ResponseDto> detail(String id);

	ResponseEntity<ResponseDto> write(HotPlaceDto hotplace, MultipartFile[] files);

	ResponseEntity<ResponseDto> edit(HotPlaceDto hotplace);

	ResponseEntity<ResponseDto> delete(String id);

	ResponseEntity<ResponseDto> like(Map<String, String> map);

}
