package view;

import runner.Runner;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;

public class MainScreen extends BorderPane {

    private static BorderPane innerBorderPane = new BorderPane();
    private static FilePane filePane = new FilePane();
    private static EncryptDecryptMenu edMenu = new EncryptDecryptMenu();
    private static InputOnDecryptMenu inputODMenu = new InputOnDecryptMenu();
    private static InputOnEncryptMenu inputOEMenu = new InputOnEncryptMenu();
    private static OptionsMenu optionsMenu = new OptionsMenu();

    private static boolean isEncryptingNotDecrypting = true;
    private static MenuOptions selectedMenu = MenuOptions.INPUTTEXT;


    public MainScreen() {

        this.setBackground(null);
        this.setLeft(edMenu);

        /*this.setStyle(
                "-fx-effect: dropshadow(gaussian, black, " + 70 + ", 0, 0, 0);"
        );*/
        //This shadow adding is not working

        innerBorderPane.setLeft(optionsMenu);
        innerBorderPane.setCenter(inputOEMenu.getRootNode());

        innerBorderPane.setBackground(new Background(new BackgroundFill(Color
            .web("#F2F3F4", 1.0), new CornerRadii(0.0, 5.0, 5.0, 0.0, false),
            new Insets(0))));
        this.setCenter(innerBorderPane);

    }

    public enum MenuOptions {
        INPUTTEXT, CHOOSEFILE, CHOOSEFOLDER;
    }

    public static void setIsEncryptingNotDecrypting(boolean areWeEncrypting) {
        isEncryptingNotDecrypting = areWeEncrypting;
        if (selectedMenu == MenuOptions.INPUTTEXT) {
            switchMenu(MenuOptions.INPUTTEXT);
        }
    }

    public static void switchMenu(MenuOptions option) {

        innerBorderPane.setCenter(null);
        System.out.println("SwitchMenu called!");

        switch (option) {
        case INPUTTEXT :
            if (isEncryptingNotDecrypting) {
                System.out.println("Encrypting");
                innerBorderPane.setCenter(inputOEMenu.getRootNode());
            } else {
                System.out.println("Decrypting");
                innerBorderPane.setCenter(inputODMenu.getRootNode());
            }
            //if EncryptDecryptMenu's Encrypt button is pressed, set the
            //right pane to be the inputOEMenu, otherwise the inputODMenu
            System.out.println("Input Text Menu & " + isEncryptingNotDecrypting);
            selectedMenu = MenuOptions.INPUTTEXT;
            break;
        case CHOOSEFILE :
            innerBorderPane.setCenter(filePane);
            selectedMenu = MenuOptions.CHOOSEFILE;
            break;
        case CHOOSEFOLDER :
            System.out.println("implement this to show folder choosing");
            selectedMenu = MenuOptions.CHOOSEFOLDER;
            break;
        }

    }


    public static InputOnDecryptMenu getInputODMenu() {
        return inputODMenu;
    }

    public static InputOnEncryptMenu getInputOEMenu() {
        return inputOEMenu;
    }

    public static boolean getIsEncryptingNotDecrypting() {
        return isEncryptingNotDecrypting;
    }

    public static boolean isInputtingDirectly() {
        return selectedMenu == MenuOptions.INPUTTEXT;
    }

    public static double getStageHeight() {
        return Runner.getStageHeight();
    }

    public static double getStageWidth() {
        return Runner.getStageWidth();
    }

}
