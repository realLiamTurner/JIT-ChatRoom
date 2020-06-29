package com.client.login;

import com.client.chatwindow.ChatController;
import com.client.chatwindow.Listener;
import com.client.util.ResizeHelper;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * @version 1.0
 * @ClassName LoginController
 * @Author Tung
 * @Date 2020/6/26 13:28
 * @Description 处理登录界面的逻辑
 * Website www.tunglee.ink
 * Github www.github.com/realLiamTurner
 */
public class LoginController implements Initializable {
    @FXML
    private ImageView Defaultview;
    @FXML
    private ImageView Taoview;
    @FXML
    private ImageView Tungview;
    @FXML
    public TextField hostnameTextfield;
    @FXML
    private TextField portTextfield;
    @FXML
    private TextField usernameTextfield;
    @FXML
    private ChoiceBox imagePicker;
    @FXML
    private Label selectedPicture;
    public static ChatController con;
    @FXML
    private BorderPane borderPane;
    private double xOffset;
    private double yOffset;
    private Scene scene;
    private static LoginController instance;

    public LoginController() {
        instance = this;
    }

    public static LoginController getInstance() {
        return instance;
    }

    /**
     * @Author Tung
     * @Date 2020/6/28 10:41
     * @Description 登录窗口的按钮操作
     * @Param  * 参数
     * @Return void
     * @version 1.0
     */
    public void loginButtonAction() throws IOException {
        // 获取登陆面板各项的内容
        String hostname = hostnameTextfield.getText();
        int port = Integer.parseInt(portTextfield.getText());
        String username = usernameTextfield.getText();
        String picture = selectedPicture.getText();

        // 加载用于渲染聊天界面的ui文件
        FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("/views/ChatView.fxml"));
        Parent window = (Pane) fmxlLoader.load();
        con = fmxlLoader.<ChatController>getController();
        Listener listener = new Listener(hostname, port, username, picture, con);
        Thread x = new Thread(listener);
        x.start();
        this.scene = new Scene(window);
    }

    public void showScene() throws IOException {
        Platform.runLater(() -> {
            Stage stage = (Stage) hostnameTextfield.getScene().getWindow();
            stage.setResizable(true);
            stage.setWidth(1040);
            stage.setHeight(620);

            stage.setOnCloseRequest((WindowEvent e) -> {
                Platform.exit();
                System.exit(0);
            });
            stage.setScene(this.scene);
            stage.setMinWidth(800);
            stage.setMinHeight(300);
            ResizeHelper.addResizeListener(stage);
            stage.centerOnScreen();
            con.setUsernameLabel(usernameTextfield.getText());
            con.setImageLabel(selectedPicture.getText());
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imagePicker.getSelectionModel().selectFirst();
        selectedPicture.textProperty().bind(imagePicker.getSelectionModel().selectedItemProperty());
        selectedPicture.setVisible(false);

        // 拖放
        borderPane.setOnMousePressed(event -> {
            xOffset = MainLauncher.getPrimaryStage().getX() - event.getScreenX();
            yOffset = MainLauncher.getPrimaryStage().getY() - event.getScreenY();
            borderPane.setCursor(Cursor.CLOSED_HAND);
        });

        borderPane.setOnMouseDragged(event -> {
            MainLauncher.getPrimaryStage().setX(event.getScreenX() + xOffset);
            MainLauncher.getPrimaryStage().setY(event.getScreenY() + yOffset);

        });

        borderPane.setOnMouseReleased(event -> {
            borderPane.setCursor(Cursor.DEFAULT);
        });

        imagePicker.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>) (selected, oldPicture, newPicture) -> {
            if (oldPicture != null) {
                switch (oldPicture) {
                    case "Default":
                        Defaultview.setVisible(false);
                        break;
                    case "Tung":
                        Tungview.setVisible(false);
                        break;
                    case "Tao":
                        Taoview.setVisible(false);
                        break;
                    default:
                }
            }
            if (newPicture != null) {
                switch (newPicture) {
                    case "Default":
                        Defaultview.setVisible(true);
                        break;
                    case "Tung":
                        Tungview.setVisible(true);
                        break;
                    case "Tao":
                        Taoview.setVisible(true);
                        break;
                    default:
                }
            }
        });
        int numberOfSquares = 30;
        while (numberOfSquares > 0) {
            generateAnimation();
            numberOfSquares--;
        }
    }

    /**
     * @Author Tung
     * @Date 2020/6/27 13:29
     * @Description 此方法用于在登录窗口上生成动画，它将生成随机整数来确定每个正方形的大小，速度，起点和方向。
     * @Param
     * @Return
     * @version 1.0
     */
    public void generateAnimation() {
        Random rand = new Random();
        int sizeOfSqaure = rand.nextInt(50) + 1;
        int speedOfSqaure = rand.nextInt(10) + 5;
        int startXPoint = rand.nextInt(420);
        int startYPoint = rand.nextInt(350);
        int direction = rand.nextInt(5) + 1;

        KeyValue moveXAxis = null;
        KeyValue moveYAxis = null;
        Rectangle r1 = null;

        switch (direction) {
            case 1:
                // 从左向右移动
                r1 = new Rectangle(0, startYPoint, sizeOfSqaure, sizeOfSqaure);
                moveXAxis = new KeyValue(r1.xProperty(), 350 - sizeOfSqaure);
                break;
            case 2:
                // 移至底部
                r1 = new Rectangle(startXPoint, 0, sizeOfSqaure, sizeOfSqaure);
                moveYAxis = new KeyValue(r1.yProperty(), 420 - sizeOfSqaure);
                break;
            case 3:
            case 6:
                // 左移，右移
                // 从左到右，从上到下
                r1 = new Rectangle(startXPoint, 0, sizeOfSqaure, sizeOfSqaure);
                moveXAxis = new KeyValue(r1.xProperty(), 350 - sizeOfSqaure);
                moveYAxis = new KeyValue(r1.yProperty(), 420 - sizeOfSqaure);
                break;
            case 4:
                // 从下往上移动
                r1 = new Rectangle(startXPoint, 420 - sizeOfSqaure, sizeOfSqaure, sizeOfSqaure);
                moveYAxis = new KeyValue(r1.xProperty(), 0);
                break;
            case 5:
                // 向左移动
                r1 = new Rectangle(420 - sizeOfSqaure, startYPoint, sizeOfSqaure, sizeOfSqaure);
                moveXAxis = new KeyValue(r1.xProperty(), 0);
                break;

            default:
                System.out.println("default");
        }

        r1.setFill(Color.web("#F89406"));
        r1.setOpacity(0.1);

        KeyFrame keyFrame = new KeyFrame(Duration.millis(speedOfSqaure * 1000), moveXAxis, moveYAxis);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        borderPane.getChildren().add(borderPane.getChildren().size() - 1, r1);
    }

    /**
     * @Author Tung
     * @Date 2020/6/27 13:28
     * @Description 终止申请
     * @Param
     * @Return
     * @version 1.0
     */
    public void closeSystem() {
        Platform.exit();
        System.exit(0);
    }

    public void minimizeWindow() {
        MainLauncher.getPrimaryStage().setIconified(true);
    }

    /**
     * @Author Tung
     * @Date 2020/6/27 13:28
     * @Description 向用户显示警报消息
     * @Param
     * @Return
     * @version 1.0
     */
    public void showErrorDialog(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告!");
            alert.setHeaderText(message);
            alert.setContentText("请检查防火墙问题，并检查服务器是否正在运行.");
            alert.showAndWait();
        });

    }
}
