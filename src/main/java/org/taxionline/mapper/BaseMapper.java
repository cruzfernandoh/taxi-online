package org.taxionline.mapper;

public interface BaseMapper<ENTITY, DTO> {

    ENTITY toEntity(DTO dto);

    DTO toDTO(ENTITY entity);
}
