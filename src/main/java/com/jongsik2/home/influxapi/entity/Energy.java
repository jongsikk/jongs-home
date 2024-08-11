package com.jongsik2.home.influxapi.entity;

import com.influxdb.annotations.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
