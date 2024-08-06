package com.jongsik2.home.influxapi.service.impl;

import com.jongsik2.home.influxapi.dto.EntityDto;
import com.jongsik2.home.influxapi.entity.Entity;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntityServiceImpl implements EntityService {
    private final InfluxDBTemplate<Point> influxDBTemplate;

    @Override
    public List<EntityDto> entityList() {
        InfluxDBResultMapper mapper = new InfluxDBResultMapper();
        String s = "SELECT LAST(\"friendly_name_str\") AS friendly_name_str, " +
                "LAST(\"state\") AS state, " +
                "LAST(\"battery\") AS battery, " +
                "LAST(\"linkquality\") AS linkquality " +
                "FROM state " +
                "WHERE time > now() - 6h AND time < now() " +
                "GROUP BY \"entity_id\", \"domain\"";
        Query query = BoundParameterQuery.QueryBuilder.newQuery(s)
                .forDatabase("homeassistant")
                .create();
        QueryResult result = influxDBTemplate.query(query);

        return mapper.toPOJO(result, Entity.class)
                .stream()
                .map(EntityDto::toDto)
                .collect(Collectors.toList());
    }
}
