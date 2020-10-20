package com.application.controller;

import com.application.model.User;
import com.application.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public @ResponseBody
    Object createUser(@RequestParam("username") String username) throws Exception {
        User user = new User();
        user.setUserName(username);
        userService.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping("/getUsers")
    public @ResponseBody
    String getUsers() throws JsonProcessingException {
        Collection users = userService.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(users);
        return json;
    }

}