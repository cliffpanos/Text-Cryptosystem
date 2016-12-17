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

    private ImageView exit;
    private ImageView minimize;
    private ImageView fullScreen;


    public WindowButtons() {

        stage = Runner.getStage();

        exit = new ImageView(Resources.getImage("exit.png"));
        minimize = new ImageView(Resources.getImage("minimize.png"));
        fullScreen = new ImageView(Resources.getImage("fullScreen.png"));
        ImageView[] buttons = {exit, minimize, fullScreen};
        for (ImageView button : buttons) {
            button.setFitWidth(14);
            button.setPreserveRatio(true);
            button.setSmooth(true);
            button.setCache(true);
        }
        exit.setOnMouseClicked(e -> {
                System.exit(0);
            });
        minimize.setOnMouseClicked(e -> {
                stage.setIconified(true);
            });
        fullScreen.setOnMouseClicked(e -> {
                System.out.println("fullscreening");
                stage.setFullScreen(!stage.isFullScreen());
            });

        this.setSpacing(5);
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPrefWidth(75);
        this.setPrefHeight(25);
        this.getChildren().addAll(exit, minimize, fullScreen);


    }

}
