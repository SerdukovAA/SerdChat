package com.serd.chat.soket;



        import com.serd.chat.config.AppConfig;
        import com.serd.chat.dao.UserDAO;
        import com.serd.chat.dao.UserDAOImpl;
        import com.serd.chat.model.Message;
        import com.serd.chat.model.User;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.beans.factory.annotation.Configurable;
        import org.springframework.beans.factory.annotation.Qualifier;
        import org.springframework.context.ApplicationContext;
        import org.springframework.context.annotation.AnnotationConfigApplicationContext;
        import org.springframework.stereotype.Component;
        import org.springframework.stereotype.Controller;
        import org.springframework.stereotype.Service;
        import org.springframework.web.servlet.config.annotation.EnableWebMvc;

        import java.io.StringReader;
        import java.util.Date;

        import javax.json.Json;
        import javax.json.JsonObject;
        import javax.websocket.DecodeException;
        import javax.websocket.Decoder;
        import javax.websocket.EndpointConfig;



public class MessageDecoder implements Decoder.Text<Message> {

   @Override
    public Message decode(String jsonMessage) throws DecodeException {

        JsonObject jsonObject = Json.createReader(new StringReader(jsonMessage)).readObject();
        Message message = new Message();
        message.setContent(jsonObject.getString("content"));
        message.setDateTime(new Date());
        message.setAttachment(jsonObject.getString("attachment"));
        message.setSubject(new User());
        message.getSubject().setUser_id(Integer.parseInt(jsonObject.getString("user_id")));
        return message;

    }

    @Override
    public boolean willDecode(String jsonMessage) {
        try {
            // Check if incoming message is valid JSON
            System.out.println("MessageDecoder - читается");
            Json.createReader(new StringReader(jsonMessage)).readObject();

           return true;
        } catch (Exception e) {
            System.out.println("MessageDecoder - нечитается");
            return false;
        }


    }

    @Override
    public void init(EndpointConfig ec) {
        System.out.println("MessageDecoder -init method called");
    }

    @Override
    public void destroy() {
        System.out.println("MessageDecoder - destroy method called");
    }

}
