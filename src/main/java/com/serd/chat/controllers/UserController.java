package com.serd.chat.controllers;

import com.serd.chat.dao.MessageDAO;
import com.serd.chat.dao.RoomDAO;
import com.serd.chat.dao.UserDAO;

import com.serd.chat.model.Message;
import com.serd.chat.model.ServerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;


import com.serd.chat.model.User;


import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Controller
public class UserController {



    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoomDAO roomDAO;
    @Autowired
    private MessageDAO messageDAO;


    //Контроллер возвращает список искомых пользователей
    @RequestMapping(value="/chatUsersSearch", method = RequestMethod.GET)
    public @ResponseBody List<User> getUsersSearch(String string) {
        List<User> listUser = userDAO.getUserSearch(string);
        return listUser;
    }


    //Контроллер возвращает список искомых пользователей разделяя друзей и не друзей
    @RequestMapping(value="/chatUsersFriendsAndNot", method = RequestMethod.GET)
    public @ResponseBody List<User> getUsersFriendsSearch(String string, int user_id ) {
        List<User> listUser;
        listUser = userDAO.getUsersByLogin(string, user_id , true);
        listUser.addAll(userDAO.getUsersByLogin(string, user_id , false));
        return listUser;

    }

    //Удаление пользователя из контактов
    @RequestMapping(value = "/chatDelUserInFriends", method = RequestMethod.POST)
    public @ResponseBody ServerMessage delUser(HttpServletRequest request) {

        int user_id = Integer.parseInt(request.getParameter("user_id"));
        int friend_id = Integer.parseInt(request.getParameter("friend_id"));
        ServerMessage message = userDAO.DelUser(user_id, friend_id);
        return message;

    }

    //Добавление пользователя
    @RequestMapping(value = "/chatAddUserInFriends", method = RequestMethod.POST)
    public @ResponseBody ServerMessage acceptUser(HttpServletRequest request) {

        int user_id = Integer.parseInt(request.getParameter("user_id"));
        int friend_id = Integer.parseInt(request.getParameter("friend_id"));
        ServerMessage message = userDAO.AddUser(user_id, friend_id);
        return message;

    }
    //Пользователи в друзьях
    @RequestMapping(value = "/chatUsersFriends", method = RequestMethod.GET)
    public @ResponseBody List<User> FriendUser(HttpServletRequest request) {
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        System.out.println("Пытаюсь получить чписок пользователей для " + user_id);
        List<User> listUser = userDAO.getFriendList(user_id);

        return listUser;
    }


    //Данный контролле проверяет есть ли комната/чат, если нет то создает новую. Возвращает странице уникальный номер комнаты
    @RequestMapping(value = "/chatBegin", method = RequestMethod.POST)
    public @ResponseBody Integer UserChatBegin(HttpServletRequest request) throws IOException {
        int user_chat_id = Integer.parseInt(request.getParameter("user_id"));
        int user_welcome_id = Integer.parseInt(request.getParameter("friend_id"));
        System.out.println(user_chat_id + "  "+user_welcome_id );
        int room_uniq_id;
        room_uniq_id = roomDAO.returnChatRoomUniq(user_chat_id, user_welcome_id);
        return room_uniq_id;
    }

    //Данный контролле проверяет есть ли комната/чат, если нет то создает новую. Возвращает странице уникальный номер комнаты
    @RequestMapping(value = "/chatRoomKey", method = RequestMethod.GET)
    public @ResponseBody  User getRoomKey(HttpServletRequest request) throws IOException {
        int room_uniq_id = Integer.parseInt(request.getParameter("room_uniq_id"));
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        System.out.println("Получить ключ от комнаты"+room_uniq_id);
        User user= userDAO.returnUserRoomKey(room_uniq_id,user_id);
        return user;
    }


 /*   //Пользователи в друзьях
    @RequestMapping(value = "/chatUsersFriends", method = RequestMethod.GET)
    public @ResponseBody List<User> FriendUser(HttpServletRequest request) {
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        System.out.println("Пытаюсь получить чписок пользователей для " + user_id);
        List<User> listUser = userDAO.getFriendList(user_id);
        return listUser;
    }*/


    //Пользователи в друзьях
    @RequestMapping(value = "/chatMessageFromDate", method = RequestMethod.GET)
    public @ResponseBody List<Message> chatMessageFromDate(HttpServletRequest request) {

        int room_uniq_id = Integer.parseInt(request.getParameter("room_uniq_id"));
        int k = Integer.parseInt(request.getParameter("k"));
        System.out.println("ss  комнаты"+k);
        List<Message> listMessage = messageDAO.getMessageDaysAgo(k, room_uniq_id);
       return listMessage;
    }

}





