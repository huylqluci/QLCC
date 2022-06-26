package com.nhom43.quanlychungcubackendgradle.mapper;

import com.nhom43.quanlychungcubackendgradle.dto.RefreshTokenDto;
import com.nhom43.quanlychungcubackendgradle.entity.RefreshToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RefreshTokenMapper extends EntityMapper<RefreshTokenDto, RefreshToken> {
}