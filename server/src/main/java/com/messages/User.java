package com.messages;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * @version 1.0
 * @ClassName User
 * @Author Tung
 * @Date 2020/6/26 13:35
 * @Description This is description of class
 * Website www.tunglee.ink
 * Github www.github.com/realLiamTurner
 */
@NoArgsConstructor
@Data
public class User implements Serializable {

    String name;
    String picture;
    Status status;
}
