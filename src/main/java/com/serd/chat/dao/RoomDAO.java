package com.serd.chat.dao;

import com.serd.chat.model.User;

import java.util.List;

public interface RoomDAO {
    public int returnChatRoomUniq(int user_chat_id, int user_welcome_id);

}