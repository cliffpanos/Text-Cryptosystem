package view;

import runner.Runner;

import javafx.scene.layout.BorderPane;

public class MainScreen extends BorderPane {

    private static BorderPane innerBorderPane = new BorderPane();
    private static EncryptDecryptMenu edMenu = new EncryptDecryptMenu();
    private static InputMenu inputOEMenu = new InputOnEncryptMenu();

    public MainScreen() {

        this.setLeft(edMenu.getRootNode());
        this.setCenter(innerBorderPane);
        // innerBorderPane.setLeft(new GetContentMenu());
        innerBorderPane.setCenter(inputOEMenu.getRootNode());
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
