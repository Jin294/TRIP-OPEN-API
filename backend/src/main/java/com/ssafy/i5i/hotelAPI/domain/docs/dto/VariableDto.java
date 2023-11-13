package com.ssafy.i5i.hotelAPI.domain.docs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class VariableDto {
    private String title;
    private String type;
    private String detail;
    private Boolean is_essential;
    private Boolean is_parameter;
}
