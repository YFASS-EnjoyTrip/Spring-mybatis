package com.ssafy.enjoytrip.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class MemberDto {
	private String memberId;
	@Setter
	private String email;
	@Setter
	private String password;
	@Setter	
	private String nickname;
	private String gender;
	@Setter
	private String profileImg;
	@Setter
	private String bio;
	private String joinDate;
	@Setter
	private String updateDate;

	private String role;
}
