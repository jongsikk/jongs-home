package com.jongsik2.home.influxapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Measurement(name = "kWh")
public class Energy {
    @Column(name = "time")
    Instant time;
    @Column(name = "friendly_name_str")
    String name;
    @Column(name = "entity_id")
    String entityId;
    @Column(name = "value")
    Double value;
}
