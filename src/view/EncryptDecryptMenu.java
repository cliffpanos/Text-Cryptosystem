package view;

import resources.Resources;
import controller.Encryptor;

import javafx.scene.effect.GaussianBlur;

import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.text.Font;



public class EncryptDecryptMenu extends StackPane {

    private VBox menu = new VBox(40);
    private static TextField passwordField = new TextField();

    public EncryptDecryptMenu() {
        //this.getStylesheets().add(this.getClass()
        //.getResource("mainScreenStyle.css").toExternalForm());
        //cliffo will fix this^ later

        this.setBackground(null);

        double width = (double) MainScreen.getStageWidth() * 0.2;
        double height = (double) MainScreen.getStageHeight();

        VBox backgroundBlur = new VBox();
        backgroundBlur.setPrefWidth(width);
        backgroundBlur.setPrefHeight(height);
        backgroundBlur.setBackground(new Background(new BackgroundFill(Color
            .web("#212F3C", 0.985), new CornerRadii(5.0, 0.0, 0.0, 5.0, false),
            new Insets(0))));
        //backgroundBlur.setEffect(new GaussianBlur());

        //Rectangle background = new Rectangle(width, height,
        //    Color.web("#2E4053", 0.5));
        menu.setAlignment(Pos.CENTER);
        menu.setBackground(null);
        menu.setPrefWidth(width);
        menu.setPrefHeight((height));


        UIButton encryptButton = new UIButton((width - 50), 25, "Encrypt");
        UIButton decryptButton = new UIButton((width - 50), 25, "Decrypt");
        encryptButton.setOnMouseClicked(e -> {
                Encryptor.setKeyword("encrypt");
            });
        decryptButton.setOnMouseClicked(e -> {
                Encryptor.setKeyword("decrypt");
            });

        passwordField.setOnMouseClicked(e -> {
                Encryptor.setPassword(passwordField.getText());
            });
        passwordField.setPromptText("Enter password");
        passwordField.setMaxWidth(width - 50);

        VBox outerVBox = new VBox();
        outerVBox.setAlignment(Pos.TOP_LEFT);
        outerVBox.getChildren().addAll(new WindowButtons(), menu);
        this.getChildren().addAll(backgroundBlur, outerVBox);

        menu.getChildren().addAll(encryptButton, decryptButton, passwordField);

    }

    public static String getPasswordFieldText() {
        return passwordField.getText();
    }

}
