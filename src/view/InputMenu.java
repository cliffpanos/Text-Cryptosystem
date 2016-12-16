package view;

import runner.Runner;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public abstract class InputMenu {

    private VBox menu;
    protected TextField inputField;
    protected TextField outputField;

    public InputMenu() {

        menu = new VBox(10);
        menu.setPrefWidth((double) Runner.getStageWidth() * 0.5);
        menu.setPrefHeight(Runner.getStageHeight());

        inputField = new TextField();
        outputField = new TextField();



    }

    public VBox getRootNode() {
        return this.menu;
    }

}
