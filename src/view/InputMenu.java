package view;

import controller.Encryptor;
import resources.Resources;

import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


public abstract class InputMenu {

    protected VBox menu;
    protected TextArea inputField = new TextArea();
    protected TextArea outputField = new TextArea();
    protected UIButton runButton;

    private HBox upperMenuBar = new HBox(7.5);

    public InputMenu() {

        double paneWidth = MainScreen.getStageWidth() * 0.57;

        menu = new VBox(10);
        menu.setPrefWidth(paneWidth - 40.0);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);
        menu.setFillWidth(false);

        inputField.setPrefWidth(paneWidth - 20.0);
        outputField.setPrefWidth(paneWidth - 20.0);
        inputField.setWrapText(true);
        outputField.setWrapText(true);

        upperMenuBar.setPrefWidth(paneWidth - 40.0);
        upperMenuBar.setPrefHeight(30);

    }

    public void completeSetUp() {

        runButton.setAlignment(Pos.CENTER_RIGHT);
        runButton.getClickable().setOnMouseClicked(e -> {
                Encryptor.setText(inputField.getText());
                String output = Encryptor.run();
                if (output != null) {
                    outputField.setText(output);
                    Resources.playSound("encryptionComplete.aiff");
                }
            });

        menu.getChildren().addAll(upperMenuBar, inputField, runButton,
            outputField);

    }

    public VBox getRootNode() {
        return this.menu;
    }

    public String getInputFieldText() {
        return inputField.getText();
    }

}
