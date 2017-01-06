package view;

import controller.Encryptor;
import resources.Resources;

import java.io.File;
import java.io.IOException;

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
    protected String processType;

    protected double stageHeight = MainScreen.getStageHeight();
    private double paneWidth = MainScreen.getStageWidth() * 0.57;

    private HBox upperMenuBar = new HBox(8.0);
    private HBox lowerMenuBar = new HBox(8.0);
    private HBox runButtonHBox;
    private HBox lowerHBox;

    private String output = "";


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
                output = Encryptor.run();
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

        // Gets the fileName from the downloadField and makes sure it isn't empty
        String fileName = downloadField.getText();
        if (fileName == null || fileName.equals("")) {
            UIAlert.show("File Name Invalid",
                    "Please enter a name for the file to download.",
                    javafx.scene.control.Alert.AlertType.ERROR);
            UIAlert.setAlertable(true);
            return;
        }

        // Gets a chosen foldder for file
        File folder = UIFile.getFolderFromDirectory();
        if (folder == null || !folder.isDirectory()) {
            return;
        }

        output = outputField.getText();

        // Attempts to create file in chosen folder and writes to it if successful
        // If there is an IOException, prompt user for different name
        try {

            if (output != null && !output.equals("")) {

                // Checks to make sure that no other files contain the same name
                File downloadFile = new File(folder, (fileName + ".txt"));
                if (!downloadFile.createNewFile()) {
                    UIAlert.show("File Name Invalid",
                        "Another file in the selected folder has\n"
                        + "the same name as the one you entered.\n"
                        + "Please try a different name.",
                        javafx.scene.control.Alert.AlertType.ERROR);
                    UIAlert.setAlertable(true);
                    return;
                }

                UIFile.writeTXTFile(downloadFile, output);

            } else {
                UIAlert.show("No Text to Write to File",
                    "This download button will write the\n"
                    + processType + "ed text to a txt file, but\n"
                    + "no text has been " + processType + "ed yet.",
                    javafx.scene.control.Alert.AlertType.ERROR);
                UIAlert.setAlertable(true);
            }

        } catch (IOException e) {
            UIAlert.show("File Creation Failed",
                "The file failed to be created. Check to see if"
                + "the file name you entered contains characters\n"
                + "that are not permitted by your operating\n"
                + "system.",
                javafx.scene.control.Alert.AlertType.ERROR);
            e.printStackTrace();
            UIAlert.setAlertable(true);
            return;
        }

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
