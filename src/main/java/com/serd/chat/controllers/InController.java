package com.serd.chat.controllers;

import com.serd.chat.dao.UserDAO;
import com.serd.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;


@Controller
public class InController {
///Помни URI - /chat** под защитой!!!!!
                         private User youUser;
    @Autowired
    private UserDAO userDAO;
//Контроллер перехода на страницу регистрации
    @RequestMapping(value = { "/registration" }, method = RequestMethod.GET)
    public String regForm() {
        return "regForm";
    }

    @RequestMapping(value = { "/file" }, method = RequestMethod.GET)
    public String file() {
        return "file";
    }


//Контроллер перехода на страницу переписки
    @RequestMapping(value = { "/chat_messager" }, method = RequestMethod.GET)
    public String chat_messager(HttpServletRequest request,ModelMap model) {
    int room_uniq_id = Integer.parseInt(request.getParameter("r"));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username
        model.addAttribute("username", name);
        List<User> listUser = userDAO.getUserSearch(name);
        youUser = listUser.get(0);
    model.addAttribute("room_uniq_id",room_uniq_id );
    model.addAttribute("lastname", youUser.getLastName());
    model.addAttribute("firstname", youUser.getFirstName());
    model.addAttribute("user_id", youUser.getUser_id());
    model.addAttribute("avatar", youUser.getAvatar());
    return "user_message";
    }




//Контроллер перехода на личную страницу пользователя
    @RequestMapping(value = { "/chat_user_page" }, method = RequestMethod.GET)
    public String userPage(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username
        model.addAttribute("username", name);
        List<User> listUser = userDAO.getUserSearch(name);
        youUser = listUser.get(0);
        model.addAttribute("error", "");
        model.addAttribute("msg", "");
        model.addAttribute("user_id",youUser.getUser_id());
        model.addAttribute("firstname", youUser.getFirstName());
        model.addAttribute("lastname", youUser.getLastName());
        model.addAttribute("avatar", youUser.getAvatar());

        return "userPage";
    }

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public String welcomePage() { return "redirect:/chat_user_page";}

//Контроллер перехода на панель администратора

    @RequestMapping(value = { "/adminRoom" }, method = RequestMethod.GET)
    public String adminPage() {return "admin";}


    @RequestMapping(value = { "/403" }, method = RequestMethod.GET)
    public String ErPage() {return "403";

    }
    //Spring Security see this :
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Логин и пароль не совпадают..");
        }

        if (logout != null) {
            model.addObject("msg", "Возвращайтесь снова. Будем ждать:)");
        }
        model.setViewName("login");

        return model;

    }

}

