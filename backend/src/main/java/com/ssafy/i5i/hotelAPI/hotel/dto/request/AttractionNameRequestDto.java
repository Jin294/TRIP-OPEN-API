package com.ssafy.i5i.hotelAPI.hotel.dto.request;

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
