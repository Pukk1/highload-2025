package com.iver.datasimulator.integration.api;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IotControllerApi {
    @POST("/api/v1/iot/device/{deviceId}/data")
    Call<String> sendData(@Path("deviceId") Integer deviceId, @Body JsonObject body);
}
