package com.jongsik2.home.influxapi.dto;

import com.jongsik2.home.influxapi.entity.Energy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class EnergyDto {
    Instant time;
    String name;
    String entityId;
    Double value;

    public static EnergyDto toDto(Energy energy) {
        return EnergyDtoMapper.INSTANCE.toDto(energy);
    }

    public static Energy toEnergy(EnergyDto energyDto) {
        return EnergyDtoMapper.INSTANCE.toEntity(energyDto);
    }

    @Mapper
    public interface EnergyDtoMapper {
        EnergyDtoMapper INSTANCE = Mappers.getMapper(EnergyDtoMapper.class);

        EnergyDto toDto(Energy energy);

        Energy toEntity(EnergyDto energyDto);
    }
}
