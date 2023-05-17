package com.ssafy.enjoytrip.member.dto;

import lombok.Data;

@Data
public class MemberInfoDto {
    private String email;
    private String nickname;
    private String profileImg;
    private String bio;
    private String joinDate;
}
