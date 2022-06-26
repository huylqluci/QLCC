package com.nhom43.quanlychungcubackendgradle.share.mapper;

import com.nhom43.quanlychungcubackendgradle.entity.User;
import com.nhom43.quanlychungcubackendgradle.share.dto.request.ProfileRequest;
import com.nhom43.quanlychungcubackendgradle.share.dto.response.ProfileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ProfileMapper {

    @Mapping(target = "id", source = "user.id" )
    @Mapping(target = "username", source = "user.username" )
    @Mapping(target = "password", source = "user.password" )
    @Mapping(target = "email", source = "user.email" )
    @Mapping(target = "image", source = "user.image" )
    @Mapping(target = "role", source = "user.role" )
    @Mapping(target = "created", source = "user.created" )
    @Mapping(target = "enabled", source = "user.enabled" )
    @Mapping(target = "status", source = "user.status" )
    public abstract ProfileResponse mapToDtoByUserName(User user);


    @Mapping(target = "id", source = "profileRequest.id" )
    @Mapping(target = "username", source = "user.username" )
    @Mapping(target = "password", source = "user.password" )
    @Mapping(target = "email", source = "profileRequest.email" )
    @Mapping(target = "image", source = "profileRequest.image" )
    @Mapping(target = "role", source = "user.role" )
    @Mapping(target = "created", source = "TheCuDan.created" )
    @Mapping(target = "enabled", source = "hoadondivu.enabled" )
    @Mapping(target = "status", source = "user.status" )
    public abstract User mapEditToDtoById(ProfileRequest profileRequest, User user);

    @Mapping(target = "id", source = "profileRequest.id" )
    @Mapping(target = "username", source = "user.username" )
    @Mapping(target = "password", source = "profileRequest.password" )
    @Mapping(target = "email", source = "profileRequest.email" )
    @Mapping(target = "image", source = "profileRequest.image" )
    @Mapping(target = "role", source = "user.role" )
    @Mapping(target = "created", source = "user.created" )
    @Mapping(target = "enabled", source = "profileRequest.enabled" )
    @Mapping(target = "status", source = "profileRequest.status" )
    public abstract User mapUpdateToDtoById(ProfileRequest profileRequest, User user);
}
