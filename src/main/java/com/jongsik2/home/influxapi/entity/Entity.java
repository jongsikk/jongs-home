package com.jongsik2.home.influxapi.entity;

import com.influxdb.annotations.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Entity {
    @Column(name = "domain")
    String domain;
    @Column(name = "friendly_name_str")
    String name;
    @Column(name = "entity_id")
    String entityId;
    @Column(name = "state")
    String state;
    @Column(name = "battery")
    Double battery;
    @Column(name = "linkquality")
    int linkQuality;
}
