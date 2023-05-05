package com.ssafy.enjoytrip.global.util;


import com.ssafy.enjoytrip.member.model.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secretKey}")
    private String secretKey;
    private final long validityInMilliseconds = 3600 * 1000; // 토큰 유효시간
    private MemberService memberService;

    public String createToken(String userEmail) {
        Claims claims = Jwts.claims().setSubject(userEmail);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

//    public Authentication getAuthentication(String token) throws Exception {
//        UserDetails userDetails = memberService.findMemberByEmail(getUserEmail(token));
//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//    }


    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

}
