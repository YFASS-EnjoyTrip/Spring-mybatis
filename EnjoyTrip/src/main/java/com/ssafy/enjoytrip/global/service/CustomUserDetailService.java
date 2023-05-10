package com.ssafy.enjoytrip.global.service;

import com.ssafy.enjoytrip.member.dto.MemberDto;
import com.ssafy.enjoytrip.member.model.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component("userDetailService")
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private MemberMapper memberMapper;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            MemberDto member = memberMapper.findMemberByEmail(email);
            if (member == null) {
                throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다.");
            }

            return new User(member.getEmail(), member.getPassword(), new ArrayList<>());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
