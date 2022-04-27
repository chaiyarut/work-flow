package com.example.workflow.service;

import com.example.workflow.data.entities.RoleEntity;
import com.example.workflow.data.model.TaskModel;
import com.example.workflow.data.repositories.RoleRepository;
import com.example.workflow.data.repositories.TaskRepository;
import com.example.workflow.data.request.SubmitRequest;
import com.example.workflow.data.request.TaskRequest;
import com.example.workflow.data.response.SubmitResponse;
import com.example.workflow.data.response.TaskResponse;
import com.example.workflow.mapper.TaskMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RedisMessageSubscriber redisMessageSubscriber;

    @Autowired
    private RedisMessagePublisher redisMessagePublisher;

    @Autowired
    private final ObjectMapper mapper = new ObjectMapper();

    public TaskResponse getTask(TaskRequest taskRequest) throws JsonProcessingException {

        var allTaskFromPubList = redisMessageSubscriber.getMessage();

        Collections.reverse(allTaskFromPubList);

        for(var allTaskFromPub : allTaskFromPubList){
            if(allTaskFromPub.getUserId()==null || !allTaskFromPub.getUserId().equals(taskRequest.getUserId())){

                var allTaskFromDB = getTaskFromDatabase(taskRequest);

                publish(taskRequest.getUserId(),allTaskFromDB);

                return allTaskFromDB;

            }else{

                if(allTaskFromPub.getUserId().equals(taskRequest.getUserId())){

                    return allTaskFromPub.getTaskResponse();

                }else{

                    var allTaskFromDB = getTaskFromDatabase(taskRequest);

                    publish(taskRequest.getUserId(),allTaskFromDB);

                    return allTaskFromDB;
                }
            }
        }

        return null;
    }

    public SubmitResponse submitTask(SubmitRequest taskRequest) throws JsonProcessingException {

        var response = SubmitResponse.builder()
                .isSuccess(false)
                .build();

        var allTaskFromPubList = redisMessageSubscriber.getMessage();

        Collections.reverse(allTaskFromPubList);

        for(var allTaskFromPub : allTaskFromPubList){

            if(allTaskFromPub.getUserId().equals(taskRequest.getUserId()) &&
                    allTaskFromPub.getTaskResponse()!=null){

                for(var task : allTaskFromPub.getTaskResponse().getTaskList()){
                    if(task.getTaskId().equals(taskRequest.getTaskId())){

                        var userId = taskRequest.getAssign().getUserIds().get(0);

                        task.setUserId(userId);

                        publish(allTaskFromPub.getUserId(),allTaskFromPub.getTaskResponse());

                        //CHECK LEVEL
                        var assign = roleRepository.findAll().stream()
                                .filter(it -> it.getUserId().equals(userId))
                                .filter(role -> role.getRoleId() == 3)
                                .collect(Collectors.toList());

                        task.setIsAssign(assign.isEmpty());

                        var taskEntity = TaskMapper.INSTANCE.toTaskEntity(task);

                        taskRepository.save(taskEntity);

                        response.setIsSuccess(true);

                        return response;
                    }
                }
            }
        }


        return response;
    }

    private TaskResponse getTaskFromDatabase(TaskRequest taskRequest){

        var allTask = taskRepository.findAll().stream()
                .filter(it -> it.getUserId().equals(taskRequest.getUserId()))
                .map(TaskMapper.INSTANCE::toTaskList)
                .collect(Collectors.toList());

        allTask.forEach(taskList -> {
            if(taskList.getIsAssign()){
                //TO DO SAVE ROLE TO CACHE
                var assign = roleRepository.findAll().stream()
                        .filter(userId -> !userId.getUserId().equals(taskRequest.getUserId()))
                        .filter(role -> role.getRoleId() > Integer.parseInt(taskRequest.getRoleId()))
                        .map(RoleEntity::getUserId)
                        .collect(Collectors.toList());

                taskList.setAssign(TaskResponse.TaskList.Assign.builder()
                        .userIds(assign)
                        .build());
            }
        });

        var isSuccess = allTask.size() > 0;

        return TaskResponse.builder()
                .taskList(allTask)
                .isSuccess(isSuccess)
                .build();
    }

    private void publish(String userId,TaskResponse taskResponse) throws JsonProcessingException {
        var taskModel = TaskModel.builder()
                .userId(userId)
                .taskResponse(taskResponse).build();

        redisMessagePublisher.publish(mapper.writeValueAsString(taskModel));
    }
}
