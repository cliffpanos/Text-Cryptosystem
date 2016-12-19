package view;

import resources.Resources;
import controller.Encryptor;

import javafx.scene.effect.GaussianBlur;

import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.text.Font;



public class EncryptDecryptMenu extends StackPane {

    private VBox menu = new VBox(50); //holds the two buttons & passwordField
    private static TextField passwordField = new TextField();

    public EncryptDecryptMenu() {
        //this.getStylesheets().add(this.getClass()
        //.getResource("mainScreenStyle.css").toExternalForm());
        //cliffo will fix this^ later

        this.setBackground(null);

        double width = (double) MainScreen.getStageWidth() * 0.18;
        double height = (double) MainScreen.getStageHeight();

        VBox backgroundBlur = new VBox();
        backgroundBlur.setPrefWidth(width);
        backgroundBlur.setPrefHeight(height);
        backgroundBlur.setBackground(new Background(new BackgroundFill(Color
            .web("#212F3C", 0.985), new CornerRadii(5.0, 0.0, 0.0, 5.0, false),
            new Insets(0))));
        //backgroundBlur.setEffect(new GaussianBlur());

        menu.setAlignment(Pos.CENTER);
        menu.setBackground(null);
        menu.setPrefWidth(width);
        menu.setPrefHeight((height));

        // Encrpyt/Decrypt Buttons
        UIButton encryptButton = new UIButton((width - 50), 30, "Encrypt");
        UIButton decryptButton = new UIButton((width - 50), 30, "Decrypt");
        encryptButton.setOnMouseClicked(e -> {
                Encryptor.setKeyword("encrypt");

                System.out.println("encrypt pressed");

                // Shades this button and makes sure other button is not shaded
                encryptButton.setBackgroundColor(Color.web("#B3B3B3", 0.9));
                decryptButton.setBackgroundColor(Color.web("#F7F7F7", 0.9));
            });
        decryptButton.setOnMouseClicked(e -> {
                Encryptor.setKeyword("decrypt");

                // Shades this button and makes sure other button is not shaded
                decryptButton.setBackgroundColor(Color.web("#B3B3B3", 0.9));
                encryptButton.setBackgroundColor(Color.web("#F7F7F7", 0.9));
            });

        // Password Field TextField
        passwordField.setOnAction(e -> {
                Encryptor.setPassword(passwordField.getText());
            });
        passwordField.setPromptText("Enter password");
        passwordField.setMaxWidth(width - 50);
        passwordField.setAlignment(Pos.CENTER);

        //Used to hold the window buttons and Encrypt / Decrypt menu
        VBox outerVBox = new VBox();
        WindowButtons windowButtons = new WindowButtons(width);
        outerVBox.setAlignment(Pos.TOP_LEFT);
        outerVBox.getChildren().addAll(windowButtons.getRootNode(), menu);
        this.getChildren().addAll(backgroundBlur, outerVBox);

        menu.getChildren().addAll(encryptButton, decryptButton, passwordField);

    }

    public static String getPasswordFieldText() {
        return passwordField.getText();
    }

}
