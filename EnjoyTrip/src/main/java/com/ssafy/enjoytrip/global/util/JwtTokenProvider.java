package com.ssafy.enjoytrip.global.util;

import com.ssafy.enjoytrip.exception.UnAuthorizedException;
import com.ssafy.enjoytrip.member.dto.MemberDto;
import com.ssafy.enjoytrip.member.model.mapper.MemberMapper;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;
    private final MemberMapper mapper;
    private static final String AUTH_HEADER = "Authorization";
    private static final int ACCESS_TOKEN_EXPIRE_MINUTES = 1000 * 60 * 30 * 10; // 300분
    private static final int REFRESH_TOKEN_EXPIRE_MINUTES = 1000 * 60 * 60 * 24 * 7; // 일주일

    private byte[] generateKey() {
        byte[] key = null;
        try {
            key = secretKey.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
        }

        return key;
    }

    public <T> String createAccessToken(String key, T data) {
        return create(key, data, "access-token", ACCESS_TOKEN_EXPIRE_MINUTES);
    }

    public <T> String createRefreshToken(String key, T data) {
        return create(key, data, "refresh-token", REFRESH_TOKEN_EXPIRE_MINUTES);
    }

    public <T> String create(String key, T data, String subject, long expire) {
        Claims claims = Jwts.claims()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expire));

        // 저장할 data의 key, value
        claims.put(key, data);

        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact(); // 직렬화 처리.

        return jwt;
    }

    public boolean checkToken(String jwt) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Map<String, Object> get(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String jwt = request.getHeader(AUTH_HEADER);
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secretKey.getBytes("UTF-8")).parseClaimsJws(jwt);
        } catch (Exception e) {
            throw new UnAuthorizedException();
        }
        Map<String, Object> value = claims.getBody();
        return value;
    }

//    public String getMemberId() {
//        return (String) this.get("member").get("memberId");
//    }

    public MemberDto authenticate(String token) throws Exception {
        String memberId = getMemberId(token);
        MemberDto member = mapper.findMemberById(memberId);
        log.info("member={}", member);
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

    public String getMemberId(String token) throws Exception {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(this.generateKey())
                .parseClaimsJws(token.replace("Bearer ", ""));

        return claims.getBody().get("memberId", String.class);
    }
}
