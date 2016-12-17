package view;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


public abstract class InputMenu {

    protected VBox menu;
    protected TextField inputField;
    protected TextField outputField;

    public InputMenu() {

        menu = new VBox(10);
        menu.setMaxWidth((double) MainScreen.getStageWidth() * 0.5);
        menu.setPrefHeight(MainScreen.getStageHeight());
        menu.setPadding(new Insets(12));
        menu.setAlignment(Pos.CENTER);

        inputField = new TextField();
        inputField.setPrefWidth(menu.getWidth() - 20);
        outputField = new TextField();
        outputField.setPrefWidth(menu.getWidth() - 20);


        menu.getChildren().addAll(inputField, outputField);

    }

    public VBox getRootNode() {
        return this.menu;
    }

}
