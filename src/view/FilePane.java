package view;

import controller.Encryptor;
import resources.Resources;
import runner.Runner;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

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
    private VBox centralDisplay = new VBox();
    private static double paneWidth = MainScreen.getStageWidth() * 0.57 - 40.0;
    private static String processType = "encrypt"; // "encrypt" or "decrypt"

    private static UIButton chooseFileButton;
    private static UIButton runButton =
        new UIButton("encrypt.png", paneWidth - 60.0, 30, "Encrypt", false);
    private static ArrayList<UIFile> files = new ArrayList<>();
        //All of the files to be processed from fileChooser in UIFile
    private static UIFile file = null; //THIS IS TEMPORARY AND NEEDS TO BE DELETED later
                                                                                        //TODO

    public FilePane() {

        chooseFileButton = new UIButton(paneWidth - 60.0, 30,
            "Choose a File to " + processType);
        chooseFileButton.setAlignment(Pos.TOP_CENTER);
        chooseFileButton.setOnMouseClicked(e -> {

                //@Anthony
                    //PLZ don't modify this part beyond what you need to with the List<UIFile>;
                        //just to the backend and i'll worry about all of this <333

                List<File> tempFiles = UIFile.getFilesFromDirectory();
                if (tempFiles != null) {                                //TODO
                    for (File f : tempFiles) {
                        files.add(new UIFile(f));
                    }
                    file = files.get(0); //CHANGE THIS SO THAT IT GETS MORE THAN JUST THE FIRST FILE
                    updateRunButtonText();
                    centralDisplay.getChildren().setAll(new FileDisplay());
                    Resources.playSound("fileUpload.aiff");
                    runButton.setMouseActions(runButton.getClickable());
                    runButton.setBackgroundColor(Color.WHITE);
                    runButton.setOnMouseClicked(e2 -> {
                            file.processFile();
                            runButton.setBackgroundColor(Color.web("#D6EAF8"));
                        });
                }
            });

        runButton.setBackgroundColor(Color.web("#D6DBDF"));

        vBox.setAlignment(Pos.CENTER);

        centralDisplay.setBackground(new Background(new BackgroundFill(Color
            .WHITE, new CornerRadii(5.0, 5.0, 5.0, 5.0, false),
            new Insets(20))));
        centralDisplay.setPadding(new Insets(10));
        centralDisplay.setAlignment(Pos.CENTER);
        centralDisplay.getChildren().add(new Label("Choose one or more files"));

        vBox.getChildren().addAll(chooseFileButton, centralDisplay,
            runButton);

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

            this.setHeight(MainScreen.getStageHeight() - 200);
            this.setPrefWidth(paneWidth - 40);

            System.out.println(file.getName() + "\n" + file.isReadable() + "\n" + file.isWritable());

            this.getChildren().add(Resources.getImageView(
                file.getIconURL(), (paneWidth - 400.0)));

        }

    }

    @Override
    public void resize() {

        System.out.println("FilePane resizing");

        paneWidth = MainScreen.getStageWidth() * 0.57 - 40.0;
        this.setPrefWidth(paneWidth);
        this.setPrefHeight(MainScreen.getStageHeight() - 40.0);
        vBox.setPrefHeight(MainScreen.getStageHeight() - 40.0);

        centralDisplay.setPrefWidth(paneWidth - 40.0);
        centralDisplay.setMinHeight(MainScreen.getStageHeight() - 200);

    }

}
