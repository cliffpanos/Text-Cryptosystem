package view;

import javafx.scene.layout.BorderPane;

public class MainScreen extends BorderPane {

    private static BorderPane innerBorderPane = new BorderPane();
    private static EncryptDecryptMenu edMenu = new EncryptDecryptMenu();

    public MainScreen() {
        this.getStylesheets().add(this.getClass()
        .getResource("mainScreenStyle.css").toExternalForm());

        //TODO: set up innerBorderPAne
        // innerBorderPane.setLeft(new GetContentMenu());
        // innerBorderPane.setCenter(CypherPanel)

        this.setLeft(edMenu);
        this.setCenter(innerBorderPane);
    }

    public static void switchMenu() {

    }

}
