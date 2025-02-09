package com.cloud.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseApi {
    private String message;
    private Object data;
}
