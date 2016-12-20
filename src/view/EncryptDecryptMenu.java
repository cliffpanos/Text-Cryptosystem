package view;

import resources.Resources;
import controller.Encryptor;

import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.BoxBlur;

import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
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
import javafx.scene.text.TextAlignment;



public class EncryptDecryptMenu extends StackPane {

    private VBox menu = new VBox(40); //holds the two buttons & passwordField
    private static TextField passwordField = new TextField();

    private static String passwordFieldText = "";
    private static int lastCaretPosition = 0;

    public EncryptDecryptMenu() {
        //this.getStylesheets().add(this.getClass()
        //.getResource("mainScreenStyle.css").toExternalForm());
        //cliffo will fix this^ later

        this.setBackground(null);

        double width = MainScreen.getStageWidth() * 0.18;
        double height = MainScreen.getStageHeight();

        VBox backgroundBlur = new VBox();
        backgroundBlur.setPrefWidth(width);
        backgroundBlur.setPrefHeight(height);
        backgroundBlur.setBackground(new Background(new BackgroundFill(Color
            .web("#212F3C", 0.968), new CornerRadii(5.0, 0.0, 0.0, 5.0, false),
            new Insets(0))));
        //backgroundBlur.setEffect(new BoxBlur(width, (height - 20), 3));

        menu.setAlignment(Pos.CENTER);
        menu.setBackground(null);
        menu.setMaxWidth(width);
        menu.setPrefHeight(height);

        // Password Field TextField Actions
        passwordField.setOnAction(e -> {
                Encryptor.setPassword(passwordField.getText());
                passwordFieldText = passwordField.getText();
            });
        passwordField.setOnMouseEntered(e -> {
                passwordField.setText(passwordFieldText);
                passwordField.positionCaret(lastCaretPosition);
                passwordField.setEditable(true);
            });
        passwordField.setOnMouseExited(e -> {
                passwordFieldText = passwordField.getText();
                lastCaretPosition = passwordField.getCaretPosition();
                //Replace the password with * characters so that onlookers
                //cannot see it
                String passwordCoverUp = "";
                for (char c : passwordFieldText.toCharArray()) {
                    passwordCoverUp += "*";
                }
                passwordField.setText(passwordCoverUp);
                passwordField.setEditable(false);
            });
        passwordField.setPromptText("enter here");
        passwordField.setMaxWidth(width - 80);
        passwordField.setAlignment(Pos.CENTER);

        //Create the password trio: the icon, the TextField, and the Label below
        ImageView passwordIcon = Resources.getImageView("password.tiff",
            (65));
        Label pwInstructions = new Label("Enter Password");
        pwInstructions.setTextFill(Color.WHITE);
        pwInstructions.setTextAlignment(TextAlignment.CENTER);
        VBox passwordVBox = new VBox(10);
        passwordVBox.getChildren().addAll(passwordIcon, passwordField,
            pwInstructions);
        passwordVBox.setAlignment(Pos.CENTER);

        //menu holds the three central components in the EncryptDecryptMenu
        double dimension = MainScreen.getStageWidth() * 0.18;
        StackPane eStackPane = new TransparentButton("encrypt.png", "Encrypt",
            dimension);
        StackPane dStackPane = new TransparentButton("decrypt.png", "Decrypt",
            dimension);
        menu.getChildren().addAll(eStackPane, dStackPane, passwordVBox);

        //Used to hold the window buttons and Encrypt / Decrypt menu
        VBox outerVBox = new VBox();
        WindowButtons windowButtons = new WindowButtons(width);
        outerVBox.setAlignment(Pos.TOP_LEFT);
        outerVBox.getChildren().addAll(windowButtons.getRootNode(), menu);
        this.getChildren().addAll(backgroundBlur, outerVBox);

    }

    public static String getPasswordFieldText() {
        return passwordFieldText;
    }


}
