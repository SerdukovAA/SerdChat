package com.serd.chat.dao;

import java.util.List;

import com.serd.chat.model.ServerMessage;
import com.serd.chat.model.User;


public interface UserDAO {

    public List<User> getUsersQueue();
    public List<User> getUsersDenied();
    public List<User> getUsersAccepted();
    public List<User> getUserSearch(String string);
    public List<User> getUsersByLogin(String string,int user_id,boolean isFriend);
    public boolean newUser(User newUser);
    public boolean DeniedUser(int user_id);
    public List<User> getFriendList(int user_id);
    public User getUserById(int user_id);
    public boolean AcceptUser(int user_id);
    public ServerMessage AddUser(int user_id, int friend_id);
    public ServerMessage DelUser(int user_id, int friend_id);
    public boolean setAvatar(int user_id, String path);
    public User returnUserRoomKey(int room_uniq_id,int user_id);


}