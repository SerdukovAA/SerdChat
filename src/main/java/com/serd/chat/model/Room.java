package com.serd.chat.model;


import java.util.Date;

public class Room {

    private int room_id;
    private int room_uniq_id;
    private int user_chat_id;
    private int user_welcome_id;
    private Date dateCreate;
    private int is_confirm;
    private int is_room;


    public Room(int room_id, int room_uniq_id, int user_chat_id, int user_welcome_id, Date dateCreate, int is_confirm, int is_room) {

        this.room_id = room_id;
        this.room_uniq_id = room_uniq_id;
        this.user_chat_id = user_chat_id;
        this.user_welcome_id = user_welcome_id;
        this.dateCreate = dateCreate;
        this.is_confirm = is_confirm;
        this.is_room = is_room;
    }

    public Room() {

    }

    public int getRoom_uniq_id() {
        return room_uniq_id;
    }

    public void setRoom_uniq_id(int room_uniq_id) {
        this.room_uniq_id = room_uniq_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getUser_chat_id() {
        return user_chat_id;
    }

    public void setUser_chat_id(int user_chat_id) {
        this.user_chat_id = user_chat_id;
    }

    public int getUser_welcome_id() {
        return user_welcome_id;
    }

    public void setUser_welcome_id(int user_welcome_id) {
        this.user_welcome_id = user_welcome_id;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public int getIs_confirm() {
        return is_confirm;
    }

    public void setIs_confirm(int is_confirm) {
        this.is_confirm = is_confirm;
    }

    public int getIs_room() {
        return is_room;
    }

    public void setIs_room(int is_room) {
        this.is_room = is_room;
    }




}