package com.serd.chat.model;


import java.util.Date;

public class Message {



    private int message_id;
    private String content;
    private Date dateTime;
    private User subject;
    private String attachment;
    private int room_uniq_id;



    public Message()
    {}

    public Message(int message_id, String content, Date dateTime, User subject, String attachment, int room_uniq_id)
    {

        this.message_id = message_id;
        this.content = content;
        this.dateTime = dateTime;
        this.subject = subject ;
        this.attachment =attachment;
        this.room_uniq_id = room_uniq_id;

    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public User getSubject() {
        return subject;
    }

    public void setSubject(User subject) {
        this.subject = subject;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public int getRoom_uniq_id() {
        return room_uniq_id;
    }

    public void setRoom_uniq_id(int room_uniq_id) {
        this.room_uniq_id = room_uniq_id;
    }


    @Override
    public String toString(){
        String mes =  getContent()+" Написано юзером"+getSubject().toString();
        return mes;
    }

}