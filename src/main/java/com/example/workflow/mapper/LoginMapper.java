package com.example.workflow.mapper;

import com.example.workflow.data.entities.RoleEntity;
import com.example.workflow.data.response.LoginResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class LoginMapper {

    public static final LoginMapper INSTANCE = Mappers.getMapper(LoginMapper.class);

    public abstract LoginResponse toResponse(Boolean isSuccess,LoginResponse.Role role,LoginResponse.Detail detail);

    @Mapping(target = "id", source = "roleId")
    @Mapping(target = "name", source = "roleName")
    public abstract LoginResponse.Role toRole(RoleEntity source);

}
