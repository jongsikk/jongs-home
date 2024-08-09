package com.jongsik2.home.influxapi.controller;

import com.jongsik2.home.influxapi.dto.EnergyDto;
import com.jongsik2.home.influxapi.service.impl.EnergyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/energy")
public class EnergyController {
    private final EnergyServiceImpl energyServiceImpl;

    @GetMapping(value = "/devices")
    public ResponseEntity<List<EnergyDto>> energyDeviceList() {
        return ResponseEntity.ok(energyServiceImpl.energyDeviceList());
    }

    @GetMapping
    public ResponseEntity<List<EnergyDto>> energyList(@RequestParam String entityId) {
        return ResponseEntity.ok(energyServiceImpl.energy(entityId));
    }
}
