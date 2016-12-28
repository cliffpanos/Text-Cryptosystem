package view;

import resources.Resources;
import controller.Encryptor;

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


public class EncryptDecryptMenu extends StackPane implements Resizable {

    private VBox menu = new VBox(); //holds the two buttons & passwordField
    private static double paneWidth = MainScreen.getStageWidth() * 0.18;
    private static double height = MainScreen.getStageHeight();
    private static TextField passwordField = new TextField();

    private static String passwordFieldText = "";
    private static boolean passwordFieldEnteredYet = false;
    private static int lastCaretPosition = 0;

    public EncryptDecryptMenu() {

        this.setBackground(null);

        VBox background = new VBox();
        background.setBackground(new Background(new BackgroundFill(Color
            .web("#212F3C", 0.968), new CornerRadii(5.0, 0.0, 0.0, 5.0, false),
            new Insets(0))));
        //backgroundBlur.setEffect(new BoxBlur(width, (height - 20), 3));

        menu.setAlignment(Pos.CENTER);
        menu.setBackground(null);

        // Password Field TextField Actions
        passwordField.setOnAction(e -> {
                Encryptor.setPassword(passwordField.getText());
                passwordFieldText = passwordField.getText();
            });
        passwordField.setOnMouseEntered(e -> {
                if (!passwordFieldEnteredYet) {
                    passwordFieldText = passwordField.getText();
                    passwordFieldEnteredYet = true;
                    lastCaretPosition = passwordField.getCaretPosition();
                }
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
                for (int i = 0; i < passwordFieldText.length(); i++) {
                    passwordCoverUp += "*";
                }
                passwordField.setText(passwordCoverUp);
                passwordField.setEditable(false);
            });
        passwordField.setPromptText("enter here");
        passwordField.setAlignment(Pos.CENTER);

        //Create the password trio: the icon, the TextField, and the Label below
        ImageView passwordIcon = Resources.getImageView("password.tiff",
            (paneWidth * 0.33));
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
        WindowButtons windowButtons = new WindowButtons(paneWidth);
        outerVBox.setAlignment(Pos.TOP_CENTER);
        outerVBox.getChildren().addAll(windowButtons.getRootNode(), menu);
        this.getChildren().addAll(background, outerVBox);

        resize();

    }

    public static String getPasswordFieldText() {
        if (!passwordFieldEnteredYet) {
            return passwordField.getText();
        }
        return passwordFieldText;
    }

    public void resize() {

        paneWidth = MainScreen.getStageWidth() * 0.18;
        height = MainScreen.getStageHeight();

        //background.setPrefWidth(paneWidth);
        //background.setPrefHeight(height);

        passwordField.setMaxWidth(paneWidth * 0.6);

        menu.setSpacing(height / 15);
        menu.setPrefWidth(paneWidth);
        menu.setPrefHeight(height);

    }

}
