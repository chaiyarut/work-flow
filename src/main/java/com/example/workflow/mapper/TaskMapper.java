package com.example.workflow.mapper;

import com.example.workflow.data.entities.TaskEntity;
import com.example.workflow.data.response.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class TaskMapper {
    public static final TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    public abstract TaskResponse.TaskList toTaskList(TaskEntity source);

    public abstract TaskEntity toTaskEntity(TaskResponse.TaskList source);
}
