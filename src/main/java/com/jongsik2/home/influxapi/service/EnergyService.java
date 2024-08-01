package com.jongsik2.home.influxapi.service;

import com.jongsik2.home.influxapi.dto.Energy;

import java.util.List;

public interface EnergyService {
    List<Energy> energyDeviceList();

    List<Energy> energy(String entityId);
}
