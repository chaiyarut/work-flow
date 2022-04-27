package com.example.workflow.service;

import com.example.workflow.data.entities.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitialDataService {

    @Autowired
    public void init(){
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleName("");
    }
}
