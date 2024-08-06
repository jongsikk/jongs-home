package com.jongsik2.home.influxapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Measurement(name = "state")
public class Entity {
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
