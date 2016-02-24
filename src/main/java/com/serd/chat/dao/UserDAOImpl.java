package com.serd.chat.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import com.serd.chat.model.ServerMessage;
import com.serd.chat.model.User;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;



public class UserDAOImpl implements UserDAO {

    private JdbcTemplate jdbcTemplate;

    public UserDAOImpl(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // Поиск пользователей по логину;
    @Override
    public List<User> getUserSearch(String string) {
        String sql = "SELECT * FROM users WHERE email like ?";
        List<User> UsersAccepted = jdbcTemplate.query(sql, new Object[] { string } ,new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User aUser = new User();
                aUser.setUser_id(rs.getInt("user_id"));
                aUser.setFirstName(rs.getString("firstName"));
                aUser.setLastName(rs.getString("lastName"));
                aUser.setEmail(rs.getString("email"));
                aUser.setPassword(rs.getString("password"));
                aUser.setDateCreate(rs.getDate("dateCreate"));
                aUser.setUserRoleId(rs.getInt("user_role_id"));
                aUser.setAvatar(rs.getString("avatar"));
                return aUser;
            }

        });

        return UsersAccepted;
    }

    // Поиск пользователей по логину друзей и нет;
    @Override
    public List<User> getUsersByLogin(String string,int user_id ,boolean isFriend) {

        String sql;

        if(isFriend) {
            sql = "SELECT * FROM users WHERE  email like ? and user_id in (select friend_id from friend_list where user_id=?) and user_id <>? and user_role_id =1";
            List<User> UserFriends = jdbcTemplate.query(sql, new Object[] { string,user_id,user_id } ,new RowMapper<User>() {

                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User aUser = new User();
                    aUser.setUser_id(rs.getInt("user_id"));
                    aUser.setFirstName(rs.getString("firstName"));
                    aUser.setLastName(rs.getString("lastName"));
                    aUser.setEmail(rs.getString("email"));
                    aUser.setPassword(rs.getString("password"));
                    aUser.setDateCreate(rs.getDate("dateCreate"));
                    aUser.setAvatar(rs.getString("avatar"));

                    aUser.setUserRoleId(8);
                    return aUser;

                 }
            });

                return UserFriends;




        }else {
                sql = "SELECT * FROM users WHERE  email like ? and user_id not in (select friend_id from friend_list where user_id=?) and user_id <>? and user_role_id =1";

                    List<User> UsersNotFriend = jdbcTemplate.query(sql, new Object[] { string,user_id,user_id } ,new RowMapper<User>() {

                                @Override
                                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                                    User aUser = new User();
                                    aUser.setUser_id(rs.getInt("user_id"));
                                    aUser.setFirstName(rs.getString("firstName"));
                                    aUser.setLastName(rs.getString("lastName"));
                                    aUser.setEmail(rs.getString("email"));
                                    aUser.setPassword(rs.getString("password"));
                                    aUser.setDateCreate(rs.getDate("dateCreate"));
                                    aUser.setUserRoleId(rs.getInt("user_role_id"));
                                    aUser.setAvatar(rs.getString("avatar"));

                                    return aUser;

                                }
                    });
                     return UsersNotFriend;


            }


    }

    // Поиск пользователя по ID;
    @Override
    public User getUserById(int user_id) {
        String sql = "SELECT * FROM users WHERE user_id like ?";
        User userFindet = jdbcTemplate.queryForObject(sql, new Object[]{user_id}, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User aUser = new User();
                aUser.setUser_id(rs.getInt("user_id"));
                aUser.setFirstName(rs.getString("firstName"));
                aUser.setLastName(rs.getString("lastName"));
                aUser.setEmail(rs.getString("email"));
                aUser.setPassword(rs.getString("password"));
                aUser.setDateCreate(rs.getDate("dateCreate"));
                aUser.setUserRoleId(rs.getInt("user_role_id"));
                aUser.setAvatar(rs.getString("avatar"));
                aUser.setUser_mix_key(rs.getString("user_mix_key"));
                return aUser;
            }

        });

        return userFindet;
    }


    @Override
    public List<User> getUsersQueue() {
        String sql = "SELECT * FROM users WHERE USER_ROLE_ID=0";
        List<User> UsersQueu = jdbcTemplate.query(sql, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User aUser = new User();
                aUser.setUser_id(rs.getInt("user_id"));
                aUser.setFirstName(rs.getString("firstName"));
                aUser.setLastName(rs.getString("lastName"));
                aUser.setEmail(rs.getString("email"));
                aUser.setPassword(rs.getString("password"));
                aUser.setDateCreate(rs.getDate("dateCreate"));
                aUser.setUserRoleId(rs.getInt("user_role_id"));
                aUser.setAvatar(rs.getString("avatar"));
                aUser.setUser_mix_key(rs.getString("user_mix_key"));
                return aUser;
            }

        });

        return UsersQueu;
    }

   // Получить список отклоненных пользователей;
    @Override
    public List<User> getUsersDenied() {
        String sql = "SELECT * FROM users WHERE USER_ROLE_ID=3";
        List<User> UsersDenied = jdbcTemplate.query(sql, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User aUser = new User();
                aUser.setUser_id(rs.getInt("user_id"));
                aUser.setFirstName(rs.getString("firstName"));
                aUser.setLastName(rs.getString("lastName"));
                aUser.setEmail(rs.getString("email"));
                aUser.setPassword(rs.getString("password"));
                aUser.setDateCreate(rs.getDate("dateCreate"));
                aUser.setUserRoleId(rs.getInt("user_role_id"));
                aUser.setAvatar(rs.getString("avatar"));
                aUser.setUser_mix_key(rs.getString("user_mix_key"));
                return aUser;
            }

        });

        return UsersDenied;
    }
    // Получить список список друзей;
    @Override
    public List<User> getFriendList(int user_id) {
        String sql = "SELECT * FROM users WHERE USER_ID in (SELECT friend_id from friend_list where user_id=?) and user_id<>?";
        List<User> UsersFriend = jdbcTemplate.query(sql,new Object[] {user_id,user_id} ,new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User aUser = new User();
                aUser.setUser_id(rs.getInt("user_id"));
                aUser.setFirstName(rs.getString("firstName"));
                aUser.setLastName(rs.getString("lastName"));
                aUser.setEmail(rs.getString("email"));
                aUser.setPassword(rs.getString("password"));
                aUser.setDateCreate(rs.getDate("dateCreate"));
                aUser.setUserRoleId(rs.getInt("user_role_id"));
                aUser.setAvatar(rs.getString("avatar"));
                aUser.setUser_mix_key(rs.getString("user_mix_key"));
                return aUser;
            }

        });

        return UsersFriend;
    }

   // Получить список всех одобренных и по сути действующи пользователей;
    @Override
    public List<User> getUsersAccepted() {
        String sql = "SELECT * FROM users WHERE USER_ROLE_ID=1";
        List<User> UsersAccepted = jdbcTemplate.query(sql, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User aUser = new User();
                aUser.setUser_id(rs.getInt("user_id"));
                aUser.setFirstName(rs.getString("firstName"));
                aUser.setLastName(rs.getString("lastName"));
                aUser.setEmail(rs.getString("email"));
                aUser.setPassword(rs.getString("password"));
                aUser.setDateCreate(rs.getDate("dateCreate"));
                aUser.setUserRoleId(rs.getInt("user_role_id"));
                aUser.setAvatar(rs.getString("avatar"));
                aUser.setUser_mix_key(rs.getString("user_mix_key"));
                return aUser;
            }

        });

        return UsersAccepted;
    }



