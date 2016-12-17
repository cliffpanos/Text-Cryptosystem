package runner;

import controller.Encryptor;
import view.MainScreen;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;


public class Runner extends Application {

    private static Stage stage;
    private static Rectangle2D primaryScreenBounds =
        Screen.getPrimary().getVisualBounds();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        stage = primaryStage;
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Encryptor");

        Scene startScene = new Scene(new MainScreen());
        startScene.setFill(Color.TRANSPARENT);
        stage.setScene(startScene);

        //set Stage boundaries to visible bounds of the main screen
        stage.setWidth((double) primaryScreenBounds.getWidth() * 7.0 / 9.0);
        stage.setHeight((double) primaryScreenBounds.getHeight() * 7.0 / 9.0);

        stage.toFront();
        stage.setResizable(true);
        /*stage.onResize(e -> {
                System.out.println("resizing");
            });*/
        stage.show();

    }

    public static Stage getStage() {
        return stage;
    }

    public static double getStageWidth() {
        return ((double) primaryScreenBounds.getWidth() * 7.0 / 9.0);
    }

    public static double getStageHeight() {
        return ((double) primaryScreenBounds.getHeight() * 7.0 / 9.0);
    }

}
