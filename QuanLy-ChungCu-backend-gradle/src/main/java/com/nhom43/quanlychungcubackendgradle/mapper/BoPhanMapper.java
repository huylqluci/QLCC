package com.nhom43.quanlychungcubackendgradle.mapper;

import com.nhom43.quanlychungcubackendgradle.dto.BoPhanDto;
import com.nhom43.quanlychungcubackendgradle.entity.BoPhan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BoPhanMapper extends EntityMapper<BoPhanDto, BoPhan> {

    @Override
    @Mapping(target = "id", ignore = false)
    BoPhan toEntity(BoPhanDto dto);
}