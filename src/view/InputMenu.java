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
    protected double heightDecrement = 60.0;

    private HBox upperMenuBar = new HBox(8.0);
    private HBox lowerMenuBar = new HBox(8.0);
    private double paneWidth = MainScreen.getStageWidth() * 0.57;

    public InputMenu() {

        menu = new VBox(8.5);
        menu.setPrefWidth(paneWidth - 40.0);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);
        menu.setFillWidth(false);

        inputField.setPrefWidth(paneWidth - 20.0);
        outputField.setPrefWidth(paneWidth - 20.0);
        inputField.setWrapText(true);
        outputField.setWrapText(true);

        double iconSize = 32.5;
        UIButton trashButton = new UIButton("trash_icon.png", iconSize);
        trashButton.setOnMouseClicked(e -> {
                inputField.clear();
            });
        UIButton selectAllButton = new UIButton("selectAll_icon.png", iconSize);
        selectAllButton.setOnMouseClicked(e -> {
                inputField.selectAll();
            });
        UIButton copyButton = new UIButton("copy_icon.png", iconSize);
        copyButton.setOnMouseClicked(e -> {
                inputField.copy();
            });
        UIButton cutButton = new UIButton("cut_icon.png", iconSize);
        cutButton.setOnMouseClicked(e -> {
                inputField.cut();
            });
        UIButton pasteButton = new UIButton("paste_icon.png", iconSize);
        pasteButton.setOnMouseClicked(e -> {
                inputField.paste();
            });
        UIButton undoButton = new UIButton("undo_icon.png", iconSize);
        undoButton.setOnMouseClicked(e -> {
                inputField.undo();
            });
        UIButton redoButton = new UIButton("redo_icon.png", iconSize);
        redoButton.setOnMouseClicked(e -> {
                inputField.redo();
            });

        upperMenuBar.setPrefWidth(paneWidth - 40.0);
        upperMenuBar.setPrefHeight(iconSize);
        upperMenuBar.setAlignment(Pos.CENTER_LEFT);
        upperMenuBar.getChildren().addAll(trashButton, selectAllButton,
            copyButton, cutButton, pasteButton, undoButton, redoButton);


        UIButton selectAllButton2 = new UIButton("selectAll_icon2.png",
            iconSize);
        selectAllButton2.setOnMouseClicked(e -> {
                outputField.selectAll();
        });
        UIButton copyButton2 = new UIButton("copy_icon2.png", iconSize);
        copyButton2.setOnMouseClicked(e -> {
                outputField.copy();
        });
        lowerMenuBar.setPrefWidth(paneWidth - 40.0 - 100.0);
        lowerMenuBar.setPrefHeight(iconSize);
        lowerMenuBar.setAlignment(Pos.CENTER_LEFT);
        lowerMenuBar.getChildren().addAll(selectAllButton2, copyButton2);

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
        HBox runButtonHBox = new HBox(runButton);
        runButtonHBox.setAlignment(Pos.CENTER_RIGHT);
        runButtonHBox.setPrefWidth(paneWidth - 45.0 - 80.0);

        HBox lowerHBox = new HBox(lowerMenuBar, runButtonHBox);

        menu.getChildren().addAll(upperMenuBar, inputField, lowerHBox,
            outputField);

    }

    public VBox getRootNode() {
        return this.menu;
    }

    public String getInputFieldText() {
        return inputField.getText();
    }

}
