package view;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

public class FolderPane extends StackPane implements Resizable {

    private static File folder = null;
    private static ArrayList<UIFile> files = new ArrayList<>();
    private static String processType = "encrypt"; // "encrypt" or "decrypt"

    private static UIButton chooseFolderButton;
    private static double paneWidth = MainScreen.getStageWidth() * 0.57 - 40.0;

    public FolderPane() {

        chooseFolderButton = new UIButton(paneWidth - 60.0, 30,
            "Choose a Folder to " + processType);
        chooseFolderButton.setAlignment(Pos.TOP_CENTER);
        chooseFolderButton.setOnMouseClicked(e -> {

                // Gets a folder File and processes it
                folder = UIFile.getFolderFromDirectory();
                this.processFolder();

            });

        setProcessType("encrypt");


        resize();
    }

    public void processFolder() {
        if (folder.isDirectory()) {
            File tempFiles[] = folder.listFiles();

            for (File tempFile : tempFiles) {
                if (tempFile != null) {
                    files.add(new UIFile(tempFile));
                }
            }

            for (UIFile file : files) {
                file.processFile();
            }
        }
    }

    public void resize() {
        //TODO
    }


// Anthony (Self-note): Fix these!!!
    public static void setProcessType(String encryptOrDecrypt) {
        processType = encryptOrDecrypt;
        chooseFolderButton.getLabel().setText(
            "Choose a Folder to " + processType); //change to encrypt or decrypt
        //runButton.updateIcon(encryptOrDecrypt + ".png");
        //updateButtonTexts();
    }
/*
    public static void updateButtonTexts() {

        String fileName = "";
        Label buttonLabel = runButton.getLabel();

        // Sets the initial text
        buttonLabel.setText(processType.substring(0, 1).toUpperCase()
            + processType.substring(1));

        if (singleFileDisplay != null) {
            singleFileDisplay.update();
        }
        for (MultiFileDisplay multiFileDisplay : multiFileDisplays) {
            multiFileDisplay.update();
        }

        // Adds additional text for additional files
        if (!files.isEmpty()) {
            fileName = " \"" + files.get(0).getName() + "\"";
            buttonLabel.setText(buttonLabel.getText() + fileName);
        }
        if (files.size() > 1) {
            String s = (files.size() > 2) ? "s" : "";
            buttonLabel.setText(buttonLabel.getText()
                + " + " + (files.size() - 1) + " other file" + s);
        }

    }
    */

}
