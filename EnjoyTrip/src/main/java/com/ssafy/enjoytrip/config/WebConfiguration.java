package com.ssafy.enjoytrip.config;

import com.ssafy.enjoytrip.global.util.JwtInterceptor;
import com.ssafy.enjoytrip.global.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@MapperScan(basePackages = { "com.ssafy.**.mapper" })
public class WebConfiguration implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor(jwtTokenProvider))
                .excludePathPatterns("/**", "/member/signup", "/member/login", "/locations", "/locations/**");
    }


}
