package com.jongsik2.home.influxapi.controller;

import com.jongsik2.home.influxapi.dto.Entity;
import com.jongsik2.home.influxapi.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/entity")
public class EntityController {
    private final EntityService entityService;

    @GetMapping
    public List<Entity> getEntityList() {
        return entityService.entityList();
    }
}
