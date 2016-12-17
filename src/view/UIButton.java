package view;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class UIButton extends StackPane {

    private Rectangle background;
    private Label buttonText;
    private Image buttonIcon;

    public UIButton(double width, double height, String text) {

        this.setMaxWidth(width);
        this.setMaxHeight(height);

        background = new Rectangle(width, height, Color.web("#F7F7F7", 0.9));
        background.setArcWidth((double) height / 4.0);
        background.setArcHeight((double) height / 4.0);

        buttonText = new Label(text);

        this.getChildren().addAll(background, buttonText);
    }

}
