package com.serd.chat.soket;

import com.serd.chat.dao.MessageDAO;
import com.serd.chat.dao.UserDAO;
import com.serd.chat.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.server.standard.SpringConfigurator;
import java.io.IOException;
import java.util.*;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;



@ServerEndpoint(value = "/echo/{id-room}",
        configurator = SpringConfigurator.class,
        encoders = { MessageEncoder.class },
        decoders = { MessageDecoder.class })
public class ServerWebSocket {


    private final MessageDAO messageDAO;
    private final UserDAO userDAO;

    @Autowired
    public ServerWebSocket (MessageDAO messageDAO, UserDAO userDAO) {
        this.userDAO = userDAO;
        this.messageDAO = messageDAO;
    }



    /** All open WebSocket sessions */
    static Set<Session> room = Collections.synchronizedSet(new HashSet<Session>());
    static Map<String,Set<Session>> rooms = Collections.synchronizedMap(new HashMap<String, Set<Session>>());

    @OnOpen
    public void onOpen(Session session, @PathParam("id-room") String idRoom) {

        System.out.println(session.getId() + " has opened a connection");
              if (rooms.containsKey(idRoom)) {
            room = rooms.get(idRoom);
            room.add(session);
        } else {
            Set<Session> newRoom = new HashSet<Session>();
            newRoom.add(session);
            rooms.put(idRoom,newRoom);
        }
    }

    @OnMessage
    public void onMessage(Message message, Session session, @PathParam("id-room") String idRoom ) throws IOException, EncodeException
    {
        System.out.println("Message from " + session.getId() + ": " + message.getContent());
        System.out.println("User_id " + message.getSubject().getUser_id());
        int from_user_id = message.getSubject().getUser_id();
        int room_uniq = Integer.parseInt(idRoom);
        message.setSubject(userDAO.getUserById(message.getSubject().getUser_id()));
        System.out.println(message+"  "+from_user_id+"  "+ room_uniq);
        try{
           messageDAO.writeNewMessage(message.getContent(), from_user_id, room_uniq);
            System.out.println("Удачно записали сообщение в базу");
        }catch (Exception e) {
            e.printStackTrace();
        }

           try {
               room = rooms.get(idRoom);
               for (Session userSession : room) {
                   if (userSession.isOpen()) {
                       userSession.getBasicRemote().sendObject(message);
                   }
               }
           } catch (IOException ex) {
               ex.printStackTrace();
           }


    }

    @OnClose
    public void onClose(Session session,@PathParam("id-room") String idRoom ) {

                    System.out.println("Session " + session.getId() + " has ended");
                    room = rooms.get(idRoom);
                    room.remove(session);

    }
}