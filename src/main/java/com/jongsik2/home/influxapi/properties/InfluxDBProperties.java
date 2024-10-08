package com.jongsik2.home.influxapi.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.influxdb")
public class InfluxDBProperties {
    private String url;
    private String token;
    private String org;
    private String bucket;
}
