package com.client.chatwindow;

import com.messages.User;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;


/**
 * @version 1.0
 * @ClassName CellRenderer
 * @Author Tung
 * @Date 2020/6/26 13:29
 * @Description A Class for Rendering users images / name on the userlist.
 * Website www.tunglee.ink
 * Github www.github.com/realLiamTurner
 */
class CellRenderer implements Callback<ListView<User>,ListCell<User>>{
        @Override
    public ListCell<User> call(ListView<User> p) {

        ListCell<User> cell = new ListCell<User>(){

            @Override
            protected void updateItem(User user, boolean bln) {
                super.updateItem(user, bln);
                setGraphic(null);
                setText(null);
                if (user != null) {
                    HBox hBox = new HBox();

                    Text name = new Text(user.getName());

                    ImageView statusImageView = new ImageView();
                    Image statusImage = new Image(getClass().getClassLoader().getResource("images/" + user.getStatus().toString().toLowerCase() + ".png").toString(), 16, 16,true,true);
                    statusImageView.setImage(statusImage);

                    ImageView pictureImageView = new ImageView();
                    Image image = new Image(getClass().getClassLoader().getResource("images/" + user.getPicture().toLowerCase() + ".jpg").toString(),50,50,true,true);
                    pictureImageView.setImage(image);

                    hBox.getChildren().addAll(statusImageView, pictureImageView, name);
                    hBox.setAlignment(Pos.CENTER_LEFT);

                    setGraphic(hBox);
                }
            }
        };
        return cell;
    }
}