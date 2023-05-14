package com.ssafy.enjoytrip.hotplace.dto;

import java.util.List;

import com.ssafy.enjoytrip.global.dto.FileDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
// id, member_id, place_title, place_content, content_img, created_at
@Getter
@ToString
public class HotPlaceDto {
	private String id;
	private String memberId;
	private String nickname;
	@Setter
	private String placeTitle;
	@Setter
	private String placeContent;
	@Setter
	private String contentImg;
	private String createdAt;
	@Setter
	private String likeCount;
	@Setter
	private String viewCount;
	@Setter
	private List<FileDto> fileInfos;
}
