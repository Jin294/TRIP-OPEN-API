package com.sch.sch_elasticsearch.domain.global;

import com.sch.sch_elasticsearch.exception.response.CommonResponse;

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
