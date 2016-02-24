package com.serd.chat.dao;

import com.serd.chat.model.Message;
import com.serd.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public class MessageDAOImpl implements MessageDAO {

    @Autowired
    private UserDAO userDAO;

    private JdbcTemplate jdbcTemplate;

    public MessageDAOImpl(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public MessageDAOImpl()
    {

    }


    //Запись сообщения в базу
    @Override
    public boolean writeNewMessage(String message,int from_user_id, int room_uniq_id) {



        String sql = "INSERT INTO messages (subject_user_id,content,room_uniq_id,dateTime,attachment)" +
                "VALUES(?,?,?,?,?)";
        int rows = jdbcTemplate.update(sql, from_user_id, message, room_uniq_id, new Date(),"пока заглушка");

        if (rows == 1) {
            return true;
        } else {
            return false;
        }
    }



    // Получить историю сообщений;
    @Override
    public List<Message> getMessageDaysAgo(int k, int room_uniq_id) {

       // System.out.println("Зашел сюда за историей" + room_uniq_id);
       // System.out.println("К" + k);
        String sql = "Select * from (SELECT *,(@rownum := @rownum + 1) AS rowNumber FROM messages JOIN (SELECT @rownum:= 0) r WHERE room_uniq_id=? ORDER BY message_id desc" +
                ")AS tab1  where rowNumber >(0+7*?) and rowNumber <=(7+7*?) ORDER BY message_id asc;";

        List<Message> messageHistory = jdbcTemplate.query(sql,new Object[] {room_uniq_id,k,k} ,new RowMapper<Message>() {

            @Override
            public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
               Message aMessage = new Message();
                aMessage.setMessage_id(rs.getInt("message_id"));
                aMessage.setSubject(userDAO.getUserById(rs.getInt("subject_user_id")));
                aMessage.setDateTime(rs.getTimestamp("dateTime"));
                aMessage.setContent(rs.getString("content"));
                aMessage.setAttachment(rs.getString("attachment"));
                aMessage.setRoom_uniq_id(rs.getInt("room_uniq_id"));
                return aMessage;
            }

        });
      //  System.out.println("лИСТ СООБЩЕНИЙ" +messageHistory );
        return messageHistory;
    }

}