//Одобрение пользователя администратором
    @Override
     public boolean AcceptUser(int user_id) {
        String sql = "UPDATE users SET USER_ROLE_ID=1 WHERE user_id=?";

        int rows = jdbcTemplate.update(sql, user_id);
        if (rows == 1) {
            return true;
        } else {
            return false;
        }

    }
  //Добавление пользователя во френд лист
    @Override
    public ServerMessage AddUser(int user_id, int friend_id) {
       String sqlU = "SELECT count(*) FROM friend_list WHERE user_id =? and friend_id=?";
       int count = jdbcTemplate.queryForObject(sqlU,new Object[]{user_id, friend_id}, Integer.class);
       if(count > 0) return new ServerMessage(3007, "Пользователь уже у Вас в контактах");


        String sql = "INSERT INTO friend_list (USER_ID, FRIEND_ID) VALUES(?,?)";
        int rows = jdbcTemplate.update(sql, user_id,friend_id);

        if (rows == 1) {
            return new ServerMessage(3008, "Пользователь успешно добавлен в контакты");
        } else {
            return new ServerMessage(3009, "Пользователь не добавлен. Неизвестная ощибка");
        }

    }
    //Удаление пользователя во френд лист
    @Override
    public ServerMessage DelUser(int user_id, int friend_id) {
        String sqlU = "SELECT count(*) FROM friend_list WHERE user_id =? and friend_id=?";
        int count = jdbcTemplate.queryForObject(sqlU,new Object[]{user_id, friend_id}, Integer.class);
        if(count == 0) return new ServerMessage(2007, "Пользователь нет у Вас в контактах");


        String sql = "DELETE FROM friend_list where USER_ID =? and FRIEND_ID =?";
        int rows = jdbcTemplate.update(sql, user_id,friend_id);

        if (rows == 1) {
            return new ServerMessage(3010, "Пользователь удален из контактов");
        } else {
            return new ServerMessage(2009, "Пользователь не удален. Неизвестная ощибка");
        }

    }
