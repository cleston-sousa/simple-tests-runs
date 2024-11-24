package com.hachinet.simple_tests_runs.infra.config;



import dev.openfeature.contrib.providers.flagd.FlagdProvider;
import dev.openfeature.sdk.exceptions.OpenFeatureError;
import dev.openfeature.sdk.OpenFeatureAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenFeatureBeans {

    @Bean
    public OpenFeatureAPI OpenFeatureAPI() {
        final OpenFeatureAPI openFeatureAPI = OpenFeatureAPI.getInstance();

        // Use flagd as the OpenFeature provider and use default configurations
        try {
            // Create a flagd instance with default options
            FlagdProvider flagd = new FlagdProvider();
// Set flagd as the OpenFeature Provider
            OpenFeatureAPI.getInstance().setProvider(flagd);

            //OpenFeatureAPI.getInstance().setProviderAndWait(flagd);

        } catch (OpenFeatureError e) {
            throw new RuntimeException("Failed to set OpenFeature provider", e);
        }


        return openFeatureAPI;
    }
}