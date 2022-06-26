package com.nhom43.quanlychungcubackendgradle.mapper;

import com.nhom43.quanlychungcubackendgradle.dto.ChiTietHoaDonDichVuDto;
import com.nhom43.quanlychungcubackendgradle.entity.ChiTietHoaDonDichVu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChiTietHoaDonDichVuMapper extends EntityMapper<ChiTietHoaDonDichVuDto, ChiTietHoaDonDichVu> {
}