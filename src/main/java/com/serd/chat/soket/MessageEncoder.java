package com.serd.chat.soket;


import com.serd.chat.model.Message;
import com.serd.chat.model.User;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EndpointConfig;


import javax.websocket.EncodeException;
import javax.websocket.Encoder;


public class MessageEncoder implements Encoder.Text<Message> {

    @Override
    public String encode(Message message) throws EncodeException {
        User subject = message.getSubject();


        JsonObject jsonObject = Json.createObjectBuilder()
                .add("user_id", subject.getUser_id())
                .add("content", message.getContent())
                .add("firstname", subject.getFirstName())
                .add("dateTime", String.valueOf(message.getDateTime()))
                .add("lastname", subject.getLastName())
                .add("avatar", subject.getAvatar())
                .build();
        return jsonObject.toString();

    }

    @Override
    public void init(EndpointConfig ec) {
        System.out.println("MessageEncoder - init method called");
    }

    @Override
    public void destroy() {
        System.out.println("MessageEncoder - destroy method called");
    }

}