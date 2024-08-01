package com.jongsik2.home.influxapi.service.impl;

import com.jongsik2.home.influxapi.dto.Entity;
import com.jongsik2.home.influxapi.service.EntityService;
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
public class EntityServiceImpl implements EntityService {
    private final InfluxDBTemplate<Point> influxDBTemplate;

    @Override
    public List<Entity> entityList() {
        InfluxDBResultMapper mapper = new InfluxDBResultMapper();
        String s = "SELECT DISTINCT \"friendly_name_str\" AS friendly_name_str FROM state WHERE time > now() - 12h AND time < now() GROUP BY \"entity_id\"";
        Query query = BoundParameterQuery.QueryBuilder.newQuery(s)
                .forDatabase("homeassistant")
                .create();
        QueryResult result = influxDBTemplate.query(query);

        return mapper.toPOJO(result, Entity.class);
    }
}
