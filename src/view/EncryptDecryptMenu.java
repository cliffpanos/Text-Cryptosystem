package view;

import resources.Resources;
import controller.Encryptor;

import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Font;



public class EncryptDecryptMenu extends StackPane {

    private VBox menu = new VBox(40);

    public EncryptDecryptMenu() {
        //this.getStylesheets().add(this.getClass()
        //.getResource("mainScreenStyle.css").toExternalForm());
        //cliffo will fix this^ later

        double width = (double) MainScreen.getStageWidth() * 0.2;
        menu.setPrefWidth(width);
        double height = (double) MainScreen.getStageHeight();
        menu.setPrefHeight(height);
        menu.setBackground(new Background(new BackgroundImage(
            Resources.getImage("edMenuBackground.png"),
            BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
            BackgroundPosition.CENTER, new BackgroundSize(menu.getWidth(),
            menu.getHeight(), false, false, true, false))));
        menu.setAlignment(Pos.CENTER);


        UIButton encryptButton = new UIButton((width - 20), 25, "Encrypt");
        UIButton decryptButton = new UIButton((width - 20), 25, "Decrypt");

        this.getChildren().add(menu);
        menu.getChildren().addAll(encryptButton, decryptButton);
    }

    public StackPane getRootNode() {
        return this;
    }

}
