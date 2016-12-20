package view;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;


public class OptionsMenu extends StackPane {

    private VBox menu;

    public OptionsMenu() {

        double width = (double) MainScreen.getStageWidth() * 0.27;
        this.setPrefWidth(width);
        this.setPrefHeight(MainScreen.getStageHeight());
        this.setAlignment(Pos.TOP_CENTER);
        this.setBackground(new Background(new BackgroundFill(Color
            .web("#FDFEFE", 1.0), new CornerRadii(5.0, 5.0, 5.0, 5.0, false),
            new Insets(10, 10, 10, 10))));

        Color c = Color.TRANSPARENT;
        this.setBorder(new Border(new BorderStroke(Color.web("#B3B3B3"),
            BorderStrokeStyle.SOLID, new CornerRadii(0.0),
            new BorderWidths(1.0))));


        menu = new VBox(20);
        menu.setMinHeight(MainScreen.getStageHeight() - 20);
        menu.setPadding(new Insets(20));

        UIButton inputText = new UIButton((width - 40), 30,
            "Input Text Directly");
        UIButton chooseFile = new UIButton((width - 40), 30,
            "Choose a File");
        UIButton chooseFolder = new UIButton((width - 40), 30,
            "Choose a Folder");

        menu.getChildren().addAll(inputText, chooseFile, chooseFolder);
        this.getChildren().add(menu);

        UIButton[] buttons = {inputText, chooseFile, chooseFolder};
        for (UIButton button : buttons) {
            HBox buttonHolder = button.getClickable();
            // Sets simple highlighting when mouse goes over button
            buttonHolder.setOnMouseEntered(e -> {
                    if (!button.isSelected()) {
                        button.setBackgroundColor(Color.web("#D6DBDF", 0.9));
                    }
                });
            buttonHolder.setOnMouseExited(e -> {
                    if (!button.isSelected()) {
                        button.setBackgroundColor(Color.WHITE);
                    }
                });

            MainScreen.MenuOptions option = ((button == inputText)
                ? MainScreen.MenuOptions.INPUTTEXT
                : ((button == chooseFile) ? MainScreen.MenuOptions.CHOOSEFILE
                : MainScreen.MenuOptions.CHOOSEFOLDER));

            buttonHolder.setOnMouseClicked(e -> {
                    for (UIButton updateButton : buttons) {
                        updateButton.setSelected(false);
                        updateButton.setBackgroundColor(Color.web("#F7F7F7",
                            0.9));
                    }
                    button.setSelected(true);
                    button.setBackgroundColor(Color.web("#AEB6BF", 0.9));
                    MainScreen.switchMenu(option);
            });
        }
    }

}
