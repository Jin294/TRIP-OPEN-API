package com.ssafy.i5i.hotelAPI.domain.docs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VariableDto {
    private String title;
    private String type;
    private String detail;
    private Boolean isEssential;
    private Boolean isParameter;
}
