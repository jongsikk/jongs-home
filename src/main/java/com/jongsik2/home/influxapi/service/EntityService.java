package com.jongsik2.home.influxapi.service;

import com.jongsik2.home.influxapi.dto.EntityDto;

import java.util.List;

public interface EntityService {
    List<EntityDto> entityList();
}
