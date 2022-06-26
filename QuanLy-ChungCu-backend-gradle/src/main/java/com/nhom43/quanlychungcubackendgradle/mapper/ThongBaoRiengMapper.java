package com.nhom43.quanlychungcubackendgradle.mapper;

import com.nhom43.quanlychungcubackendgradle.dto.ThongBaoRiengDto;
import com.nhom43.quanlychungcubackendgradle.entity.ThongBaoRieng;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ThongBaoRiengMapper extends EntityMapper<ThongBaoRiengDto, ThongBaoRieng> {
}