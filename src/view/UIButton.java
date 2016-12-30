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

    private Shape background;
    private Label buttonText;
    private ImageView buttonIcon;
    private HBox buttonHolder;
    private boolean isSelected = false;
    private double height;


    //Circular Buttons below : --------------------------------------------- :

    public UIButton(String iconURL, double dimension) {
        this(iconURL, dimension, 0);
    }

    public UIButton(String iconURL, double dimension, double imageDecrement) {
        background = new Circle((dimension / 2.0), Color.WHITE);
        this.setOnMousePressed(e -> {
                background.setFill(Color.web("#D7DBDD"));
            });
        this.setOnMouseReleased(e -> {
                background.setFill(Color.WHITE);
            });
        ImageView icon = Resources.getImageView(iconURL,
            (dimension - 12.5 - imageDecrement));
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(background, icon);
    }

    //Rectangular Buttons below : ------------------------------------------ :

    public UIButton(double width, double height, String text) {
        this(null, width, height, text);
    }

    //This constructor is the most widely used of them all
    public UIButton(String iconURL, double width, double ht, String text) {
        this(iconURL, width, ht, text, true);
    }

    public UIButton(String iconURL, double width, double ht, String text,
        boolean doSetMouseActions) {

        this.height = ht;
        this.setPrefWidth(width);
        this.setPrefHeight(height);

        background = new Rectangle(width, height, Color.WHITE);
        ((Rectangle) background).setArcWidth((double) height / 4.5);
        ((Rectangle) background).setArcHeight((double) height / 4.5);
        background.setStroke(Color.web("#3498DB"));

        buttonText = new Label(text);
        buttonText.setFont(new Font(height / 2.25));
        buttonText.setTextAlignment(TextAlignment.CENTER);

        //This buttonHolder is the HBox to hold the icon (if present) and Label
        buttonHolder = new HBox(7);
        buttonHolder.setAlignment(Pos.CENTER);

        //On pressed, released, entered, clicked
        if (doSetMouseActions) {
            setMouseActions(buttonHolder);
        }

        //Constructs the buttonHolder HBox according to having an icon or not
        if (iconURL != null) {
            ImageView iconIV = Resources.getImageView(iconURL, (height - 15));
            buttonHolder.getChildren().addAll(iconIV, buttonText);
        } else {
            buttonHolder.getChildren().add(buttonText);
        }
        this.getChildren().addAll(background, buttonHolder);
    }

    //Setup mouse actions for a given UIButton's color Node
    public void setMouseActions(Node actionNode) {

        actionNode.setOnMouseEntered(e -> {
                background.setFill(Color.web("#EAEDED", 0.9));
            });
        actionNode.setOnMouseExited(e -> {
                background.setFill(Color.WHITE);
            });
        actionNode.setOnMousePressed(e -> {
                background.setFill(Color.web("#D6DBDF"));
            });
        actionNode.setOnMouseClicked(e -> {
                background.setFill(Color.web("#D6EAF8"));
            });
        actionNode.setOnMouseReleased(e -> {
                background.setFill(Color.WHITE);
            });
    }

    public void eraseMouseActions(Node actionNode) {

        actionNode.setOnMouseEntered(e -> {});
        actionNode.setOnMouseExited(e -> {});
        actionNode.setOnMousePressed(e -> {});
        actionNode.setOnMouseClicked(e -> {});

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

    public void updateIcon(String newIconURL) { //Used in FilePane & FolderPane
        ImageView iconIV = Resources.getImageView(newIconURL, (height - 15));
        buttonHolder.getChildren().clear();
        buttonHolder.getChildren().addAll(iconIV, buttonText);
    }

}
