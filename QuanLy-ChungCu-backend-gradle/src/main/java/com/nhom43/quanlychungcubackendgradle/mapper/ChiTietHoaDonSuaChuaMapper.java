package com.nhom43.quanlychungcubackendgradle.mapper;

import com.nhom43.quanlychungcubackendgradle.dto.ChiTietHoaDonSuaChuaDto;
import com.nhom43.quanlychungcubackendgradle.entity.ChiTietHoaDonSuaChua;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChiTietHoaDonSuaChuaMapper extends EntityMapper<ChiTietHoaDonSuaChuaDto, ChiTietHoaDonSuaChua> {
}