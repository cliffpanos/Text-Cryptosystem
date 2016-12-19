package runner;

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
    private static Rectangle2D screenBounds =
        Screen.getPrimary().getVisualBounds();
    private static boolean isFullScreen;

    private static double stageWidth = screenBounds.getWidth() * 7.0 / 9.0;
    private static double stageHeight = screenBounds.getHeight() * 7.0 / 9.0;
    private static double lastStageWidth = stageWidth;
    private static double lastStageHeight = stageHeight;

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
        stage.setWidth(stageWidth);
        stage.setHeight(stageHeight);
        stage.centerOnScreen();
        isFullScreen = false;

        stage.toFront();
        stage.setResizable(true);
        /*stage.onResize(e -> {
                System.out.println("resizing");
            });*/
        stage.show();

    }

    public static void windowButtonResize() {
        if (isFullScreen) {
            resizeWindow(lastStageWidth, lastStageHeight);
            isFullScreen = false;
        } else {
            lastStageWidth = stageWidth;
            lastStageHeight = stageHeight;
            resizeWindow(screenBounds.getWidth(), screenBounds.getHeight());
            isFullScreen = true;
        }
        //TODO
    }

    public static void resizeWindow(double length, double width) {
        int i;
        //implement
    }

    public static Stage getStage() {
        return stage;
    }

    public static double getStageWidth() {
        return stageWidth;
    }

    public static double getStageHeight() {
        return stageHeight;
    }

}
