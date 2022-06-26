package com.nhom43.quanlychungcubackendgradle.mapper;

import com.nhom43.quanlychungcubackendgradle.dto.DichVuCoDinhDto;
import com.nhom43.quanlychungcubackendgradle.entity.DichVuCoDinh;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DichVuCoDinhMapper extends EntityMapper<DichVuCoDinhDto, DichVuCoDinh> {
}