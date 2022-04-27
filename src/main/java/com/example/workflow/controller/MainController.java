package com.example.workflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    public ResponseEntity<?> returnObject(Object object){
        return new ResponseEntity<>(object, HttpStatus.OK);
    }

    public ResponseEntity<?> returnListObject(List<?> object){
        return new ResponseEntity<>(object, HttpStatus.OK);
    }
}
