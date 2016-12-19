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

    public TransparentButton(String imageURL, String text, double dimension) {

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
                background.setStroke(Color.web("#3498DB", 0.9));
            });
        this.setOnMouseExited(e -> {
                background.setStroke(Color.web("#F7F7F7", 0.9));
            });
        String keyword = (text.equals("Encrypt")) ? "encrypt" : "decrypt";
        this.setOnMouseClicked(e -> {
                background.setFill(Color.web("#212F3C"));
                Encryptor.setKeyword(keyword);
            });

        button = new Label(text);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setTextFill(Color.web("#F7F7F7"));

        VBox vBox = new VBox(12, imageView, button);
        vBox.setAlignment(Pos.CENTER);
        this.getChildren().addAll(background, vBox);
        this.setAlignment(Pos.CENTER);

    }

}
