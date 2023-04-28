package com.ssafy.enjoytrip.response;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@NoArgsConstructor
public class AttractionResponseDto {
    private int status;
    private String message;
    private Object result;

    public AttractionResponseDto successGetLocations(List<AttractionDto> list) {
        AttractionResponseDto res = new AttractionResponseDto();

        res.setStatus(HttpStatus.OK.value());
        res.setMessage("요청 정상적으로 수행");
        res.setResult(list);
        return res;
    }

    public AttractionResponseDto failGetLocations() {
        AttractionResponseDto res = new AttractionResponseDto();

        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        res.setMessage("서버에 문제가 발생했습니다");
        return res;
    }

    public AttractionResponseDto successSearchLocations(List<AttractionDto> list) {
        AttractionResponseDto res = new AttractionResponseDto();

        res.setStatus(HttpStatus.OK.value());
        res.setMessage("요청 정상적으로 수행");
        res.setResult(list);
        return res;
    }
}