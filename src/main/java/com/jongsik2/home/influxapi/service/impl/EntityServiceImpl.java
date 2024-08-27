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

import static com.influxdb.query.dsl.functions.restriction.Restrictions.and;
import static com.influxdb.query.dsl.functions.restriction.Restrictions.field;

@Service
@RequiredArgsConstructor
public class EntityServiceImpl implements EntityService {
    private final InfluxDBClient influxDBClient;
    private static final String BUCKET = "homeassistant";

    @Override
    public List<EntityDto> entityList() {
        Flux linkquality = Flux.from(BUCKET)
                .range(-1L, ChronoUnit.DAYS)
                .filter(and(
                        field().equal("linkquality")))
                .last();
        Flux friendlyNameStr = Flux.from(BUCKET)
                .range(-1L, ChronoUnit.DAYS)
                .filter(and(
                        field().equal("friendly_name_str")))
                .last();
        Flux state = Flux.from(BUCKET)
                .range(-1L, ChronoUnit.DAYS)
                .filter(and(
                        field().equal("state")))
                .last();
        Flux battery = Flux.from(BUCKET)
                .range(-1L, ChronoUnit.DAYS)
                .filter(and(
                        field().equal("battery")))
                .last();
        Flux value = Flux.from(BUCKET)
                .range(-1L, ChronoUnit.DAYS)
                .filter(and(
                        field().equal("value")))
                .last();

        Flux query = Flux.union(linkquality, friendlyNameStr, state, battery, value)
                .pivot(new String[]{"_time"}, new String[]{"_field"}, "_value")
                .groupBy("_value")
                .sort(new String[]{"friendly_name_str"});

        return influxDBClient.getQueryApi()
                .query(query.toString(), Entity.class)
                .stream()
                .map(EntityDto::toDto)
                .toList();
    }
}
