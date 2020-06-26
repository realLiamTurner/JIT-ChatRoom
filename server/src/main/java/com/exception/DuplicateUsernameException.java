package com.exception;

/**
 * @version 1.0
 * @ClassName DuplicateUsernameException
 * @Author Tung
 * @Date 2020/6/26 13:35
 * @Description This is description of class
 * Website www.tunglee.ink
 * Github www.github.com/realLiamTurner
 */
public class DuplicateUsernameException extends Exception {
    public DuplicateUsernameException(String message){
        super(message);
    }
}
