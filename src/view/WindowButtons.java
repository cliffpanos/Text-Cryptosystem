package view;

import runner.Runner;
import resources.Resources;

import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class WindowButtons extends HBox {

    private Stage stage;

    private HBox hBox;
    private ImageView exit;
    private ImageView minimize;
    private ImageView fullScreen;


    public WindowButtons() {

        stage = Runner.getStage();

        exit = new ImageView(Resources.getImage("exit.png"));
        exit.setFitWidth(14);
        exit.setPreserveRatio(true);
        exit.setSmooth(true);
        minimize = new ImageView(Resources.getImage("minimize.png"));
        fullScreen = new ImageView(Resources.getImage("fullScreen.png"));

        this.setSpacing(7);
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPrefWidth(75);
        this.setPrefHeight(25);
        this.getChildren().addAll(exit, minimize, fullScreen);

        //stage.setIconified(true);
        //stage.setFullScreen(true);



    }

}
