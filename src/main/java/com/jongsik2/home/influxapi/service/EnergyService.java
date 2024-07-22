package com.jongsik2.home.influxapi.service;

import com.jongsik2.home.influxapi.dto.Energy;
import lombok.RequiredArgsConstructor;
import org.influxdb.dto.BoundParameterQuery;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnergyService {
    private final InfluxDBTemplate<Point> influxDBTemplate;

    public List<Energy> energy(String entityId) {
        InfluxDBResultMapper mapper = new InfluxDBResultMapper();
        String s = "SELECT * FROM kWh WHERE time > now() - 1h AND time < now() AND \"entity_id\"='" + entityId + "_energy' LIMIT 1";
        Query query = BoundParameterQuery.QueryBuilder.newQuery(s)
                .forDatabase("homeassistant")
                .create();
        QueryResult result = influxDBTemplate.query(query);

        return mapper.toPOJO(result, Energy.class);
    }
}
