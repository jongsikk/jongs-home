package com.jongsik2.home.influxapi.controller;

import com.jongsik2.home.influxapi.config.MqttConfig;
import com.jongsik2.home.influxapi.dto.MqttDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/control")
public class DeviceControlController {
    private final MqttConfig.MqttGateway mqttGateway;

    @PostMapping
    public void controlDevice(@RequestBody MqttDto mqttDto) {
        mqttGateway.sentMqtt(mqttDto.getPayload(), mqttDto.getTopic());
    }
}
