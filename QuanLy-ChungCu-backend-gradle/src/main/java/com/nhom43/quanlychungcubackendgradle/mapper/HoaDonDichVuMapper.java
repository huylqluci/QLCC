package com.nhom43.quanlychungcubackendgradle.mapper;

import com.nhom43.quanlychungcubackendgradle.dto.HoaDonDichVuDto;
import com.nhom43.quanlychungcubackendgradle.entity.HoaDonDichVu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HoaDonDichVuMapper extends EntityMapper<HoaDonDichVuDto, HoaDonDichVu> {
}