package com.jongsik2.home.influxapi.service.impl;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.query.dsl.Flux;
import com.jongsik2.home.influxapi.dto.EnergyDto;
import com.jongsik2.home.influxapi.entity.Energy;
import com.jongsik2.home.influxapi.service.EnergyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.influxdb.query.dsl.functions.restriction.Restrictions.*;

@Service
@RequiredArgsConstructor
public class EnergyServiceImpl implements EnergyService {
    private final InfluxDBClient influxDBClient;

    private static final String BUCKET = "homeassistant";
    private static final String MEASUREMENT = "kWh";

    public List<EnergyDto> energyDeviceList() {
        Flux value = Flux.from(BUCKET)
                .range(-1L, ChronoUnit.HOURS)
                .filter(and(
                        measurement().equal(MEASUREMENT),
                        field().equal("value")))
                .last();
        Flux friendlyNameStr = Flux.from(BUCKET)
                .range(-1L, ChronoUnit.HOURS)
                .filter(and(
                        measurement().equal(MEASUREMENT),
                        field().equal("friendly_name_str")))
                .last();

        Flux query = Flux.union(value, friendlyNameStr)
                .pivot(new String[]{"_time"}, new String[]{"_field"}, "_value")
                .groupBy("_measurement");

        return influxDBClient.getQueryApi()
                .query(query.toString(), Energy.class)
                .stream()
                .map(EnergyDto::toDto)
                .toList();
    }

    public List<EnergyDto> energy(String entityId) {
        Flux value = Flux.from(BUCKET)
                .range(-1L, ChronoUnit.HOURS)
                .filter(and(
                        measurement().equal(MEASUREMENT),
                        field().equal("value"),
                        column("entity_id").equal(entityId)
                ));
        Flux friendlyNameStr = Flux.from(BUCKET)
                .range(-1L, ChronoUnit.HOURS)
                .filter(and(
                        measurement().equal(MEASUREMENT),
                        field().equal("friendly_name_str"),
                        column("entity_id").equal(entityId)
                ));

        Flux query = Flux.union(value, friendlyNameStr)
                .pivot(new String[]{"_time"}, new String[]{"_field"}, "_value")
                .groupBy("_measurement")
                .limit(10);

        return influxDBClient.getQueryApi()
                .query(query.toString(), Energy.class)
                .stream()
                .map(EnergyDto::toDto)
                .toList();
    }
}
