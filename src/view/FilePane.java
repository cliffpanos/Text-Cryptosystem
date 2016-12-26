package view;

import controller.Encryptor;
import resources.Resources;
import runner.Runner;

import java.io.File;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class FilePane extends StackPane implements Resizable {

    private VBox vBox = new VBox(20);
    private static double paneWidth = MainScreen.getStageWidth() * 0.57 - 40.0;
    private static String processType = "encrypt"; // "encrypt" or "decrypt"

    private static UIButton chooseFileButton;
    private static UIButton runButton =
        new UIButton("encrypt.png", paneWidth - 60.0, 30, "Encrypt");
    private static UIFile file = null; //the file to be processed

    public FilePane() {

        this.setPrefWidth(paneWidth);
        this.setPrefHeight(MainScreen.getStageHeight() - 40.0);
        //this.setBackground(new Background(new BackgroundFill(Color
        //    .WHITE, new CornerRadii(5.0, 5.0, 5.0, 5.0, false),
        //    new Insets(10, 10, 10, 10))));
        //this.setPadding(new Insets(20));

        chooseFileButton = new UIButton(paneWidth - 60.0, 30,
            "Choose a File to " + processType);
        chooseFileButton.setAlignment(Pos.TOP_CENTER);
        chooseFileButton.setOnMouseClicked(e -> {
                File tempFile = UIFile.getFileFromDirectory();
                if (tempFile != null) {
                    file = new UIFile(tempFile);
                    System.out.println(file.getName() + "\n" + file.isReadable() + "\n" + file.isWritable());
                    FileDisplay fileDisplay = new FileDisplay();
                    updateRunButtonText();
                    vBox.getChildren().clear();
                    vBox.getChildren().addAll(chooseFileButton, fileDisplay);
                    Resources.playSound("fileUpload.aiff");
                }
            });

        runButton.setOnMouseClicked(e -> {
                file.processFile();
            });

        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setMaxHeight(MainScreen.getStageHeight() - 40.0);
        vBox.getChildren().addAll(chooseFileButton);

        this.getChildren().add(vBox);

        setProcessType("Encrypt");

        resize();

    }

    public static void setProcessType(String encryptOrDecrypt) {
        processType = encryptOrDecrypt;
        chooseFileButton.getLabel().setText(
            "Choose a File to " + processType); //change to encrypt or decrypt
        runButton.updateIcon(encryptOrDecrypt + ".png");
        updateRunButtonText();
    }

    public static void updateRunButtonText() {

        String fileName = "";
        if (file != null) {
            fileName = " \"" + file.getName() + "\"";
        }
        runButton.getLabel().setText(processType.substring(0, 1).toUpperCase()
            + processType.substring(1) + fileName);
    }


    //Private inner class for the display that shows file statistics & the icon

    private class FileDisplay extends VBox {

        private HBox infoBox = new HBox(15);
        private ImageView imageView;
        private Label label;

        public FileDisplay() {

            this.setSpacing(15);
            this.setAlignment(Pos.CENTER);

            this.getChildren().addAll(Resources.getImageView(
                file.getIconURL(), (paneWidth - 250.0)),
                runButton);

        }

    }

    @Override
    public void resize() {
        System.out.println("FilePane resizing");
    }

}
