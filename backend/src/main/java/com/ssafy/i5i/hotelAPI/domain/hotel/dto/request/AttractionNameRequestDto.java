package com.ssafy.i5i.hotelAPI.domain.hotel.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AttractionNameRequestDto {
    private String attractionName;
    private Double distance;
    private String sorted;
}
