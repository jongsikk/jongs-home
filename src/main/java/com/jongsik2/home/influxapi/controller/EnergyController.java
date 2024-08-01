package com.jongsik2.home.influxapi.controller;

import com.jongsik2.home.influxapi.dto.Energy;
import com.jongsik2.home.influxapi.service.EnergyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/energy")
public class EnergyController {
    private final EnergyService energyService;

    @GetMapping(value = "/devices")
    public List<Energy> energyDeviceList() {
        return energyService.energyDeviceList();
    }

    @GetMapping
    public List<Energy> energyList(@RequestParam String entityId) {
        return energyService.energy(entityId);
    }
}
