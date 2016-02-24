package com.serd.chat.controllers;


import com.serd.chat.dao.UserDAO;
import com.serd.chat.model.ServerMessage;
import com.serd.chat.model.User;
import com.serd.chat.util.RegistrationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@EnableWebMvc
@Controller
public class RegistrationController {


    @Autowired
    private RegistrationUtil registrationUtil;

    //Самостоятельная регистрация пользователя, перевод его в очередь ожидания подтверждения

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public @ResponseBody ServerMessage newUser(@RequestBody User newUser, HttpServletRequest request) {

               if(!request.getParameter("pas2").equals(newUser.getPassword())) return new ServerMessage(1011, "Пароли не совпадают");
               if (Integer.parseInt(request.getParameter("accept"))==0)
               {
                    return new ServerMessage(1010, "Примите пользовательское соглашение");
               }else{
               ServerMessage  message = registrationUtil.addNewUser(newUser);
                    return message;
               }
    }


}



