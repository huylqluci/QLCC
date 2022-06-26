package com.nhom43.quanlychungcubackendgradle.mapper;

import com.nhom43.quanlychungcubackendgradle.dto.NhanVienDto;
import com.nhom43.quanlychungcubackendgradle.entity.NhanVien;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NhanVienMapper extends EntityMapper<NhanVienDto, NhanVien> {
}