package view;

import java.util.List;
import java.util.ArrayList;

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

    private VBox menu;
    private double paneWidth = MainScreen.getStageWidth() * 0.27;

    public OptionsMenu() {

        this.setAlignment(Pos.TOP_CENTER);
        this.setBackground(new Background(new BackgroundFill(Color.WHITE,
            new CornerRadii(5.0, 5.0, 5.0, 5.0, false),
            new Insets(10, 10, 10, 10))));

        Color c = Color.TRANSPARENT;
        this.setBorder(new Border(new BorderStroke(Color.TRANSPARENT,
            Color.web("#B3B3B3"), Color.TRANSPARENT, Color.TRANSPARENT,
            BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
            BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
            new CornerRadii(0.0), new BorderWidths(1.33), new Insets(0.0))));


        menu = new VBox(20);
        menu.setMinHeight(MainScreen.getStageHeight() - 20);
        menu.setPadding(new Insets(20));

        UIButton inputText = new UIButton((paneWidth - 40), 30,
            "Input Text Directly");
        UIButton chooseFile = new UIButton((paneWidth - 40), 30,
            "Choose a File");
        UIButton chooseFolder = new UIButton((paneWidth - 40), 30,
            "Choose a Folder");

        IconBox iconBox1 = new IconBox("pencil.png", false);
        IconBox iconBox2 = new IconBox("file.png", true);
        IconBox iconBox3 = new IconBox("folder.png", true);


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

            this.setPrefWidth(paneWidth - 100.0);
            this.setAlignment(Pos.CENTER);
            this.setSpacing(20);

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
    }

    public void resize() {

        paneWidth = MainScreen.getStageWidth() * 0.27;
        this.setPrefWidth(paneWidth);
        this.setPrefHeight(MainScreen.getStageHeight());

    }

}
