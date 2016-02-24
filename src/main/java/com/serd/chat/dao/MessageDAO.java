package com.serd.chat.dao;

import com.serd.chat.model.Message;

import java.util.Date;
import java.util.List;

public interface MessageDAO {
    public boolean writeNewMessage(String message, int from_user_id, int  room_uniq_id);
    public List<Message> getMessageDaysAgo(int k, int room_uniq_id);
}