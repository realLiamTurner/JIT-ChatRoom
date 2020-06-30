package com.server;

import com.exception.DuplicateUsernameException;
import com.messages.Message;
import com.messages.MessageType;
import com.messages.Status;
import com.messages.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @version 1.0
 * @ClassName Server
 * @Author Tung
 * @Date 2020/6/28 15:32
 * @Description 服务端程序
 * Website www.tunglee.ink
 * Github www.github.com/realLiamTurner
 */
public class Server {

    //设置变量
    private static final int PORT = 9888;
    private static final HashMap<String, User> names = new HashMap<>();
    private static HashSet<ObjectOutputStream> writers = new HashSet<>();
    private static ArrayList<User> users = new ArrayList<>();
    static Logger logger = LoggerFactory.getLogger(Server.class);


    public static void main(String[] args) throws Exception {
        logger.info("The chat server is running.");
        ServerSocket listener = new ServerSocket(PORT);

        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            listener.close();
        }
    }


    private static class Handler extends Thread {
        private String name;
        private final Socket socket;
        private final Logger logger = LoggerFactory.getLogger(Handler.class);
        private User user;
        private ObjectInputStream input;
        private OutputStream os;
        private ObjectOutputStream output;
        private InputStream is;

        public Handler(Socket socket) throws IOException {
            this.socket = socket;
        }


        @Override
        public void run() {
            logger.info("Attempting to connect a user...");
            try {
                is = socket.getInputStream();
                input = new ObjectInputStream(is);
                os = socket.getOutputStream();
                output = new ObjectOutputStream(os);

                Message firstMessage = (Message) input.readObject();
                checkDuplicateUsername(firstMessage);
                writers.add(output);
                sendNotification(firstMessage);
                addToList();

                while (socket.isConnected()) {
                    Message inputs = (Message) input.readObject();
                    if (inputs != null) {
                        logger.info(inputs.getType() + " - " + inputs.getName() + ": " + inputs.getMsg());
                        switch (inputs.getType()) {
                            case USER:
                            case VOICE:
                                write(inputs);
                                break;
                            case CONNECTED:
                                addToList();
                                break;
                            case STATUS:
                                changeStatus(inputs);
                                break;
                            default:

                        }
                    }
                }
            } catch (SocketException socketException) {
                logger.error("Socket Exception for user " + name);
            } catch (DuplicateUsernameException duplicateException) {
                logger.error("Duplicate Username : " + name);
            } catch (Exception e) {
                logger.error("Exception in run() method for user: " + name, e);
            } finally {
                closeConnections();
            }
        }

        private void changeStatus(Message inputmsg) throws IOException {
            logger.debug(inputmsg.getName() + " has changed status to  " + inputmsg.getStatus());
            Message msg = new Message();
            msg.setName(user.getName());
            msg.setType(MessageType.STATUS);
            msg.setMsg("");
            User userObj = names.get(name);
            userObj.setStatus(inputmsg.getStatus());
            write(msg);
        }


        private synchronized void checkDuplicateUsername(Message firstMessage) throws DuplicateUsernameException {
            logger.info(firstMessage.getName() + " is trying to connect");
            if (!names.containsKey(firstMessage.getName())) {
                this.name = firstMessage.getName();
                user = new User();
                user.setName(firstMessage.getName());
                user.setStatus(Status.ONLINE);
                user.setPicture(firstMessage.getPicture());

                users.add(user);
                names.put(name, user);

                logger.info(name + " has been added to the list");
            } else {
                logger.error(firstMessage.getName() + " is already connected");
                throw new DuplicateUsernameException(firstMessage.getName() + " is already connected");
            }
        }

        private Message sendNotification(Message firstMessage) throws IOException {
            Message msg = new Message();
            msg.setMsg("has joined the chat.");
            msg.setType(MessageType.NOTIFICATION);
            msg.setName(firstMessage.getName());
            msg.setPicture(firstMessage.getPicture());
            write(msg);
            return msg;
        }


        private Message removeFromList() throws IOException {
            logger.debug("removeFromList() method Enter");
            Message msg = new Message();
            msg.setMsg("has left the chat.");
            msg.setType(MessageType.DISCONNECTED);
            msg.setName("SERVER");
            msg.setList(names);
            write(msg);
            logger.debug("removeFromList() method Exit");
            return msg;
        }


        /**
         * @Author Tung
         * @Date 2020/6/28 15:37
         * @Description 用于显示用户已加入服务器
         * @Param * 参数
         * @Return com.messages.Message
         * @version 1.0
         */
        private Message addToList() throws IOException {
            Message msg = new Message();
            msg.setMsg("Welcome, You have now joined the server! Enjoy chatting!");
            msg.setType(MessageType.CONNECTED);
            msg.setName("SERVER");
            write(msg);
            return msg;
        }


        /**
         * @Author Tung
         * @Date 2020/6/28 15:38
         * @Description 创建消息类型并将其发送给侦听器。
         * @Param * 参数 msg
         * @Return void
         * @version 1.0
         */
        private void write(Message msg) throws IOException {
            for (ObjectOutputStream writer : writers) {
                msg.setList(names);
                msg.setUsers(users);
                msg.setCount(names.size());
                writer.writeObject(msg);
                writer.reset();
            }
        }


        /**
         * @Author Tung
         * @Date 2020/6/28 15:38
         * @Description 断开用户连接后，将关闭打开的连接并删除编写器
         * @Param * 参数
         * @Return void
         * @version 1.0
         */
        private synchronized void closeConnections() {
            logger.debug("closeConnections() method Enter");
            logger.info("HashMap names:" + names.size() + " writers:" + writers.size() + " usersList size:" + users.size());
            if (name != null) {
                names.remove(name);
                logger.info("User: " + name + " has been removed!");
            }
            if (user != null) {
                users.remove(user);
                logger.info("User object: " + user + " has been removed!");
            }
            if (output != null) {
                writers.remove(output);
                logger.info("Writer object: " + user + " has been removed!");
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                removeFromList();
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("HashMap names:" + names.size() + " writers:" + writers.size() + " usersList size:" + users.size());
            logger.debug("closeConnections() method Exit");
        }
    }
}
