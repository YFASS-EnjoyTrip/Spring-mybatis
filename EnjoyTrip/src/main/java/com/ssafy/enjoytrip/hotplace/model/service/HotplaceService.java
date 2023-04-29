package com.ssafy.enjoytrip.hotplace.model.service;

import org.springframework.http.ResponseEntity;

import com.ssafy.enjoytrip.response.ResponseDto;

public interface HotplaceService {

	ResponseEntity<ResponseDto> list();

}
