package com.example.entity;

/**
 * @author 唐孝顺
 * @date 2022/3/22 18:18
 */
public class Email {
    private String sender;
    private String userName;
    private String password;

    public Email() {
    }

    public Email(String sender, String userName, String password) {
        this.sender = sender;
        this.userName = userName;
        this.password = password;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Email{" +
                "sender='" + sender + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
