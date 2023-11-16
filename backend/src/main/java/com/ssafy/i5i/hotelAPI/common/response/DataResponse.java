package com.ssafy.i5i.hotelAPI.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class DataResponse<T> extends CommonResponse {
    public T data;
    public DataResponse(int code, String message) {
        super(code, message);
    }

    public DataResponse(int code, String message, T data) {
        super(code, message);
        this.data = data;
    }
}
