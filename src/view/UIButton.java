package view;

import resources.Resources;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
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
    private boolean isSelected = false;

    public UIButton(double width, double height, String text) {
        this(null, width, height, text);
    }

    public UIButton(String iconURL, double width, double height) {
        this(iconURL, width, height, null);
        //This will create a CIRCULAR icon button rather than a rectangular one
    }

    public UIButton(String iconURL, double width, double height, String text) {

        this.setPrefWidth(width);
        this.setPrefHeight(height);

        background = new Rectangle(width, height, Color.WHITE);
        background.setArcWidth((double) height / 4.0);
        background.setArcHeight((double) height / 4.0);
        background.setStroke(Color.web("#3498DB"));

        buttonText = new Label(text);
        buttonText.setFont(new Font(height / 2.25));
        buttonText.setTextAlignment(TextAlignment.CENTER);

        //This buttonHolder is the HBox to hold the icon (if present) and Label
        buttonHolder = new HBox(8);
        buttonHolder.setAlignment(Pos.CENTER);

        // Sets simple highlighting when mouse goes over button
        buttonHolder.setOnMouseEntered(e -> {
                background.setFill(Color.web("#D7DBDD", 0.9));
            });
        buttonHolder.setOnMouseExited(e -> {
                background.setFill(Color.WHITE);
            });

        //Constructs the buttonHolder HBox according to having an icon or not
        if (iconURL != null) {
            ImageView iconIV = Resources.getImageView(iconURL, (height - 14));
            buttonHolder.getChildren().addAll(iconIV, buttonText);
        } else {
            buttonHolder.getChildren().add(buttonText);
        }
        this.getChildren().addAll(background, buttonHolder);
    }

    public void setBackgroundColor(Paint color) {
        background.setFill(color);
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

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

}
