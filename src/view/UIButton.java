package view;

import resources.Resources;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Pos;

public class UIButton extends StackPane {

    private Rectangle background;
    private Label buttonText;
    private ImageView buttonIcon;
    private HBox buttonHolder;

    public UIButton(double width, double height, String text) {
        this(null, width, height, text);
    }

    public UIButton(String iconURL, double width, double height, String text) {

        this.setMaxWidth(width);
        this.setMaxHeight(height);

        background = new Rectangle(width, height, Color.web("#F7F7F7", 0.9));
        background.setArcWidth((double) height / 4.0);
        background.setArcHeight((double) height / 4.0);
        background.setStroke(Color.web("#3498DB"));

        buttonText = new Label(text);
        buttonText.setFont(new Font(height / 2.5));
        buttonText.setTextAlignment(TextAlignment.CENTER);

        //This buttonHolder is the HBox to hold the icon (if present) and Label
        buttonHolder = new HBox(5);
        buttonHolder.setAlignment(Pos.CENTER);

        // Sets simple highlighting when mouse goes over button
        buttonHolder.setOnMouseEntered(e -> {
                setBackgroundColor(Color.web("#B3B3B3", 0.9));
            });
        buttonHolder.setOnMouseExited(e -> {
                setBackgroundColor(Color.web("#F7F7F7", 0.9));
            });

        //Constructs the buttonHolder HBox according to having an icon or not
        if (iconURL != null) {
            ImageView iconIV = new ImageView(Resources.getImage(iconURL));
            iconIV.setFitHeight(height);
            iconIV.setPreserveRatio(true);
            iconIV.setSmooth(true);
            buttonHolder.getChildren().addAll(iconIV, buttonText);
        } else {
            buttonHolder.getChildren().add(buttonText);
        }
        this.getChildren().addAll(background, buttonHolder);
    }

    public void setBackgroundColor(Paint color) {
        background.setFill(color);
        //this.getChildren().clear();
        //this.getChildren().addAll(background, buttonHolder);
    }

    public void setTextColor(Paint color) {
        buttonText.setTextFill(color);
    }

    public Label getLabel() {
        return this.buttonText;
    }

    public HBox getClickable() {
        return this.buttonHolder;
    }

}
