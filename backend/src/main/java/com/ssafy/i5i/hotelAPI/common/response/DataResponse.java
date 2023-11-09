package com.ssafy.i5i.hotelAPI.common.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DataResponse<T> extends CommonResponse {
    private T data;
    public DataResponse(int code, String message) {
        super(code, message);
    }

    public DataResponse(int code, String message, T data) {
        super(code, message);
        this.data = data;
    }
}
