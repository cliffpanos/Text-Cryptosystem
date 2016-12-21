package view;

import resources.Resources;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import javafx.scene.Node;
import javafx.scene.shape.Shape;
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

    public UIButton(String iconURL, double dimension) {
        //This will create a CIRCULAR icon button rather than a rectangular one
        Circle circle = new Circle((dimension / 2.0), Color.WHITE);
        this.setOnMousePressed(e -> {
                circle.setFill(Color.web("#D7DBDD"));
            });
        this.setOnMouseReleased(e -> {
                circle.setFill(Color.WHITE);
            });
        ImageView icon = Resources.getImageView(iconURL, (dimension - 12.5));
        this.getChildren().addAll(circle, icon);
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

        //On pressed, released, entered, clicked
        setMouseActions(buttonHolder, background);

        //Constructs the buttonHolder HBox according to having an icon or not
        if (iconURL != null) {
            ImageView iconIV = Resources.getImageView(iconURL, (height - 14));
            buttonHolder.getChildren().addAll(iconIV, buttonText);
        } else {
            buttonHolder.getChildren().add(buttonText);
        }
        this.getChildren().addAll(background, buttonHolder);
    }

    //Setup mouse actions for a given UIButton's color Node
    public void setMouseActions(Node actionNode, Shape colorNode) {

        actionNode.setOnMouseEntered(e -> {
                colorNode.setFill(Color.web("#EAEDED", 0.9));
            });
        actionNode.setOnMouseExited(e -> {
                colorNode.setFill(Color.WHITE);
            });
        actionNode.setOnMousePressed(e -> {
                colorNode.setFill(Color.web("#D6DBDF"));
            });
        actionNode.setOnMouseClicked(e -> {
                colorNode.setFill(Color.web("#D6EAF8"));
            });
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
