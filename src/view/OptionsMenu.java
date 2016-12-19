package view;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
//import javafx.scene.layout.StrokeType;
//import javafx.scene.layout.StrokeLineJoin;
//import javafx.scene.layout.StrokeLineJoin;
//import javafx.scene.layout.


public class OptionsMenu extends StackPane {

    private VBox menu;

    public OptionsMenu() {

        double width = (double) MainScreen.getStageWidth() * 0.27;
        this.setPrefWidth(width);
        this.setPrefHeight(MainScreen.getStageHeight());
        this.setAlignment(Pos.TOP_CENTER);
        this.setBackground(new Background(new BackgroundFill(Color
            .web("#FDFEFE", 1.0), new CornerRadii(3.0, 3.0, 3.0, 3.0, false),
            new Insets(10))));

        /*List<Double> borderList = new ArrayList(4);
        borderList.addAll(3.0, 3.0, 3.0, 3.0);
        this.setBorder(new Border(new BorderStroke(Color.web("#B3B3B3"),
            new BorderStrokeStyle(
            new StrokeType(), new StrokeLineJoin(),
            new StrokeLineCap(), 5.0, 5.0, borderList),

            new CornerRadii(2.0), new BorderWidths(3.0))));*/


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

    }

}
