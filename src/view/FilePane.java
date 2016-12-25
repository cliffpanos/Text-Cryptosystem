package view;

import controller.Encryptor;
import controller.UIFile;
import resources.Resources;
import runner.Runner;

import java.io.File;


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
    private static String processType = "encrypt"; // "encrypt" or "decrypt"
    private boolean fileIsDisplayed = false;

    private static UIButton chooseFileButton;
    private static UIButton runButton =
        new UIButton("encrypt.png", paneWidth - 60.0, 30, "Encrypt");
    private static UIFile file;


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
                File tempFile = UIFile.getFileFromDirectory();
                if (tempFile != null) {
                    file = new UIFile(tempFile);
                    if (!fileIsDisplayed) {
                        vBox.getChildren().addAll(Resources.getImageView(
                            "txtFile.png", (paneWidth - 250.0)),
                            runButton);
                        fileIsDisplayed = true;
                    }
                    Resources.playSound("fileUpload.aiff");
                }
            });

        runButton.setOnMouseClicked(e -> {
                file.processFile();
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

}
