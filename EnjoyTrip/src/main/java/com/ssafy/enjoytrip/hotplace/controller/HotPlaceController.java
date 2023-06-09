package com.ssafy.enjoytrip.hotplace.controller;

import java.util.HashMap;
import java.util.Map;

import com.ssafy.enjoytrip.global.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.hotplace.dto.HotPlaceDto;
import com.ssafy.enjoytrip.hotplace.model.service.HotplaceService;
import com.ssafy.enjoytrip.response.ResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/hotplace")
public class HotPlaceController {
	private final HotplaceService hotplaceService;
	private final FileService fileService;
	@GetMapping("/list")
	public ResponseEntity<ResponseDto> list() throws Exception {
		log.info("controller : hotplace - list");
		return hotplaceService.list();
	}

	@GetMapping("/detail/{id}")
	public ResponseEntity<ResponseDto> detail(@PathVariable String id) throws Exception {
		log.info("controller : hotplace - detail = {}", id);
		return hotplaceService.detail(id);
	}

	@PostMapping("/write")
	public ResponseEntity<ResponseDto> write(@RequestPart(value = "hotplace") HotPlaceDto hotPlace, @RequestPart(value = "file") MultipartFile[] files) throws Exception {
		log.info("controller : hotplace - write = {}", hotPlace);
		return hotplaceService.write(hotPlace, files);
	}

	@PutMapping("/edit")
	public ResponseEntity<ResponseDto> edit(@RequestBody HotPlaceDto hotplace) throws Exception {
		log.info("controller : hotplace - edit = {}", hotplace);
		return hotplaceService.edit(hotplace);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> delete(@RequestBody Map<String, String> map) throws Exception {
		log.info("controller : hotplace - delete = {}", map);
		return hotplaceService.delete(map.get("id"));
	}
	
	@PostMapping("/like/{memberId}/{id}")
	public ResponseEntity<ResponseDto> like(@PathVariable String memberId, @PathVariable String id) throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("contentId",id);
		log.info("controller : like = {}", map);
		return hotplaceService.like(map);
	}

}
