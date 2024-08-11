package com.jongsik2.home.influxapi.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.jongsik2.home.influxapi.properties.InfluxDBProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InfluxDBConfig {
    private final InfluxDBProperties influxDBProperties;

    @Bean
    public InfluxDBClient influxDBClient() {
        return InfluxDBClientFactory.create(
                influxDBProperties.getUrl(),
                influxDBProperties.getToken().toCharArray(),
                influxDBProperties.getOrg(),
                influxDBProperties.getBucket()
        );
    }
}
