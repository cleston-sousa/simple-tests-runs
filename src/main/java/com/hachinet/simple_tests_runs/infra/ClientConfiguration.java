package com.hachinet.simple_tests_runs.infra;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

}
