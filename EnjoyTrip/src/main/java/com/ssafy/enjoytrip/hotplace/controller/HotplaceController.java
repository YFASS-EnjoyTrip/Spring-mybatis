package com.ssafy.enjoytrip.hotplace.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.hotplace.model.service.HotplaceService;
import com.ssafy.enjoytrip.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/hotplace")
public class HotplaceController {
	private final HotplaceService hotplaceService;
	
	@GetMapping("/list")
	public ResponseEntity<ResponseDto> list() throws Exception {
		log.info("controller : hotplace - list");
		return hotplaceService.list();
	}
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<ResponseDto> detail(@PathVariable String id) throws Exception {
		log.info("controller : hotplace - list = {}", id);
		return hotplaceService.detail(id);
	}
	
}
