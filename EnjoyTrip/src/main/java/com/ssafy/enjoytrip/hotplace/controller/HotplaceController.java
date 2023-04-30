package com.ssafy.enjoytrip.hotplace.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.hotplace.dto.HotplaceDto;
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

	@PostMapping("/write")
	public ResponseEntity<ResponseDto> write(@RequestBody HotplaceDto hotplace) throws Exception {
		log.info("controller : hotplace - list = {}", hotplace);
		return hotplaceService.write(hotplace);
	}

	@PutMapping("/edit")
	public ResponseEntity<ResponseDto> edit(@RequestBody HotplaceDto hotplace) throws Exception {
		log.info("controller : hotplace - edit = {}", hotplace);
		return hotplaceService.edit(hotplace);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> delete(@RequestBody Map<String, String> map) throws Exception {
		log.info("controller : hotplace - delete = {}", map);
		return hotplaceService.delete(map.get("id"));
	}

}
