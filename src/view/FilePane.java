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

    private VBox vBox = new VBox(10);

    private static String processType = "encrypt"; // "encrypt" or "decrypt"
    private static UIButton chooseFileButton;
    private File fileToProcess;

    public FilePane() {

        this.setPrefWidth(MainScreen.getStageWidth() * 0.57 - 40.0);
        this.setPrefHeight(MainScreen.getStageHeight());
        //this.setBackground(new Background(new BackgroundFill(Color
        //    .WHITE, new CornerRadii(5.0, 5.0, 5.0, 5.0, false),
        //    new Insets(10, 10, 10, 10))));
        //this.setPadding(new Insets(20));

        chooseFileButton = new UIButton(MainScreen.getStageWidth() * 0.57
            - 100.0, 30, "Choose a file to " + processType);
        chooseFileButton.setOnMouseClicked(e -> {
                fileToProcess = getFileFromDirectory();

                // Process the chosen File
                processFile(fileToProcess);
            });
        chooseFileButton.setAlignment(Pos.TOP_CENTER);

        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(chooseFileButton);

        this.getChildren().add(vBox);

    }

    public File getFileFromDirectory() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a File");
        //Set extension filter
        FileChooser.ExtensionFilter extFilter =
            new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");

        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        return fileChooser.showOpenDialog(Runner.getStage());
    }

    //put this in the folderchooser thing and delete fom
    public File getFolderFromDirectory() {

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select a Folder");
        File selectedDirectory = chooser.showDialog(Runner.getStage());
        return selectedDirectory;
    }

    public static void setProcessType(String encryptOrDecrypt) {
        processType = encryptOrDecrypt;
        chooseFileButton.getLabel().setText(
            "Choose a file to " + processType); //change to encrypt or decrypt
    }

    private static void processFile(File fileToProcess) {
        if (fileToProcess == null) {
            System.out.println("Trying to process a null file");
            return;
        } else {

            String textToProcess = "";

            // Gets text to process from fileToProcess
            BufferedReader br = null;
            FileReader fr = null;
            try {

                fr = new FileReader(fileToProcess);
                br = new BufferedReader(fr);

                String currentLine;

                while ((currentLine = br.readLine()) != null) {
                    System.out.println("Adding | " + currentLine + " | to textToProcess");
                    textToProcess = textToProcess + currentLine;
                }

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

            // Processes text
            Encryptor.setText(textToProcess);
            String processedText = Encryptor.run();
            if (processedText == null) {
                return;
            }

            // Writes processedText to fileToProcess
            BufferedWriter bw = null;
            FileWriter fw = null;

            try {

                fw = new FileWriter(fileToProcess);
                bw = new BufferedWriter(fw);
                if (bw != null && processedText != null) {
                    bw.write(processedText);
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

    }

}