//Отклонение пользователя администратором
    @Override
    public boolean  DeniedUser(int user_id) {



        String sql = "UPDATE users SET USER_ROLE_ID=3 WHERE user_id=?";

        int rows = jdbcTemplate.update(sql, user_id);
        if( rows == 1){
            return true;
        }else{
            return false;
        }

    }
    //Регистрация нового юзера
    @Override
    public boolean newUser(User newUser) {
        newUser.setDateCreate(new Date());
        System.out.print(" в базу прошt пароль" + newUser.getPassword());
        String sql = "INSERT INTO users (FIRSTNAME,LASTNAME,DATECREATE,EMAIL,PASSWORD,USER_ROLE_ID,USER_MIX_KEY)" +
                "VALUES(? , ? ,?, ? , ?, ?,?)";
        int rows = jdbcTemplate.update(sql, newUser.getFirstName(),newUser.getLastName(), newUser.getDateCreate(), newUser.getEmail().toLowerCase(), newUser.getPassword(), 0, newUser.getUser_mix_key());

        if (rows == 1) {
            return true;
        } else {
            return false;
        }

    }

    //Аватарка
    @Override
    public boolean setAvatar(int user_id, String path) {
        String sql = "UPDATE users Set avatar=? WHERE user_id=?";

        int rows = jdbcTemplate.update(sql,path,user_id);
        if (rows == 1) {
            return true;
        } else {
            return false;
        }

    }


    //вернуть список ключей
    @Override
    public User returnUserRoomKey(int room_uniq_id, int user_id) {

        String sql = "SELECT * FROM users WHERE user_id in (select user_welcome_id from rooms where room_uniq_id =? AND user_welcome_id <>?)";

        User userWithKey = jdbcTemplate.queryForObject(sql, new Object[]{room_uniq_id,user_id}, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User aUser = new User();
                aUser.setUser_id(rs.getInt("user_id"));
                aUser.setFirstName(rs.getString("firstName"));
                aUser.setLastName(rs.getString("lastName"));
                aUser.setEmail(rs.getString("email"));
                aUser.setPassword(rs.getString("password"));
                aUser.setDateCreate(rs.getDate("dateCreate"));
                aUser.setUserRoleId(rs.getInt("user_role_id"));
                aUser.setAvatar(rs.getString("avatar"));
                aUser.setUser_mix_key(rs.getString("user_mix_key"));
                return aUser;
            }

        });

        return userWithKey;
    }




    public String test(){
        return  "Тест пройден";
    }

}
