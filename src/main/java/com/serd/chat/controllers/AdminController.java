package com.serd.chat.controllers;



import java.util.List;

import com.serd.chat.dao.UserDAO;
import com.serd.chat.model.User;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class AdminController {

	@Autowired
	private UserDAO userDAO;

//Контроллер возвращает список пользователей ожидающих одобрение
	@RequestMapping(value="/chatUsersQueue", method = RequestMethod.GET)
	public @ResponseBody List<User> getUsersQeue() {
		List<User> listUser = userDAO.getUsersQueue();
		return listUser;
	}
	//Контроллер возвращает список действующих пользователей
	@RequestMapping(value="/chatUsersAccepted", method = RequestMethod.GET)
	public @ResponseBody List<User> getUsersAccepted() {
		List<User> listUser = userDAO.getUsersAccepted();
		return listUser;
	}

	//Контроллер возвращает список действующих пользователей
	@RequestMapping(value="/chatUsersDenied", method = RequestMethod.GET)
	public @ResponseBody List<User> getUsersDenied() {
		List<User> listUser = userDAO.getUsersDenied();
		return listUser;
	}



//одобрение пользователей
		@RequestMapping(value = "/chatAcceptUser", method = RequestMethod.POST)
		public void acceptUser(HttpServletRequest request, HttpServletResponse response) {
		int user_id = Integer.parseInt(request.getParameter("id"));
		System.out.println(user_id);
			if(userDAO.AcceptUser(user_id))
				{
				response.setStatus(HttpServletResponse.SC_OK);
				}
				else{
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}
		}
//отклонение пользователей
	    @RequestMapping(value = "/chatDeniedUser", method = RequestMethod.POST)
		public void deniedtUser(HttpServletRequest request, HttpServletResponse response) {
			int user_id = Integer.parseInt(request.getParameter("id"));
			System.out.println(user_id);
			if(userDAO.DeniedUser(user_id))
			{
				response.setStatus(HttpServletResponse.SC_OK);
			}
			else{
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}

}