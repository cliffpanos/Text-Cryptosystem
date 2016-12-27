package view;

import controller.Encryptor;
import resources.Resources;
import runner.Runner;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

import javafx.scene.layout.StackPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.image.ImageView;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class FilePane extends VBox implements Resizable {

    private ScrollPane scrollPane = new ScrollPane();
    private VBox centralDisplay = new VBox();
    private static SingleFileDisplay singleFileDisplay = null;
    private static MultiFileDisplay multiFileDisplay = null;
    private static double paneWidth = MainScreen.getStageWidth() * 0.57 - 40.0;
    private static String processType = "encrypt"; // "encrypt" or "decrypt"

    private static UIButton chooseFileButton;
    private static UIButton runButton =
        new UIButton("encrypt.png", paneWidth - 60.0, 30, "Encrypt", false);
    private static ArrayList<UIFile> files = new ArrayList<>();
        //All of the files to be processed from fileChooser in UIFile


    public FilePane() {

        this.setSpacing(20); //This entire class is a VBox

        chooseFileButton = new UIButton(paneWidth - 60.0, 30,
            "Choose a File to " + processType);
        chooseFileButton.setAlignment(Pos.TOP_CENTER);
        chooseFileButton.setOnMouseClicked(e -> {

                //@Anthony
                    //PLZ don't modify this part beyond what you need to with the List<UIFile>;
                        //just to the backend and i'll worry about all of this <333

                List<File> tempFiles = UIFile.getFilesFromDirectory();
                if (tempFiles != null) {

                    files.clear(); //TODO make this an option to add more files or to clear all files and select totally new ones
                    for (File f : tempFiles) {

                        if (f != null) {
                            files.add(new UIFile(f));
                        }

                    }
                    if (!files.isEmpty()) {

                        for (UIFile uifile : files) {
                            System.out.println("Name: " + uifile.getName());
                        }

                        Resources.playSound("fileUpload.aiff");

                        if (files.size() == 1) {
                            singleFileDisplay = new SingleFileDisplay();
                            centralDisplay.getChildren()
                                .setAll(singleFileDisplay);
                            this.getChildren().setAll(chooseFileButton,
                                centralDisplay, runButton);
                        } else { //When there is more than one File
                            multiFileDisplay = new MultiFileDisplay();
                            centralDisplay.getChildren()
                                .setAll(multiFileDisplay);
                            scrollPane.setContent(centralDisplay);
                            this.getChildren().setAll(chooseFileButton,
                                scrollPane, runButton);
                        }
                        runButton.setMouseActions(runButton.getClickable());
                        runButton.setBackgroundColor(Color.WHITE);
                        runButton.setOnMouseClicked(e2 -> {
                                for (UIFile f : files) {
                                    System.out.println("Processing a file");
                                    f.processFile();
                                }
                                runButton.setBackgroundColor(
                                    Color.web("#D6EAF8"));
                                updateButtonTexts();
                            });
                        updateButtonTexts();
                    }

                }
            });

        runButton.setBackgroundColor(Color.web("#D6DBDF"));

        this.setAlignment(Pos.CENTER);

        scrollPane.setBackground(new Background(new BackgroundFill(Color
            .TRANSPARENT, new CornerRadii(5.0, 5.0, 5.0, 5.0, false),
            new Insets(20))));
        scrollPane.setPannable(false);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        centralDisplay.setBackground(new Background(new BackgroundFill(Color
            .WHITE, new CornerRadii(5.0, 5.0, 5.0, 5.0, false),
            new Insets(20))));
        centralDisplay.setPadding(new Insets(20));
        centralDisplay.setAlignment(Pos.CENTER);
        centralDisplay.getChildren().add(new Label("Choose one or more files"));

        this.getChildren().addAll(chooseFileButton, centralDisplay,
            runButton);

        setProcessType("Encrypt");

        resize();

    }


    public static void setProcessType(String encryptOrDecrypt) {
        processType = encryptOrDecrypt;
        chooseFileButton.getLabel().setText(
            "Choose a File to " + processType); //change to encrypt or decrypt
        runButton.updateIcon(encryptOrDecrypt + ".png");
        updateButtonTexts();
    }

    public static void updateButtonTexts() {

        String fileName = "";
        Label buttonLabel = runButton.getLabel();

        // Sets the initial text
        buttonLabel.setText(processType.substring(0, 1).toUpperCase()
            + processType.substring(1));

        if (singleFileDisplay != null) {
            singleFileDisplay.update();
        }
        if (multiFileDisplay != null) {
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



    //Private inner class for the display that shows file statistics & the icon

    private class SingleFileDisplay extends HBox {

        private VBox infoVBox = new VBox(15);
        private Label encryptableLabel = new Label("");
        private UIFile file = files.get(0);
        private Rectangle topLine;
        private Text titleLabel;

        public SingleFileDisplay() {

            this.setSpacing(15);
            this.setAlignment(Pos.CENTER);

            titleLabel = new Text(file.getName().substring(0, file
                .getName().length() - file.getFileExtension().length() - 1));
            titleLabel.setFont(Font.font("", FontWeight.EXTRA_BOLD, 24));
            titleLabel.setTextAlignment(TextAlignment.CENTER);
            Label extLabel = new Label("Text Type: " + file.getFileExtension()
                + " file");
            extLabel.setFont(new Font(16));

            encryptableLabel.setFont(new Font(15));

            HBox infoHBox1 = new HBox(10);
            HBox infoHBox2 = new HBox(10);
            makeInfoBox(infoHBox1, file.isReadable(), true);
            makeInfoBox(infoHBox2, file.isWritable(), false);
            update();
            resize();

            infoVBox.setAlignment(Pos.CENTER);
            infoVBox.getChildren().setAll(titleLabel, topLine, extLabel,
                infoHBox1, infoHBox2, encryptableLabel);
            this.getChildren().addAll(infoVBox, Resources.getImageView(
                file.getIconURL(), (paneWidth - 420.0)));

        }

        public void update() {
            System.out.println("update called");
            System.out.println(processType.equals("Encrypt"));
            encryptableLabel.setText(processType.equals("Encrypt")
                ? (file.isEncryptable() ? "Encryptable" : "Already Encrypted")
                : "");
            System.out.println(encryptableLabel.getText());
        }

        public void makeInfoBox(HBox infoBox, boolean c, boolean readNotWrite) {
            ImageView icon = Resources.getImageView(c //isReadable or Writable
                ? "check_icon.png" : "cross_icon.png", 20);
            String ability = readNotWrite ? "Readable" : "Writable ";
            Label label = new Label(c ? ("File is " + ability)
                : "File is Not " + ability + "!");
            infoBox.getChildren().setAll(icon, label);
            infoBox.setAlignment(Pos.CENTER);
        }

        public void resize() {
            this.setHeight(MainScreen.getStageHeight() - 200);
            this.setPrefWidth(paneWidth - 40);
            infoVBox.setPrefWidth((paneWidth - 40) / 2.5);

            topLine = new Rectangle((paneWidth - 40) / 3.25,
                1.18, Color.web("#D7DBDD"));
            titleLabel.setWrappingWidth((paneWidth - 40) / 3.5);

        }

    }

    private class MultiFileDisplay extends VBox {

        private VBox vBox = new VBox(15);
        private HBox infoBox = new HBox(15);
        private ImageView imageView;
        private Label label;

        public MultiFileDisplay() {

            this.setSpacing(15);
            this.setAlignment(Pos.CENTER);

            for (UIFile file : files) {
                System.out.println(file.getName() + "\n" + file.isReadable()
                    + "\n" + file.isWritable());

                // Message from Anthony: Cliff, the following two lines create a
                // weird effect when multiple files are selected. I will let you
                // decide how to handle that

                /*UIButton icon = new UIButton(iconURL, (paneWidth / 3.5));
                icon.setOnMousePressed(e -> {});
                icon.setOnMouseReleased(e -> {});
                icon.setBackgroundColor(Color.web("#EAEDED", 0.9));*/

                this.getChildren().add(Resources.getImageView(
                    file.getIconURL(), (paneWidth - 400.0)));
            }

            resize();

        }

        public void update() {

        }

        public void resize() {
            this.setHeight(MainScreen.getStageHeight() - 200);
            this.setPrefWidth(paneWidth - 40);
        }

    }

    @Override
    public void resize() {

        paneWidth = MainScreen.getStageWidth() * 0.57 - 40.0;
        this.setPrefWidth(paneWidth);
        this.setPrefHeight(MainScreen.getStageHeight() - 40.0);

        centralDisplay.setPrefWidth(paneWidth - 40.0);
        centralDisplay.setMinHeight(MainScreen.getStageHeight() - 200);
        scrollPane.setMaxHeight(MainScreen.getStageHeight() - 200);

        if (singleFileDisplay != null) {
            System.out.println("filePane resize");
            singleFileDisplay = new SingleFileDisplay();
        }
        if (multiFileDisplay != null) {
            multiFileDisplay.resize();
        }

    }

}
