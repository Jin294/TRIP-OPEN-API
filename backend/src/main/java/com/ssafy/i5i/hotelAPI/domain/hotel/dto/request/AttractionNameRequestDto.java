package com.ssafy.i5i.hotelAPI.domain.hotel.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AttractionNameRequestDto {
    private String attractionName;
    private Long distance;
    private String sorted;
}
