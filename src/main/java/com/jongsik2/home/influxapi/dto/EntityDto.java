package com.jongsik2.home.influxapi.dto;

import com.jongsik2.home.influxapi.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Getter
@Setter
@AllArgsConstructor
public class EntityDto {
    String domain;
    String name;
    String entityId;
    String state;
    Double battery;
    int linkQuality;
    Double value;

    public static EntityDto toDto(Entity energy) {
        return EntityDto.EntityDtoMapper.INSTANCE.toDto(energy);
    }

    public static Entity toEntity(EntityDto energyDto) {
        return EntityDto.EntityDtoMapper.INSTANCE.toEntity(energyDto);
    }

    @Mapper
    public interface EntityDtoMapper {
        EntityDto.EntityDtoMapper INSTANCE = Mappers.getMapper(EntityDto.EntityDtoMapper.class);

        EntityDto toDto(Entity energy);

        Entity toEntity(EntityDto energyDto);
    }
}
