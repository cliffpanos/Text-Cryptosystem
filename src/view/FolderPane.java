package view;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
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

        this.setPadding(new Insets(22));
        this.getChildren().addAll(chooseFolderButton);

        setProcessType("encrypt");


        resize();
    }

    public void processFolder() {
        boolean fileIsNotActionable = false;
        boolean encrypting = processType.equals("encrypt");

        if (folder.isDirectory()) {
            File tempFiles[] = folder.listFiles();

            for (File tempFile : tempFiles) {
                if (tempFile != null) {
                    files.add(new UIFile(tempFile));
                }
            }

            for (UIFile file : files) {
                if ((file.hasEncryptedTags() && !encrypting)
                    || (!file.hasEncryptedTags() && encrypting)) {
                    file.processFile();
                } else {
                    fileIsNotActionable = true;
                }
            }
        }

        if (fileIsNotActionable) {
            if (encrypting) {
                UIAlert.show("Text Already Encrypted",
                    "One or more of the files has already\n"
                    + "been encrypted. To prevent the convolution\n"
                    + "inherent in multiple encryptions,\n"
                    + "these file(s) have not been encrypted again.",
                    javafx.scene.control.Alert.AlertType.ERROR);
            } else {
                UIAlert.show("Text Not Encrypted",
                    "The text that you are attempting to decrypt\n"
                    + "has NOT been encrypted by this system.\n"
                    + "To prevent a loss of data through false\n"
                    + "decryption, you may not decrypt this text.",
                    javafx.scene.control.Alert.AlertType.ERROR);
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
