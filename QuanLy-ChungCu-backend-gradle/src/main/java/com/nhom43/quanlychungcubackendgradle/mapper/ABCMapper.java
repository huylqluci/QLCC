package com.nhom43.quanlychungcubackendgradle.mapper;

import com.nhom43.quanlychungcubackendgradle.dto.ABCDto;
import com.nhom43.quanlychungcubackendgradle.entity.ABC;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ABCMapper extends EntityMapper<ABCDto, ABC> {
}