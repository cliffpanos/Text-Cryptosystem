package view;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

public class FolderPane extends StackPane implements Resizable {

    private static ArrayList<UIFile> files = new ArrayList<>();
    private static String processType = "encrypt"; // "encrypt" or "decrypt"
    private static File selectedFolder = null;
    private static boolean unactionableFiles;

    private static UIButton chooseFolderButton;
    private static double paneWidth = MainScreen.getStageWidth() * 0.57 - 40.0;

    public FolderPane() {

        chooseFolderButton = new UIButton(paneWidth - 60.0, 30,
            "Choose Folders to " + processType);
        chooseFolderButton.setAlignment(Pos.TOP_CENTER);
        chooseFolderButton.setOnMouseClicked(e -> {

                // Gets a folder File and processes it
                selectedFolder = UIFile.getFolderFromDirectory();
                processFolder(selectedFolder);

                // Will create an alert if any files were trying to be encrypted
                // twice or decrypted prior to being encrypted.
                checkForUnactionableFiles();

            });

        this.setPadding(new Insets(20));
        this.getChildren().addAll(chooseFolderButton);

        setProcessType("encrypt");


        resize();
    }

    // Creates an alert if a file is trying to be decrypted without
    // first being encrypted or encrypted twice.
    public void checkForUnactionableFiles() {
        if (unactionableFiles) {
            if (processType.equals("encrypt")) {
                UIAlert.show("Text Already Encrypted",
                    "One or more of the files has already\n"
                    + "been encrypted. To prevent the convolution\n"
                    + "inherent in multiple encryptions,\n"
                    + "these file(s) have not been encrypted again.",
                    javafx.scene.control.Alert.AlertType.ERROR);
            } else {
                UIAlert.show("Text Not Encrypted",
                    "One or more files that you are attempting to \n"
                    + "decrypt has NOT been encrypted by this system.\n"
                    + "To prevent a loss of data through false\n"
                    + "decryption, you may not decrypt this text.",
                    javafx.scene.control.Alert.AlertType.ERROR);
            }
        }
    }

    public void processFolder(File folder) {
        unactionableFiles = false;

        // This is the main processing segment
        if (folder.isDirectory()) {

            // Clears files before adding new ones
            files.clear();

            File[] tempFiles = folder.listFiles();

            // Checks if any nested Files in folder are directories and
            // recursively calls this function if so.
            for (File tempFile : tempFiles) {

                // Will recursively call this function if there are nested directories.
                if (tempFile.isDirectory()) {

                    // TODO: Ask user before doing the next two commands.
                    processFolder(tempFile);
                    files.clear();
                }
            }

            // Creates an ArrayList of Files called "files" that will be processed
            for (File tempFile : tempFiles) {
                if (tempFile != null) {

                    UIFile tempUIFile = new UIFile(tempFile);

                    if (tempUIFile.hasProcessableExtension()) {
                        files.add(new UIFile(tempFile));
                    }
                }
            }

            // Processes files if possible
            boolean encrypting = processType.equals("encrypt");
            for (UIFile file : files) {
                if ((file.hasEncryptedTags() && !encrypting)
                    || (!file.hasEncryptedTags() && encrypting)) {
                    file.processFile();
                } else {
                    unactionableFiles = true;
                    System.out.println("File " + file.getName() + " is not actionable");
                }
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
            "Choose Folders to " + processType); //change to encrypt or decrypt
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
