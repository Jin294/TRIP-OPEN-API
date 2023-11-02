package com.ssafy.i5i.hotelAPI.domain.hotel.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AttractionCoordinateRequestDto {
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Long distance;
    private String sorted;
}