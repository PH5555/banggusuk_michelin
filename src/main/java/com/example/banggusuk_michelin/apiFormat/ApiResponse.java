package com.example.banggusuk_michelin.apiFormat;

public class ApiResponse <T>{
    private final static String SUCCESS = "success";
    private final static String FAIL = "fail";

    private String status;
    private T data;

    public ApiResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(SUCCESS, data);
    }

    public static <T> ApiResponse<T> fail(T data) {
        return new ApiResponse<>(FAIL, data);
    }

    @Override
    public String toString() {
        return "status : " + this.status + ", data : " + this.data;
    }
}
