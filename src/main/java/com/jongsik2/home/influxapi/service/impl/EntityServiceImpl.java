package com.jongsik2.home.influxapi.service.impl;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.query.dsl.Flux;
import com.jongsik2.home.influxapi.dto.EntityDto;
import com.jongsik2.home.influxapi.entity.Entity;
import com.jongsik2.home.influxapi.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.influxdb.query.dsl.functions.restriction.Restrictions.*;

@Service
@RequiredArgsConstructor
public class EntityServiceImpl implements EntityService {
    private final InfluxDBClient influxDBClient;
    private static final String BUCKET = "homeassistant";
    private static final String MEASUREMENT = "units";

    @Override
    public List<EntityDto> entityList() {
        Flux linkquality = Flux.from(BUCKET)
                .range(-1L, ChronoUnit.HOURS)
                .filter(and(
                        measurement().equal(MEASUREMENT),
                        field().equal("linkquality")))
                .last();
        Flux friendlyNameStr = Flux.from(BUCKET)
                .range(-1L, ChronoUnit.HOURS)
                .filter(and(
                        measurement().equal(MEASUREMENT),
                        field().equal("friendly_name_str")))
                .last();
        Flux state = Flux.from(BUCKET)
                .range(-1L, ChronoUnit.HOURS)
                .filter(and(
                        measurement().equal(MEASUREMENT),
                        field().equal("state")))
                .last();
        Flux battery = Flux.from(BUCKET)
                .range(-1L, ChronoUnit.HOURS)
                .filter(and(
                        measurement().equal(MEASUREMENT),
                        field().equal("battery")))
                .last();

        Flux query = Flux.union(linkquality, friendlyNameStr, state, battery)
                .pivot(new String[]{"_time"}, new String[]{"_field"}, "_value")
                .groupBy("_measurement");

        return influxDBClient.getQueryApi()
                .query(query.toString(), Entity.class)
                .stream()
                .map(EntityDto::toDto)
                .toList();
    }
}
