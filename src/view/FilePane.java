package view;

import controller.Encryptor;
import runner.Runner;

import java.io.File;
import javafx.stage.DirectoryChooser;

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
        this.setBackground(new Background(new BackgroundFill(Color
            .WHITE, new CornerRadii(5.0, 5.0, 5.0, 5.0, false),
            new Insets(10, 10, 10, 10))));
        //this.setPadding(new Insets(20));

        chooseFileButton = new UIButton(MainScreen.getStageWidth() * 0.57
            - 80.0, 30, "Choose a file to " + processType);
        chooseFileButton.setOnMouseClicked(e -> {
                fileToProcess = getFileFromDirectory();
            });
        chooseFileButton.setAlignment(Pos.CENTER);

        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(chooseFileButton);

        this.getChildren().add(vBox);

    }

    public File getFileFromDirectory() {

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select a File");
        File selectedDirectory = chooser.showDialog(Runner.getStage());
        return selectedDirectory;
    }

    public static void setProcessType(String encryptOrDecrypt) {
        processType = encryptOrDecrypt;
        chooseFileButton.getLabel().setText(
            "Choose a file to " + processType); //change to encrypt or decrypt
    }
}
