package com.serd.chat.util;


import com.serd.chat.model.ServerMessage;
import com.serd.chat.model.User;

/**
 * Created by serdukovaa on 17.12.2014.
 */
public interface RegistrationUtil {
    public ServerMessage addNewUser(User user);
    public boolean isExistUser(User user);
}
