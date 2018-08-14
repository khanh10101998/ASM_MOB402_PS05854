package com.example.mrrs.mob402_asm_ps05854;

import com.example.mrrs.mob402_asm_ps05854.model.ServerRequest;
import com.example.mrrs.mob402_asm_ps05854.model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {
    @POST("learn-login-register/") Call<ServerResponse> operation(@Body ServerRequest request);
}
