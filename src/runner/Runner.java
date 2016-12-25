package runner;

import view.MainScreen;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
    private static double lastX = 0;
    private static double lastY = 0;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        stage = primaryStage;
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Encryptor");

        Scene mainScene = new Scene(new MainScreen());
        mainScene.setFill(Color.TRANSPARENT);
        stage.setScene(mainScene);

        //set Stage boundaries to visible bounds of the main screen
        stage.setWidth(stageWidth);
        stage.setHeight(stageHeight);
        stage.centerOnScreen();
        lastX = stage.getX();
        lastY = stage.getY();
        isFullScreen = false;

        stage.toFront();
        stage.setResizable(true);

        stage.show();

    }

    public static void toggleFullScreen() {
        if (isFullScreen) { //revert the stage to the previous size & location
            stage.setX(lastX);
            stage.setY(lastY);
            stage.setWidth(lastStageWidth);
            stage.setHeight(lastStageHeight);
            isFullScreen = false;
        } else { //make it fullScreen
            lastStageWidth = stageWidth;
            lastStageHeight = stageHeight;
            lastX = stage.getX();
            lastY = stage.getY();
            stage.setX(screenBounds.getMinX());
            stage.setY(screenBounds.getMinY());
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());
            isFullScreen = true;
        }
    }

    public static void resizeWindow(double length, double width) {
        int i;
        //implement
        //TODO
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

    public static void setXandY(double x, double y) {
        lastX = y;
        lastY = y;
    }

}
