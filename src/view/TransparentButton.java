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

public class TransparentButton extends StackPane {

    private ImageView imageView;
    private Rectangle background;
    private Label button;

    private static TransparentButton encryptPane = null;
    private static TransparentButton decryptPane = null;
    private static TransparentButton paneSelected = null;


    public TransparentButton(String imageURL, String text, double dimension) {

        //Setup the StackPane variables to be the two instances of this class
        encryptPane = (text.equals("Encrypt")) ? this : encryptPane;
        decryptPane = (text.equals("Decrypt")) ? this : decryptPane;

        double imageDecrement = (text.equals("Encrypt")) ? 7.5 : 0;
        imageView = Resources.getImageView(imageURL,
            (dimension * 0.25 - imageDecrement));

        //Set background stroke
        double dim = dimension - 85;
        background = new Rectangle(dim, dim, Color.TRANSPARENT);
        background.setArcWidth((double) dim / 4.0);
        background.setArcHeight((double) dim / 4.0);
        background.setStroke(Color.web("#F7F7F7"));
        this.setOnMouseEntered(e -> {
                if (paneSelected == null) {
                    background.setStroke(Color.web("#3498DB")); //blue
                }
                if (paneSelected != null && this != paneSelected) {
                    background.setStroke(Color.web("#5D6D7E", 0.9));
                } else {
                    background.setStroke(Color.web("#3498DB")); //blue
                }
            });
        this.setOnMouseExited(e -> {
                if (paneSelected == null) {
                    background.setStroke(Color.web("#F7F7F7", 0.9));
                }
                if (paneSelected != null && this != paneSelected) {
                    background.setStroke(Color.web("#F7F7F7", 0.9));
                }
            });
        boolean isEncrypt = text.equals("Encrypt");
        String keyword = (isEncrypt) ? "encrypt" : "decrypt";
        this.setOnMouseClicked(e -> {
                paneSelected = this;
                Encryptor.setKeyword(keyword);
                FilePane.setProcessType(text);
                MainScreen.setIsEncryptingNotDecrypting(isEncrypt);
                updateSelected();
            });
        this.setMaxWidth(dim + 2);
        this.setMaxHeight(dim + 2);

        button = new Label(text);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setTextFill(Color.web("#F7F7F7"));

        VBox vBox = new VBox(12, imageView, button);
        vBox.setAlignment(Pos.CENTER);
        this.getChildren().addAll(background, vBox);
        this.setAlignment(Pos.CENTER);

        //The program initializes with encryptPane selected by default:
        if (this == encryptPane) {
            paneSelected = encryptPane;
            Encryptor.setKeyword(keyword);
            FilePane.setProcessType(text);
            MainScreen.setIsEncryptingNotDecrypting(isEncrypt);
            background.setFill(Color.web("#212F3C"));
            background.setStroke(Color.web("#3498DB"));
        }


    }

    public void updateSelected() {

        TransparentButton other = (this == encryptPane) ? decryptPane
            : encryptPane;
        //Whichever instance calls the update method is the Pane that was
        //clicked on, so the other Pane is the one no longer selected

        this.background.setFill(Color.web("#212F3C")); //New Fill
        other.getTheBackground().setFill(Color.TRANSPARENT);

        this.background.setStroke(Color.web("#3498DB"));
        other.getTheBackground().setStroke(Color.web("#F7F7F7"));
    }

    public Rectangle getTheBackground() { //must be named this; can't override
        return this.background;
    }

}
