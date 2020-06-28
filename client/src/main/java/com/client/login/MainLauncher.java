package com.client.login;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @version 1.0
 * @ClassName MainLauncher
 * @Author Tung
 * @Date 2020/6/26 13:28
 * @Description 客户端入口类
 * Website www.tunglee.ink
 * Github www.github.com/realLiamTurner
 */
public class MainLauncher extends Application {

    private static Stage primaryStageObj;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStageObj = primaryStage;
        // 加载根节点渲染所需的文件
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/LoginView.fxml"));
        // 初始化为白色无装饰的背景
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Socket Chat : Client version 0.3");
        // 设置窗口和最小化时的图标
        primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResource("images/plug.png").toString()));
        // 设置根节点的特定尺寸
        Scene mainScene = new Scene(root, 350, 420);
        mainScene.setRoot(root);
        // 不可调整大小
        primaryStage.setResizable(false);
        // 设置主场景
        primaryStage.setScene(mainScene);
        // 显示窗口
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }

    public static Stage getPrimaryStage() {
        return primaryStageObj;
    }
}