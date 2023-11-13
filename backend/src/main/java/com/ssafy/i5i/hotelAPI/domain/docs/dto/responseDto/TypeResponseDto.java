package com.ssafy.i5i.hotelAPI.domain.docs.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TypeResponseDto {
    private Long id;
    private String title;
    private String detail;
}
