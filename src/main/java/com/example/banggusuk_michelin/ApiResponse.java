package com.example.banggusuk_michelin;

public class ApiResponse <T>{
    private static int SUCCESS = 200;

    private ApiHeader apiHeader;
    private ApiBody<T> apiBody;

    public ApiResponse(ApiHeader apiHeader, ApiBody<T> apiBody) {
        this.apiHeader = apiHeader;
        this.apiBody = apiBody;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(new ApiHeader(SUCCESS, "SUCCESS"), new ApiBody<>(data));
    }
}
