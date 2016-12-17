package view;

import runner.Runner;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;


public class MainScreen extends BorderPane {

    private static BorderPane innerBorderPane = new BorderPane();
    private static EncryptDecryptMenu edMenu = new EncryptDecryptMenu();
    private static InputMenu inputOEMenu = new InputOnEncryptMenu();

    public MainScreen() {

        this.setBackground(null);
        this.setLeft(edMenu);
        this.setCenter(innerBorderPane);
        
        //innerBorderPane.setLeft(new GetContentMenu());
        innerBorderPane.setCenter(inputOEMenu.getRootNode());
        innerBorderPane.setBackground(new Background(new BackgroundFill(Color
            .web("#F2F3F4", 1.0), new CornerRadii(0.0, 5.0, 5.0, 0.0, false),
            new Insets(0))));
    }

    public static void switchMenu() {

    }

    public static double getStageHeight() {
        return Runner.getStageHeight();
    }

    public static double getStageWidth() {
        return Runner.getStageWidth();
    }
}
