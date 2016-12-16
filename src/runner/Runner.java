package runner;

import controller.Encryptor;
import view.MainScreen;

import javafx.scene.layout.StackPane;

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

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        stage = primaryStage;
        Scene startScene = new Scene(new MainScreen());
        stage.setTitle("Encryptor");
        stage.setScene(startScene);
        stage.initStyle(StageStyle.UNIFIED);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //set Stage boundaries to visible bounds of the main screen
        //stage.setX(primaryScreenBounds.getMinX());
        //stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth((double) primaryScreenBounds.getWidth() * 2.0 / 3.0);
        stage.setHeight((double) primaryScreenBounds.getHeight() * 2.0 / 3.0);

        stage.toFront();
        stage.setResizable(false);
        stage.show();

        //do {
        Encryptor.startEncryptor();
        //} while (true); //Ends the do-while loop

    }

    public static Stage getStage() {
        return stage;
    }

    public static double getStageWidth() {
        return stage.getWidth();
    }

    public static double getStageHeight() {
        return stage.getHeight();
    }

}
