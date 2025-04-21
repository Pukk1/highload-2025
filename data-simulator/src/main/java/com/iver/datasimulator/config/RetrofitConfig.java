package com.iver.datasimulator.config;

import com.iver.datasimulator.config.properties.IotControllerProperties;
import com.iver.datasimulator.integration.api.IotControllerApi;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
@RequiredArgsConstructor
public class RetrofitConfig {

    private final IotControllerProperties iotControllerProperties;

    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder().baseUrl(iotControllerProperties.getUrl())
                .addConverterFactory(GsonConverterFactory.create()).client(new OkHttpClient.Builder().build()).build();
    }

    @Bean
    public IotControllerApi iotControllerApi() {
        return retrofit().create(IotControllerApi.class);
    }
}
