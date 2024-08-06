package com.jongsik2.home.influxapi.service.impl;

import com.jongsik2.home.influxapi.dto.EnergyDto;
import com.jongsik2.home.influxapi.entity.Energy;
import com.jongsik2.home.influxapi.service.EnergyService;
import lombok.RequiredArgsConstructor;
import org.influxdb.dto.BoundParameterQuery;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnergyServiceImpl implements EnergyService {
    private final InfluxDBTemplate<Point> influxDBTemplate;

    public List<EnergyDto> energyDeviceList() {
        InfluxDBResultMapper mapper = new InfluxDBResultMapper();
        String s = "SELECT DISTINCT \"friendly_name_str\" AS friendly_name_str FROM kWh WHERE time > now() - 1h AND time < now() GROUP BY \"entity_id\"";
        Query query = BoundParameterQuery.QueryBuilder.newQuery(s)
                .forDatabase("homeassistant")
                .create();
        QueryResult result = influxDBTemplate.query(query);

        return mapper.toPOJO(result, Energy.class)
                .stream()
                .map(EnergyDto::toDto)
                .collect(Collectors.toList());
    }

    public List<EnergyDto> energy(String entityId) {
        InfluxDBResultMapper mapper = new InfluxDBResultMapper();
        String s = "SELECT * FROM kWh WHERE time > now() - 1h AND time < now() AND \"entity_id\"='" + entityId + "' LIMIT 1";
        Query query = BoundParameterQuery.QueryBuilder.newQuery(s)
                .forDatabase("homeassistant")
                .create();
        QueryResult result = influxDBTemplate.query(query);

        return mapper.toPOJO(result, Energy.class).stream()
                .map(EnergyDto::toDto)
                .collect(Collectors.toList());
    }
}
