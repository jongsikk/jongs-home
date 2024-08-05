package com.jongsik2.home.influxapi.service;

import com.jongsik2.home.influxapi.dto.EnergyDto;

import java.util.List;

public interface EnergyService {
    List<EnergyDto> energyDeviceList();

    List<EnergyDto> energy(String entityId);
}
