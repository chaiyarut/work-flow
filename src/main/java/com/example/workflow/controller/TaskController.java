package com.example.workflow.controller;

import com.example.workflow.data.request.SubmitRequest;
import com.example.workflow.data.request.TaskRequest;
import com.example.workflow.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController extends MainController{

    @Autowired
    private TaskService taskService;

    @PostMapping("/get")
    public ResponseEntity<?> getTask(@RequestBody TaskRequest taskRequest) throws JsonProcessingException {

        var response = taskService.getTask(taskRequest);

        return returnObject(response);
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitTask(@RequestBody SubmitRequest submitRequest) throws JsonProcessingException {

        var response = taskService.submitTask(submitRequest);

        return returnObject(response);
    }
}
