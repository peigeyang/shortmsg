package com.qbb.shortmsg.shortmsg.entity;

import javax.persistence.*;
import java.util.Date;


public class User {
    public User() {
    }

    public User(String phone,String passWord) {
        this.phone = phone;
    }

    private Date date;
    private String id;
    private String phone;
    private String passWord;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
