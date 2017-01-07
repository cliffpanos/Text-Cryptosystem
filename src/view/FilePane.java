package view;

import controller.Encryptor;
import resources.Resources;
import runner.Runner;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;

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

    private static FilePane instance;
    private static ScrollPane scrollPane = null;
    private static VBox centralDisplay = new VBox();
    private static double paneWidth = MainScreen.getStageWidth() * 0.57 - 40.0;
    private static String processType = "encrypt"; // "encrypt" or "decrypt"

    private static UIButton chooseFileButton;
    private static UIButton runButton =
        new UIButton("encrypt.png", paneWidth - 60.0, 30, "Encrypt", false);
    private static SingleFileDisplay singleFileDisplay = null;
    private static ArrayList<MultiFileDisplay> multiFileDisplays =
        new ArrayList<>();
    private static TreeSet<UIFile> files =
        new TreeSet<>((a, b) -> (a.getName().compareTo(b.getName())));
        //All of the files to be processed from fileChooser in UIFile
    private static boolean unactionableFiles;


    public FilePane() {

        instance = this;
        this.setSpacing(20); //This entire class is a VBox

        chooseFileButton = new UIButton(paneWidth - 60.0, 30,
            "Choose Files to " + processType);
        chooseFileButton.setAlignment(Pos.TOP_CENTER);
        chooseFileButton.setOnMouseClicked(e -> {

                List<File> tempFiles = UIFile.getFilesFromDirectory();
                boolean userChoseFiles = (tempFiles != null);
                if (userChoseFiles) {

                    files.clear(); //TODO make this an option to add more files or to clear all files and select totally new ones
                    addFiles(tempFiles);
                }

                if (!files.isEmpty() && userChoseFiles) {

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

                        scrollPane = createScrollPane();
                        scrollPane.setContent(centralDisplay);
                        this.getChildren().setAll(chooseFileButton,
                            scrollPane, runButton);
                    }
                    runButton.setMouseActions(runButton.getClickable());
                    runButton.setBackgroundColor(Color.WHITE);
                    runButton.setOnMouseClicked(e2 -> {
                            if (files.size() == 0) {
                                UIAlert.show("No Files Selected",
                                    "Choose one or more\n"
                                    + "files to be " + processType + "ed.",
                                    javafx.scene.control.Alert.AlertType.ERROR);
                            }
                            boolean encrypting = processType.equals("encrypt");
                            boolean unactionableFiles = false;
                            for (UIFile file : files) {
                                // Check to see if all files are processable
                                if ((file.hasEncryptedTags() && encrypting)
                                    || (!file.hasEncryptedTags() && !encrypting)) {
                                    if (encrypting) {
                                        UIAlert.show("File(s) Already Encrypted",
                                            "One or more of the files has already\n"
                                            + "been encrypted. To prevent the convolution\n"
                                            + "inherent in multiple encryptions,\n"
                                            + "these file(s) have not been encrypted again.",
                                            javafx.scene.control.Alert.AlertType.ERROR);
                                    } else {
                                        UIAlert.show("File(s) Not Decryptable",
                                            "One or more files that you are attempting to \n"
                                            + "decrypt has NOT been encrypted by this system.\n"
                                            + "To prevent a loss of data through false\n"
                                            + "decryption, you may not decrypt this text.",
                                            javafx.scene.control.Alert.AlertType.ERROR);
                                    }
                                    System.out.println("File " + file.getName()
                                        + " is not actionable");
                                    unactionableFiles = true;
                                }
                            }
                            //if all files are procesable (actionable):
                            if (!unactionableFiles) {
                                for (UIFile file : files) {
                                    System.out.println("PROCESSING");
                                    file.processFile();
                                }
                            }
                            UIAlert.setAlertable(true);

                            runButton.setBackgroundColor(
                                Color.web("#D6EAF8"));
                            update();
                        });
                    update();
                }

            });

        runButton.setBackgroundColor(Color.web("#D6DBDF"));

        centralDisplay.setBackground(new Background(new BackgroundFill(Color
            .WHITE, new CornerRadii(5.0, 5.0, 5.0, 5.0, false),
            new Insets(0.0))));
        centralDisplay.setPadding(new Insets(20));
        centralDisplay.setAlignment(Pos.CENTER);
        centralDisplay.getChildren()
            .add(new UILabel("Choose one or more files"));

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(20));
        this.getChildren().addAll(chooseFileButton, centralDisplay,
            runButton);

        setProcessType("encrypt");

        resize();

    }

    // This function was created to assist the addFiles button
    public static void addFiles(List<File> filesToAdd) {
        for (File file : filesToAdd) {
            if (file != null) {

                UIFile uIFile = new UIFile(file);

                if (uIFile.hasProcessableExtension()) {
                    files.add(uIFile);
                }
            }
        }
    }

    public static ScrollPane createScrollPane() {
        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setBackground(new Background(new BackgroundFill(Color
            .TRANSPARENT, new CornerRadii(5.0, 5.0, 5.0, 5.0, false),
            new Insets(40))));
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);

        scrollPane.setPrefHeight(MainScreen.getStageHeight() - 190);
        scrollPane.setPrefWidth(paneWidth - 40.0);
        scrollPane.setPrefViewportHeight(paneWidth - 40);
        scrollPane.setHmax(paneWidth - 40);

        return scrollPane;

    }

    public static void setProcessType(String encryptOrDecrypt) {
        processType = encryptOrDecrypt;
        chooseFileButton.getLabel().setText(
            "Choose Files to " + processType.substring(0, 1).toUpperCase()
                + processType.substring(1)); //change to Encrypt or Decrypt
        runButton.updateIcon(encryptOrDecrypt + ".png");
        update();
    }

    public static void update() {
        if (singleFileDisplay != null) {
            singleFileDisplay.update();
        }
        for (MultiFileDisplay multiFileDisplay : multiFileDisplays) {
            multiFileDisplay.update();
        }
        updateButtonText();
    }

    public static void updateButtonText() {

        String fileName = "";
        UILabel buttonLabel = runButton.getLabel();

        // Sets the initial text
        buttonLabel.setText(processType.substring(0, 1).toUpperCase()
            + processType.substring(1));

        // Adds additional text for additional files
        if (!files.isEmpty()) {
            fileName = " \"" + files.first().getName() + "\"";
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
        private UILabel encryptableLabel = new UILabel("", 15, "BOLD");
        private UIFile file = files.first();
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

            HBox infoHBox1 = makeInfoBox(file.isReadable(), true, Pos.CENTER);
            HBox infoHBox2 = makeInfoBox(file.isWritable(), false, Pos.CENTER);
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
    public static HBox makeInfoBox(boolean b, boolean readNotWrite, Pos pos) {
        ImageView icon = Resources.getImageView(b //isReadable or Writable
            ? "check_icon.png" : "cross_icon.png", 20);
        String ability = readNotWrite ? "Readable" : "Writable ";
        UILabel label = new UILabel(b ? ability : "Not " + ability + "!");
        HBox infoBox = new HBox(8);
        infoBox.getChildren().setAll(icon, label);
        infoBox.setAlignment(pos);
        return infoBox;
    }

    private static class MultiFileDisplay extends HBox {

        private UIFile file;
        private VBox infoVBox = new VBox(MainScreen.getStageHeight() / 70);
        private ImageView imageView;
        private UILabel nameLabel;
        private UIButton removeButton;

        private static VBox displays = new VBox(16);


        public MultiFileDisplay(UIFile file) {

            this.file = file;

            this.setSpacing(18);
            this.setAlignment(Pos.CENTER_LEFT);

            UIButton icon = new UIButton(file.getIconURL(),
                MainScreen.getStageHeight() / 10,
                MainScreen.getStageHeight() / 32.5); //imageDecrement
            icon.setOnMousePressed(e -> {});
            icon.setOnMouseReleased(e -> {});
            icon.setBackgroundColor(Color.web("#EAEDED", 0.9));

            HBox infoHBox1 =
                makeInfoBox(file.isReadable(), true, Pos.CENTER_LEFT);
            HBox infoHBox2 =
                makeInfoBox(file.isWritable(), false, Pos.CENTER_LEFT);
            infoVBox.getChildren().addAll(infoHBox1, infoHBox2);
            infoVBox.setAlignment(Pos.CENTER);

            nameLabel = new UILabel("set in update", 17.5, "BOLD");

            removeButton = new UIButton("remove_icon.png",
                MainScreen.getStageHeight() / 10,
                MainScreen.getStageHeight() / 40); //imageDecrement
            removeButton.setOnMouseClicked(e -> {
                        files.remove(this.file);
                    createAllNewDisplays();
                    centralDisplay.getChildren()
                        .setAll(MultiFileDisplay.getDisplaysVBox());

                    scrollPane = createScrollPane();
                    scrollPane.setContent(centralDisplay);
                    FilePane.instance.getChildren().setAll(chooseFileButton,
                        scrollPane, runButton);
                    updateButtonText();
                });
            removeButton.setVisible(false);

            HBox removeHBox = new HBox(removeButton);
            removeHBox.setAlignment(Pos.CENTER);
            this.setOnMouseEntered(e -> {
                    removeButton.setVisible(true);
                });
            this.setOnMouseExited(e -> {
                    removeButton.setVisible(false);
                });

            displays.setAlignment(Pos.CENTER_RIGHT);

            this.getChildren().addAll(icon, infoVBox, nameLabel, removeHBox);

            update();
            resize();

        }

        public void update() {
            nameLabel.setText(file.getName() + " — "
                + file.isActionable(processType));
        }

        public void resize() {
            this.setPrefHeight(120);
            this.setPrefWidth(paneWidth - 40);
        }

        //Static methods for MultiFileDisplay assembly into scrollPane:

        public static void createAllNewDisplays() {
            multiFileDisplays.clear();
            for (UIFile uiFile : files) {
                multiFileDisplays.add(new MultiFileDisplay(uiFile));
            }
        }

        public static VBox getDisplaysVBox() {
            displays.getChildren().clear();
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

        if (singleFileDisplay != null) {
            singleFileDisplay.resize(); // = new SingleFileDisplay();
        }
        for (MultiFileDisplay multiFileDisplay : multiFileDisplays) {
            multiFileDisplay.resize();
        }

        if (files.size() > 0) {
            centralDisplay.getChildren()
                .setAll(MultiFileDisplay.getDisplaysVBox());
        }

    }

}
