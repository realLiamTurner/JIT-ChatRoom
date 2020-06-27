package com.messages;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @version 1.0
 * @ClassName Message
 * @Author Tung
 * @Date 2020/6/26 13:35
 * @Description 信息的model
 * Website www.tunglee.ink
 * Github www.github.com/realLiamTurner
 */
@Data
@NoArgsConstructor
public class Message implements Serializable {

    private String name;
    private MessageType type;
    private String msg;
    private int count;
    private ArrayList<User> list;
    private ArrayList<User> users;
    private Status status;
    private byte[] voiceMsg;
    private String picture;

    public void setList(HashMap<String, User> userList) {
        this.list = new ArrayList<>(userList.values());
    }

}
