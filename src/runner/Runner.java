package runner;

import view.MainScreen;
import view.Resizable;

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
    private static boolean isFullScreen;
    private static Rectangle2D screenBounds; // = Screen.getPrimary().getVisualBounds();

    private static double stageWidth = 1000; //screenBounds.getWidth() * 7.5 / 9.0;
    private static double stageHeight = 800; //screenBounds.getHeight() * 7.5 / 9.0;
    private static double lastStageWidth = stageWidth;
    private static double lastStageHeight = stageHeight;
    private static double lastX = 0;
    private static double lastY = 0;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        try {
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

        } catch (Exception e) {
            System.out.println("Caught exception");
        }

    }

    public static void toggleFullScreen() {

        if (isFullScreen) { //revert the stage to the previous size & location
            stage.setX(lastX);
            stage.setY(lastY);
            stage.setWidth(lastStageWidth);
            stage.setHeight(lastStageHeight);
            stageWidth = lastStageWidth; //Update actual width for resize()
            stageHeight = lastStageHeight; //Update actual height for resize()
            isFullScreen = false;
            resizeWindow();

        } else { //make it fullScreen since it is not already fullScreen
            lastStageWidth = stageWidth;
            lastStageHeight = stageHeight;
            setLastXandY(stage.getX(), stage.getY());
            stage.setX(screenBounds.getMinX());
            stage.setY(screenBounds.getMinY());
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());
            stageWidth = screenBounds.getWidth(); //Update width for resize()
            stageHeight = screenBounds.getHeight(); //Update height for resize()
            isFullScreen = true;
            resizeWindow();
        }

    }

    public static void resizeWindow() {

        Resizable[] resizables = MainScreen.getResizables();
        for (Resizable resizablePane : resizables) {
            resizablePane.resize();
        }

    }

    public static Stage getStage() {
        return stage;
    }

    public static double getStageWidth() {
        return stageWidth - 50;
    }

    public static double getStageHeight() {
        return stageHeight - 50;
    }

    public static void setLastXandY(double x, double y) {
        lastX = y;
        lastY = y;
    }

}
