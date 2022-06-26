package com.nhom43.quanlychungcubackendgradle.mapper;

import com.nhom43.quanlychungcubackendgradle.dto.ThongBaoDto;
import com.nhom43.quanlychungcubackendgradle.entity.ThongBao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ThongBaoMapper extends EntityMapper<ThongBaoDto, ThongBao> {
}