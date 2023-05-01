package com.ssafy.enjoytrip.hotplace.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
// id, member_id, place_title, place_content, content_img, created_at
@Getter
@ToString
public class HotplaceDto {
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
}
