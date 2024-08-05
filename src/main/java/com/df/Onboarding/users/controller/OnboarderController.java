package com.df.Onboarding.users.controller;

import com.df.Onboarding.phone.model.PhoneNumber;
import com.df.Onboarding.users.exceptions.InvalidUserNameException;
import com.df.Onboarding.users.model.ResultList;
import com.df.Onboarding.users.model.ResultMessage;

import com.df.Onboarding.users.implservice.ValidateServiceImpl;
import com.df.Onboarding.users.model.Users;
import com.df.Onboarding.users.repo.RepoManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class OnboarderController {
    public static final Logger log = LoggerFactory.getLogger(OnboarderController.class);
    @Autowired
    ValidateServiceImpl implementValidate;

    @Autowired
    RepoManager repoManager;


    @PostMapping(path="/ping/")

    public ResponseEntity<Object> great(@RequestHeader("Idempotency-Key") String idempotencyKey, @RequestBody Users user){
        try{
            ResultMessage resultMessage = new ResultMessage();
            implementValidate.checkValidUser(user, resultMessage,idempotencyKey);
            return new ResponseEntity<>(resultMessage, HttpStatus.OK);
        }catch(InvalidUserNameException invalidUserNameException){
            return new ResponseEntity<>("Check the format or provide valid username",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findNumber/")
    public PhoneNumber findByNumber(@RequestBody Users users){
        return implementValidate.restTemplateUsage(users);
    }


    @GetMapping("/findallusers/")
    public ResultList listAllUsers(){
        return implementValidate.getAllUsersData();
    }



}
