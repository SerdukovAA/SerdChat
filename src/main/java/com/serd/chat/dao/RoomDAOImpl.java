package com.serd.chat.dao;

import com.serd.chat.model.Room;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.util.Random;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {

    private JdbcTemplate jdbcTemplate;

    public RoomDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    //Регистрация нового юзера
    @Override
    public int returnChatRoomUniq(int user_chat_id, int user_welcome_id) {

        String sqlC = "SELECT count(*) FROM rooms WHERE (user_chat_id =? and user_welcome_id=?)or (user_chat_id =? and user_welcome_id=?)";
        int count = jdbcTemplate.queryForObject(sqlC, new Object[]{user_chat_id, user_welcome_id, user_welcome_id, user_chat_id}, Integer.class);



        if (count > 0) {
            String sqlR = "SELECT room_uniq_id FROM rooms WHERE (user_chat_id =? and user_welcome_id=?)or (user_chat_id =? and user_welcome_id=?)";
            int room_uniq_id = jdbcTemplate.queryForObject(sqlR, new Object[]{user_chat_id, user_welcome_id, user_welcome_id, user_chat_id}, Integer.class);
            return room_uniq_id;
        } else {
            Random rand = new Random();
            int randomInt = rand.nextInt(1000000);
            Date date = new Date();
            String RuID = "" + user_chat_id + user_welcome_id;
            String sql = "INSERT INTO rooms (room_uniq_id,user_chat_id,user_welcome_id,dateCreate,is_confirm,is_room,room_key)" +
                    "VALUES(? , ? ,?, ? , ?, ?,?)";
            int rows = jdbcTemplate.update(sql, RuID, user_chat_id, user_chat_id, date, 0, 1, randomInt);

            if (rows == 1) {
                String sql3 = "INSERT INTO rooms (room_uniq_id,user_chat_id,user_welcome_id,dateCreate,is_confirm,is_room,room_key)" +
                        "VALUES(? , ? ,?, ? , ?, ?,?)";
                int rows2 = jdbcTemplate.update(sql3, RuID, user_chat_id, user_welcome_id, date, 0, 1, randomInt);
                if (rows2 == 1) {
                    return Integer.parseInt(RuID);
                }else return 0;
            } else return 0;

        }

    }





}
