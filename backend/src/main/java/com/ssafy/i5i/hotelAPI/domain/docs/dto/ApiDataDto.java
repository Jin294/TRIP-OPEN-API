package com.ssafy.i5i.hotelAPI.domain.docs.dto;

import lombok.*;

@Getter
@Setter
public class ApiDataDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApiDataList {
        private String title;
        private String content;
        private String method;
        private String return_type;
        private String content_type;
        private String endpoint;
        private String return_example;
    }



}
