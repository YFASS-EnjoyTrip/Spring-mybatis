package com.ssafy.enjoytrip.global.util;

import com.ssafy.enjoytrip.member.dto.MemberDto;
import com.ssafy.enjoytrip.member.model.mapper.MemberMapper;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;
    private final MemberMapper mapper;
    private final long validTime= 3600 * 1000 * 24; // 토큰 유효시간 (1시간)
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject("user")
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public MemberDto authenticate(String token) throws Exception {
        String email = getEmail(token);
        MemberDto member = mapper.findMemberByEmail(email);
        if (member == null) {
            throw new Exception("USER NOT FOUND");
        }

        return member;
    }

    public String getEmail(String token) throws Exception {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token.replace("Bearer ", ""));

        return claims.getBody().get("email", String.class);
    }
}
