package com.serd.chat.model;


public class ServerMessage {



    private int status;


    private String content;


    public ServerMessage()
    {}

    public ServerMessage(int status, String content)
    {

        this.status = status;
        this.content = content;

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}