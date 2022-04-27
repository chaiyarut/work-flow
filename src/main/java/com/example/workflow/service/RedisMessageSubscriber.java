package com.example.workflow.service;

import com.example.workflow.data.model.TaskModel;
import com.example.workflow.data.response.TaskResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RedisMessageSubscriber implements MessageListener {

    @Autowired
    private final ObjectMapper mapper = new ObjectMapper();

    public static List<TaskModel> messageList = new ArrayList<TaskModel>();

    @SneakyThrows
    public void onMessage(Message message, byte[] pattern) {

        /*if(messageList.size()>0){
            messageList.clear();
        }*/

        System.out.println("Message received Data : " + message.toString());

        var data = mapper.readValue(message.toString(), TaskModel.class);

        messageList.add(data);

    }

    public List<TaskModel> getMessage(){

        var size = messageList.size();

        List<TaskModel> taskModelList = new ArrayList<>();

        var data = TaskModel.builder()
                .userId(null)
                .build();

        taskModelList.add(data);

        return size>0?messageList:taskModelList;
    }
}
