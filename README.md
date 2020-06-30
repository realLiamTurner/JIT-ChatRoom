# JIT-ChatRoom
基于JavaFX的聊天室

## 登录界面
<p align = "center">
<img src="https://cdn.jsdelivr.net/gh/realLiamTurner/Image-Hosting-Service/JIT-Chatroom/README/login2.png" />
</p>

## 聊天界面
<p align = "center">
<img src="https://cdn.jsdelivr.net/gh/realLiamTurner/Image-Hosting-Service/JIT-Chatroom/README/ChatRoom.png"/>
</p>

## 使用
1. 先启动com.server.Server下的main()方法

### 服务端入口
com.server.Server类
``` java
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
```

2. 再启动com.client.login.MainLauncher下的main()方法

### 客户端入口
com.client.login.MainLauncher类
``` java
public static void main(String[] args) {
        launch(args);
    }
```

3. 如果端口被占用了,修改com.server.Server类下面的PORT的值即可:

``` java
private static final int PORT = 9888;
```


