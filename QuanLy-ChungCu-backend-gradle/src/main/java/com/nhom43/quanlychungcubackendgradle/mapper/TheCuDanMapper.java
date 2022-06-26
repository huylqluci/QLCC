package com.nhom43.quanlychungcubackendgradle.mapper;

import com.nhom43.quanlychungcubackendgradle.dto.TheCuDanDto;
import com.nhom43.quanlychungcubackendgradle.entity.TheCuDan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TheCuDanMapper extends EntityMapper<TheCuDanDto, TheCuDan> {
}