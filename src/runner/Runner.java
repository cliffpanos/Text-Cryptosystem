/*import javafx.scene.layout.StackPane;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D; */

import java.util.Scanner;

public class Runner /*extends Application*/ {


    public static void main(String[] args) {
        //launch(args);

        do {
            Encryptor.startEncryptor();
         } while (true); //Ends the do-while loop
    }

    /*
    private static Stage stage;

    public void start(Stage primaryStage) {

        stage = primaryStage;
        Scene startScene = new Scene(new StackPane());
        stage.setTitle("Encryptor");
        stage.setScene(startScene);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //set Stage boundaries to visible bounds of the main screen
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());

        stage.toFront();
        stage.setResizable(false);
        stage.show();

    } */

}