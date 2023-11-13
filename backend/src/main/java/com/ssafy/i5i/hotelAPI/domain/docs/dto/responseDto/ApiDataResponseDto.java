package com.ssafy.i5i.hotelAPI.domain.docs.dto.responseDto;

import com.ssafy.i5i.hotelAPI.domain.docs.dto.VariableDto;
import com.ssafy.i5i.hotelAPI.domain.docs.entity.ApiData;
import com.ssafy.i5i.hotelAPI.domain.docs.entity.ApiType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ApiDataResponseDto {
    private String title;
    private String content;
    private String method;
    private String returnType;
    private String contentType;
    private String endpoint;
    private String returnExample;
    private List<VariableDto> request;
    private List<VariableDto> response;


}
