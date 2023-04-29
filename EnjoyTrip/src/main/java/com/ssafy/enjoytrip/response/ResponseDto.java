package com.ssafy.enjoytrip.response;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.ssafy.enjoytrip.member.dto.MemberDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@NoArgsConstructor
public class ResponseDto {
	private int status;
	private String message;
	private Object result;
}
