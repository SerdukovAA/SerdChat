package com.serd.chat.util;


import com.serd.chat.dao.UserDAO;
import com.serd.chat.model.Message;
import com.serd.chat.model.ServerMessage;
import com.serd.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegistrationUtilImpl implements RegistrationUtil {

    @Autowired
    private UserDAO userDAO;


    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


public ServerMessage addNewUser(User newUser){


    if( !validStringRU(newUser.getFirstName()))  return new ServerMessage(1001, "Некорректное имя пользователя");
    if( !validStringRU(newUser.getLastName()))  return new ServerMessage(1002, "Некорректная фамилия пользователя");
    if( !validEmail(newUser.getEmail()))return  new ServerMessage(1003, "Некорректно заполнен логин пользователя");
    if( !validPassword(newUser.getPassword())) return  new ServerMessage(1004, "Некорректно заполнен пароль пользователя");
    if( isExistUser(newUser)) return new ServerMessage(1005, "Пользователь с таким email уже существует");

   //хэширование пароля
  // newUser.setPassword(PasswordEncoderGenerator.pasHash(newUser.getPassword()));


    if (userDAO.newUser(newUser)) {
        System.out.print("Запись в базу прошла");
        return new ServerMessage(3001, "Пользователь успешно записан в базу");

    } else {

        System.out.print("Запись в базу не прошла");
        return new ServerMessage(1006, "Ошибка при записи User'а в базу");
    }

}


    public boolean isExistUser(User user){

        if(userDAO.getUserSearch(user.getEmail().toLowerCase()).size()<1){
            System.out.println("Новый пользователь!"+user.getEmail());
            return false;
        }else {
            System.out.println("Я нашел вот что"+userDAO.getUserSearch(user.getEmail()).toString());
            System.out.println("Такой пользователь уже существует"+user.getEmail());
            return true;
        }
    }


public boolean validStringRU(String string){
    Pattern p = Pattern.compile("^[а-яА-ЯЁьъ]{3,15}$");
    Matcher m = p.matcher(string);
    return  m.matches();
}
public boolean validEmail(String emailStr){

       Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
       return matcher.find();
    }

    public boolean validPassword(String string){
        Pattern p = Pattern.compile("^[a-zA-Z0-9_-]{4,256}$");
        Matcher m = p.matcher(string);
        return  m.matches();
    }
}

