package com.jongsik2.home.influxapi.controller;

import com.jongsik2.home.influxapi.dto.EnergyDto;
import com.jongsik2.home.influxapi.dto.EntityDto;
import com.jongsik2.home.influxapi.entity.Energy;
import com.jongsik2.home.influxapi.entity.Entity;
import com.jongsik2.home.influxapi.service.EnergyService;
import com.jongsik2.home.influxapi.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final EntityService entityService;
    private final EnergyService energyService;

    @GetMapping("/")
    public String index(Model model) {
        List<String> measurementList = List.of("kWh", "units", "%");
        List<Energy> energyList = energyService.energyDeviceList()
                .stream()
                .map(EnergyDto::toEntity)
                .toList();
        List<Entity> entityList = entityService.entityList()
                .stream()
                .map(EntityDto::toEntity)
                .toList();

        model.addAttribute("measurementList", measurementList);
        model.addAttribute("energyList", energyList);
        model.addAttribute("entityList", entityList);
        return "index";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Entity> entityList = entityService.entityList()
                .stream()
                .map(EntityDto::toEntity)
                .toList();

        model.addAttribute("entityList", entityList);
        return "list";
    }
}
