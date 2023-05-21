package com.ssafy.enjoytrip.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;

@Getter
@ToString
public class MemberDto {
	private String memberId;

	@Setter
	@Email
	private String email;
	@Setter
	private String password;
	@Setter
	private String nickname;
	@Setter
	private String profileImg;
	@Setter
	private String bio;
	private String joinDate;
	@Setter
	private String updateDate;
}
