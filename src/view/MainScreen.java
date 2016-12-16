package view;

import javafx.scene.layout.BorderPane;

public class MainScreen extends BorderPane {

    private static BorderPane innerBorderPane = new BorderPane();
    private static EncryptDecryptMenu edmenu = new EncryptDecryptMenu();

    public MainScreen() {
        this.getStylesheets().add(this.getClass()
        .getResource("mainScreenStyle.css").toExternalForm());
    }

    public static void switchMenu() {

    }

}
