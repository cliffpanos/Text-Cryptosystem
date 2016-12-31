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
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class FilePane extends VBox implements Resizable {

    private ScrollPane scrollPane = new ScrollPane();
    private VBox centralDisplay = new VBox();
    private static double paneWidth = MainScreen.getStageWidth() * 0.57 - 40.0;
    private static String processType = "encrypt"; // "encrypt" or "decrypt"

    private static UIButton chooseFileButton;
    private static UIButton runButton =
        new UIButton("encrypt.png", paneWidth - 60.0, 30, "Encrypt", false);
    private static SingleFileDisplay singleFileDisplay = null;
    private static ArrayList<MultiFileDisplay> multiFileDisplays =
        new ArrayList<>();
    private static ArrayList<UIFile> files = new ArrayList<>();
        //All of the files to be processed from fileChooser in UIFile


    public FilePane() {

        this.setSpacing(20); //This entire class is a VBox

        chooseFileButton = new UIButton(paneWidth - 60.0, 30,
            "Choose a File to " + processType);
        chooseFileButton.setAlignment(Pos.TOP_CENTER);
        chooseFileButton.setOnMouseClicked(e -> {

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
                            MultiFileDisplay.createAllNewDisplays();
                            centralDisplay.getChildren()
                                .setAll(MultiFileDisplay.getDisplaysVBox());
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

        scrollPane.setBackground(new Background(new BackgroundFill(Color
            .TRANSPARENT, new CornerRadii(5.0, 5.0, 5.0, 5.0, false),
            new Insets(41.0))));
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);

        centralDisplay.setBackground(new Background(new BackgroundFill(Color
            .WHITE, new CornerRadii(5.0, 5.0, 5.0, 5.0, false),
            new Insets(0.0))));
        centralDisplay.setPadding(new Insets(20));
        centralDisplay.setAlignment(Pos.CENTER);
        centralDisplay.getChildren()
            .add(new UILabel("Choose one or more files"));

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(22));
        this.getChildren().addAll(chooseFileButton, centralDisplay,
            runButton);

        setProcessType("encrypt");

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
        UILabel buttonLabel = runButton.getLabel();

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



    //Private inner class for the display that shows file statistics & the icon

    private static class SingleFileDisplay extends HBox {

        private VBox infoVBox = new VBox(15);
        private UILabel encryptableLabel = new UILabel();
        private UIFile file = files.get(0);
        private Rectangle topLine;
        private Text titleLabel;

        public SingleFileDisplay() {

            this.setAlignment(Pos.CENTER);

            titleLabel = new Text(file.getName().substring(0, file
                .getName().length() - file.getFileExtension().length() - 1));
            titleLabel.setFont(Font.font("Helvetica",
                FontWeight.EXTRA_BOLD, 24));
            titleLabel.setTextAlignment(TextAlignment.CENTER);

            UILabel extLabel = new UILabel("Text Type: "
                + file.getFileExtension() + " file", 16);

            encryptableLabel.setFont(15);

            HBox infoHBox1 = makeInfoBox(file.isReadable(), true);
            HBox infoHBox2 = makeInfoBox(file.isWritable(), false);
            update();
            resize();

            infoVBox.setAlignment(Pos.CENTER);
            infoVBox.getChildren().setAll(titleLabel, topLine, extLabel,
                infoHBox1, infoHBox2, encryptableLabel);
            this.getChildren().setAll(infoVBox, Resources.getImageView(
                file.getIconURL(), (paneWidth - 420.0)));

        }

        public void update() {
            encryptableLabel.setText(file.isActionable(processType));
        }

        public void resize() {
            this.setSpacing(paneWidth / 15);
            this.setHeight(MainScreen.getStageHeight() - 200);
            this.setPrefWidth(paneWidth - 40);

            topLine = new Rectangle((paneWidth - 40) / 3.25,
                1.18, Color.web("#D7DBDD"));
            titleLabel.setWrappingWidth((paneWidth - 40) / 3.5);

            this.getChildren().setAll(infoVBox, Resources.getImageView(
                file.getIconURL(), (paneWidth - 420.0)));
        }

    }

    //Used in SingleFileDisplay and MultiFileDisplay for the read/write boxes
    public static HBox makeInfoBox(boolean p, boolean readNotWrite) {
        ImageView icon = Resources.getImageView(p //isReadable or Writable
            ? "check_icon.png" : "cross_icon.png", 20);
        String ability = readNotWrite ? "Readable" : "Writable ";
        UILabel label = new UILabel(p ? ("File is " + ability)
            : "File is Not " + ability + "!");
        HBox infoBox = new HBox(8);
        infoBox.getChildren().setAll(icon, label);
        infoBox.setAlignment(Pos.CENTER);
        return infoBox;
    }

    private static class MultiFileDisplay extends HBox {

        private UIFile file;
        private VBox infoVBox = new VBox(MainScreen.getStageHeight() / 70);
        private ImageView imageView;
        private UILabel label;

        public MultiFileDisplay(UIFile file) {

            this.file = file;

            this.setSpacing(18);
            this.setAlignment(Pos.CENTER);

            UIButton icon = new UIButton(file.getIconURL(),
                MainScreen.getStageHeight() / 10,
                MainScreen.getStageHeight() / 32.5); //imageDecrement
            icon.setOnMousePressed(e -> {});
            icon.setOnMouseReleased(e -> {});
            icon.setBackgroundColor(Color.web("#EAEDED", 0.9));

            HBox infoHBox1 = makeInfoBox(file.isReadable(), true);
            HBox infoHBox2 = makeInfoBox(file.isWritable(), false);
            infoVBox.getChildren().addAll(infoHBox1, infoHBox2);
            infoVBox.setAlignment(Pos.CENTER);

            this.getChildren().addAll(icon, infoVBox);

            resize();

        }

        public void update() {

        }

        public void resize() {
            this.setHeight(MainScreen.getStageHeight() - 200);
            this.setPrefWidth(paneWidth - 40);
        }

        public static void createAllNewDisplays() {
            multiFileDisplays.clear();
            for (UIFile uiFile : files) {
                multiFileDisplays.add(new MultiFileDisplay(uiFile));
                System.out.println("adding display");
            }
        }

        public static VBox getDisplaysVBox() {
            VBox displays = new VBox(16);
            displays.setAlignment(Pos.CENTER);
            for (MultiFileDisplay multiFileDisplay : multiFileDisplays) {
                displays.getChildren().addAll(multiFileDisplay, new Rectangle(
                    (paneWidth - 100), 1.18, Color.web("#D7DBDD")));
            }
            return displays;
        }


    }


    @Override
    public void resize() {

        paneWidth = MainScreen.getStageWidth() * 0.57 - 40.0;
        this.setPrefWidth(paneWidth);
        this.setPrefHeight(MainScreen.getStageHeight() - 40.0);

        centralDisplay.setPrefWidth(paneWidth - 40.0);
        centralDisplay.setMinHeight(MainScreen.getStageHeight() - 190);
        scrollPane.setPrefHeight(MainScreen.getStageHeight() - 190);
        scrollPane.setPrefWidth(paneWidth - 40.0);
        scrollPane.setPrefViewportHeight(paneWidth - 40);
        scrollPane.setHmax(paneWidth - 40);

        if (singleFileDisplay != null) {
            singleFileDisplay.resize(); // = new SingleFileDisplay();
        }
        for (MultiFileDisplay multiFileDisplay : multiFileDisplays) {
            multiFileDisplay.resize();
        }

    }

}
