package view;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;


public class OptionsMenu extends StackPane implements Resizable {

    private VBox menu = new VBox();
    private double paneWidth = MainScreen.getStageWidth() * 0.27;
    private IconBox[] iconBoxes;

    public OptionsMenu() {

        this.setAlignment(Pos.TOP_CENTER);
        this.setBackground(new Background(new BackgroundFill(Color.WHITE,
            new CornerRadii(5.0, 5.0, 5.0, 5.0, false),
            new Insets(10, 10, 10, 10))));

        Color c = Color.TRANSPARENT;
        this.setBorder(new Border(new BorderStroke(c, Color.web("#B3B3B3"),
            c, c, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
            BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
            new CornerRadii(0.0), new BorderWidths(1.0), new Insets(0.0))));


        menu.setMinHeight(MainScreen.getStageHeight() - 20);
        menu.setPadding(new Insets(30, 20, 30, 20));

        UIButton inputText = new UIButton((paneWidth - 40), 30,
            "Input Text Directly");
        UIButton chooseFile = new UIButton((paneWidth - 40), 30,
            "Choose Some Files");
        UIButton chooseFolder = new UIButton((paneWidth - 40), 30,
            "Choose Folders of Files");

        IconBox iconBox1 = new IconBox("pencil.png", false);
        IconBox iconBox2 = new IconBox("file.png", true);
        IconBox iconBox3 = new IconBox("folder.png", true);
        IconBox[] localIconBoxes = {iconBox1, iconBox2, iconBox3};
        iconBoxes = localIconBoxes;

        menu.getChildren().addAll(iconBox1, inputText, iconBox2, chooseFile,
            iconBox3, chooseFolder);
        this.getChildren().add(menu);

        UIButton[] buttons = {inputText, chooseFile, chooseFolder};
        for (UIButton button : buttons) {
            HBox buttonHolder = button.getClickable();
            // Sets simple highlighting when mouse goes over button
            buttonHolder.setOnMouseEntered(e -> {
                    if (!button.isSelected()) {
                        button.setBackgroundColor(Color.web("#EAEDED", 0.9));
                    }
                });
            buttonHolder.setOnMouseExited(e -> {
                    if (!button.isSelected()) {
                        button.setBackgroundColor(Color.WHITE);
                    }
                });
            buttonHolder.setOnMousePressed(e -> {
                    if (!button.isSelected()) {
                        button.setBackgroundColor(Color.web("#D6DBDF"));
                    }
                });

            MainScreen.MenuOptions option = ((button == inputText)
                ? MainScreen.MenuOptions.INPUTTEXT
                : ((button == chooseFile) ? MainScreen.MenuOptions.CHOOSEFILE
                : MainScreen.MenuOptions.CHOOSEFOLDER));

            buttonHolder.setOnMouseClicked(e -> {
                    for (UIButton updateButton : buttons) {
                        updateButton.setSelected(false);
                        updateButton.setBackgroundColor(Color.WHITE);
                    }
                    button.setSelected(true);
                    button.setBackgroundColor(Color.web("#D6EAF8", 0.9));
                    MainScreen.switchMenu(option);
            });

        }

        //The options menu initializes with inputText selected by default
        inputText.setSelected(true);
        inputText.setBackgroundColor(Color.web("#D6EAF8", 0.9));

        resize();

    }

    private class IconBox extends VBox {

        public IconBox(String iconURL, boolean topLineYes) {

            this.setAlignment(Pos.CENTER);

            UIButton icon = new UIButton(iconURL,
                MainScreen.getStageHeight() / 9);
            icon.setOnMousePressed(e -> {});
            icon.setOnMouseReleased(e -> {});
            icon.setBackgroundColor(Color.web("#EAEDED", 0.9));

            HBox iconHBox = new HBox(icon);
            iconHBox.setAlignment(Pos.CENTER);

            Rectangle topLine =
                new Rectangle((paneWidth - 20), 1.18, Color.web("#D7DBDD"));

            if (topLineYes) {
                this.getChildren().addAll(topLine, iconHBox);
            } else {
                this.getChildren().add(iconHBox);
            }

        }

        public void resize() {
            this.setPrefWidth(paneWidth - 100.0);
            this.setSpacing(MainScreen.getStageHeight() / 25);
        }
    }

    public void resize() {

        paneWidth = MainScreen.getStageWidth() * 0.27;
        this.setPrefWidth(paneWidth);
        this.setPrefHeight(MainScreen.getStageHeight());
        menu.setSpacing(MainScreen.getStageHeight() / 25);
        for (IconBox iconBox : iconBoxes) {
            iconBox.resize();
        }

    }

}
