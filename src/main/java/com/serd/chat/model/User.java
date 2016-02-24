
package com.serd.chat.model;


import java.util.Date;

public class User {

    private int user_id;
    private String firstName;
    private String lastName;
    private Date dateCreate;
    private String email;
    private String password;
    private String avatar;
    private int userRoleId;
    private String user_mix_key;



    public User(int user_id, String firstName, String lastName, Date dateCreate, String email, String password,String avatar, int userRoleId, String user_mix_key)
    {

        this.user_id = user_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateCreate = dateCreate;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.userRoleId = userRoleId;
        this.user_mix_key=user_mix_key;
    }

    public User()
    {

    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getUser_mix_key() {
        return user_mix_key;
    }

    public void setUser_mix_key(String user_mix_key) {
        this.user_mix_key = user_mix_key;
    }


    @Override
    public String toString(){
        String name =  getFirstName()+" "+getLastName();
    return name;
    }
}