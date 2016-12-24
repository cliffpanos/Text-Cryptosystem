package view;

import controller.Encryptor;
import resources.Resources;
import runner.Runner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class FilePane extends StackPane {

    private VBox vBox = new VBox(20);
    private static double paneWidth = MainScreen.getStageWidth() * 0.57 - 40.0;

    private static UIButton runButton =
        new UIButton("encrypt.png", paneWidth - 60.0, 30, "Encrypt");
    private static String processType = "encrypt"; // "encrypt" or "decrypt"
    private static UIButton chooseFileButton;
    private File fileToProcess;

    public FilePane() {

        this.setPrefWidth(paneWidth);
        this.setPrefHeight(MainScreen.getStageHeight() - 40.0);
        //this.setBackground(new Background(new BackgroundFill(Color
        //    .WHITE, new CornerRadii(5.0, 5.0, 5.0, 5.0, false),
        //    new Insets(10, 10, 10, 10))));
        //this.setPadding(new Insets(20));

        chooseFileButton = new UIButton(paneWidth - 60.0, 30,
            "Choose a file to " + processType);
        chooseFileButton.setAlignment(Pos.TOP_CENTER);
        chooseFileButton.setOnMouseClicked(e -> {
                File tempFile = getFileFromDirectory();
                if (tempFile != null) {
                    fileToProcess = tempFile;
                    vBox.getChildren().addAll(Resources.getImageView(
                        "txtFile.png", (paneWidth - 250.0)),
                        runButton);
                    Resources.playSound("fileUpload.aiff");
                }
            });

        runButton.setOnMouseClicked(e -> {
                processFile();
            });

        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setMaxHeight(MainScreen.getStageHeight() - 40.0);
        vBox.getChildren().add(chooseFileButton);

        this.getChildren().add(vBox);

    }

    public static void setProcessType(String encryptOrDecrypt) {
        processType = encryptOrDecrypt;
        chooseFileButton.getLabel().setText(
            "Choose a file to " + processType); //change to encrypt or decrypt
        runButton.getLabel().setText(processType.substring(0, 1).toUpperCase()
            + processType.substring(1));
        runButton.updateIcon(encryptOrDecrypt + ".png");
    }


    //Code to get and process Files ----------------- :

    public void processFile() {

        String extension = getFileExtension(fileToProcess.getName());

        if (extension.equals("txt")) {
            Encryptor.setText(readTXTFile(fileToProcess));
            String processedText = Encryptor.run();
            if (processedText != null) {
                writeTXTFile(fileToProcess, processedText);
            }
        }

    }

    public File getFileFromDirectory() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a File");
        //Set extension filter
        FileChooser.ExtensionFilter extFilter =
            new FileChooser.ExtensionFilter("TXT files (.txt)", "*.txt");

        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showOpenDialog(Runner.getStage());

    }

    //Returns a String of the file extension, such as "txt" or "doc"
    public static String getFileExtension(String fileName) {

        String extension = ""; //Get the extension of the file
        int dot = fileName.lastIndexOf('.');
        int slash = Math.max(fileName.lastIndexOf('/'),
            fileName.lastIndexOf('\\'));
        if (dot > slash) {
            extension = fileName.substring(dot + 1);
        }
        return extension;
    }

    private static String readTXTFile(File txtFile) {

        String textToProcess = "";
        if (txtFile != null) {

            // Gets text to process from txtFile
            BufferedReader br = null;
            FileReader fr = null;
            try {

                fr = new FileReader(txtFile);
                br = new BufferedReader(fr);

                String currentLine;

                while ((currentLine = br.readLine()) != null) {
                    System.out.println("Adding | " + currentLine
                        + " | to txt File");
                    textToProcess += currentLine + "\n";
                }
                textToProcess =
                    textToProcess.substring(0, textToProcess.length() - 1);
                //remove the extra "\n" that was added at the end
                System.out.println("TEXTTT:\n" + textToProcess);

            } catch (IOException e) {

                e.printStackTrace();

            } finally {

                try {

                    if (br != null) {
                        br.close();
                    }
                    if (fr != null) {
                        fr.close();
                    }

                } catch (IOException ex) {

                    ex.printStackTrace();

                }
            }

        }
        return textToProcess;

    }

    public void writeTXTFile(File txtFile, String textToWrite) {

        // Writes textToWrite to txtFile
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            fw = new FileWriter(txtFile);
            bw = new BufferedWriter(fw);
            if (bw != null && textToWrite != null) {
                bw.write(textToWrite);
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

        Resources.playSound("encryptionComplete.aiff");

    }








    //put this in the folderchooser class and delete from here
    public File getFolderFromDirectory() {

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select a Folder");
        File selectedDirectory = chooser.showDialog(Runner.getStage());
        return selectedDirectory;
    }








}
