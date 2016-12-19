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
            .web("#212F3C", 0.975), new CornerRadii(5.0, 0.0, 0.0, 5.0, false),
            new Insets(0))));
        //backgroundBlur.setEffect(new BoxBlur(width, (height - 20), 3));

        menu.setAlignment(Pos.CENTER);
        menu.setBackground(null);
        menu.setPrefWidth(width);
        menu.setPrefHeight(height);

//These can be deleted
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
//Delete from here to the above outdented comment

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
        passwordField.setPromptText("**********");
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
        StackPane eStackPane = makeStackPane("encrypt.png", "Encrypt");
        StackPane dStackPane = makeStackPane("decrypt.png", "Decrypt");
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

    public StackPane makeStackPane(String imageURL, String text) {

        StackPane sp = new StackPane();

        double imageDecrement = (text.equals("Encrypt")) ? 7.5 : 0;
        ImageView imageView = Resources.getImageView(imageURL,
            (MainScreen.getStageWidth() * 0.05 - imageDecrement));

        //Set background stroke
        double dim = (MainScreen.getStageWidth() * 0.18) - 85;
        Rectangle background = new Rectangle(dim, dim, Color.TRANSPARENT);
        background.setArcWidth((double) dim / 4.0);
        background.setArcHeight((double) dim / 4.0);
        background.setStroke(Color.web("#F7F7F7"));
        sp.setOnMouseEntered(e -> {
                background.setStroke(Color.web("#3498DB", 0.9));
            });
        sp.setOnMouseExited(e -> {
                background.setStroke(Color.web("#F7F7F7", 0.9));
            });
        String keyword = (text.equals("Encrypt")) ? "encrypt" : "decrypt";
        sp.setOnMouseClicked(e -> {
                background.setFill(Color.web("#212F3C"));
                Encryptor.setKeyword(keyword);
            });

        Label button = new Label(text);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setTextFill(Color.web("#F7F7F7"));

        VBox vBox = new VBox(12, imageView, button);
        vBox.setAlignment(Pos.CENTER);
        sp.getChildren().addAll(background, vBox);
        sp.setAlignment(Pos.CENTER);

        return sp;
    }

}
