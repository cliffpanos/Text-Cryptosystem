package view;

import controller.Encryptor;
import resources.Resources;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


public abstract class InputMenu implements Resizable {

    protected VBox menu;
    protected TextArea inputField = new TextArea();
    protected TextArea outputField = new TextArea();
    protected TextField downloadField = new TextField();
    protected UIButton runButton;
    protected UIButton downloadDirectory;
    protected double heightDecrement = 60.0; //Used in subclass TextArea sizing

    protected double stageHeight = MainScreen.getStageHeight();
    private double paneWidth = MainScreen.getStageWidth() * 0.57;

    private HBox upperMenuBar = new HBox(8.0);
    private HBox lowerMenuBar = new HBox(8.0);
    private HBox runButtonHBox;
    private HBox lowerHBox;


    public InputMenu() {

        menu = new VBox(8.5);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);
        menu.setFillWidth(false);

        inputField.setWrapText(true);
        outputField.setWrapText(true);
        inputField.setFont(Font.font("Helvetica"));
        outputField.setFont(Font.font("Helvetica"));

        double iconSize = 35;
        UIButton trashButton = new UIButton("trash_icon.png", iconSize);
        trashButton.setOnMouseClicked(e -> {
                inputField.clear();
            });
        UIButton selectAllButton = new UIButton("selectAll_icon.png", iconSize);
        selectAllButton.setOnMouseClicked(e -> {
                inputField.selectAll();
            });
        UIButton pasteButton = new UIButton("paste_icon.png", iconSize);
        pasteButton.setOnMouseClicked(e -> {
                inputField.paste();
            });
        UIButton copyButton = new UIButton("copy_icon.png", iconSize);
        copyButton.setOnMouseClicked(e -> {
                inputField.copy();
            });
        UIButton cutButton = new UIButton("cut_icon.png", iconSize);
        cutButton.setOnMouseClicked(e -> {
                inputField.cut();
            });
        UIButton undoButton = new UIButton("undo_icon.png", iconSize);
        undoButton.setOnMouseClicked(e -> {
                inputField.undo();
            });
        UIButton redoButton = new UIButton("redo_icon.png", iconSize);
        redoButton.setOnMouseClicked(e -> {
                inputField.redo();
            });
        upperMenuBar.setPrefHeight(iconSize);
        upperMenuBar.setAlignment(Pos.CENTER_LEFT);
        upperMenuBar.getChildren().addAll(trashButton, selectAllButton,
            pasteButton, copyButton, cutButton, undoButton, redoButton);


        UIButton selectAllButtonBlue = new UIButton("selectAll_icon2.png",
            iconSize);
        selectAllButtonBlue.setOnMouseClicked(e -> {
                outputField.selectAll();
            });
        UIButton copyButtonBlue = new UIButton("copy_icon2.png", iconSize);
        copyButtonBlue.setOnMouseClicked(e -> {
                outputField.copy();
            });
        UIButton downloadDirectory = new UIButton("forward_icon.png", iconSize);
        downloadDirectory.setOnMouseClicked(e -> {
                downloadText();
            });
        UIButton downloadButton = new UIButton("download_icon.png", iconSize);
        downloadButton.setOnMouseClicked(e -> {
                downloadField.setVisible(!downloadField.isVisible());
                downloadDirectory.setVisible(!downloadDirectory.isVisible());
            });
        downloadDirectory.setVisible(false);
        downloadField.setVisible(false);
        downloadField.setPromptText("Name the File to Download");

        lowerMenuBar.setPrefHeight(iconSize);
        lowerMenuBar.setAlignment(Pos.CENTER_LEFT);
        lowerMenuBar.getChildren().addAll(selectAllButtonBlue, copyButtonBlue,
            downloadButton, downloadField, downloadDirectory);

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
                UIAlert.setAlertable(true);
            });

        runButtonHBox = new HBox(runButton);
        runButtonHBox.setAlignment(Pos.CENTER_RIGHT);

        lowerHBox = new HBox(lowerMenuBar, runButtonHBox);

        menu.getChildren().addAll(upperMenuBar, inputField, lowerHBox,
            outputField);
    }

    private void downloadText() {

        //Check to see if user has implemented file name
        //if not, show a UIAlert

        //Have the user choose a directory
        //if no directory chosen, do nothing

        //if directory chosen, try to create a file in that directory with the
        //file name from downloadField, and catch IOException if the file
        //already exists; prompt the user using UIAlert to enter a diff. name

        //if all is successful, use UIFile's
        //writeTXTFile(File file, String textToWrite) with outputField's text

        UIAlert.setAlertable(true); //This must be at the end of this function
    }

    public VBox getRootNode() {
        return this.menu;
    }

    public String getInputFieldText() {
        return inputField.getText();
    }

    public void resize() {

        stageHeight = MainScreen.getStageHeight();
        paneWidth = MainScreen.getStageWidth() * 0.57;

        menu.setPrefWidth(paneWidth - 40.0);

        inputField.setPrefWidth(paneWidth - 20.0);
        outputField.setPrefWidth(paneWidth - 20.0);

        upperMenuBar.setPrefWidth(paneWidth - 30.0);
        lowerMenuBar.setPrefWidth(paneWidth - 40.0 - 100.0);
        lowerHBox.setPrefWidth(paneWidth - 30.0);

        runButtonHBox.setPrefWidth(paneWidth - 45.0 - 80.0);

        downloadField.setPrefWidth(paneWidth * 0.3);

    }

}
