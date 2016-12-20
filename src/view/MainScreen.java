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
    private static EncryptDecryptMenu edMenu = new EncryptDecryptMenu();
    private static InputOnEncryptMenu inputOEMenu = new InputOnEncryptMenu();
    private static InputOnDecryptMenu inputODMenu = new InputOnDecryptMenu();
    private static OptionsMenu optionsMenu = new OptionsMenu();
    private static FilePane filePane = new FilePane();

    private static VBox innerBorderPaneVBox = new VBox(inputOEMenu
        .getRootNode());
    private static boolean isEncryptingNotDecrypting = true;
    private static MenuOptions selectedMenu = MenuOptions.INPUTTEXT;


    public MainScreen() {

        this.setBackground(null);
        this.setLeft(edMenu);
        System.out.println("computed width: " + computePrefWidth(MainScreen.getStageHeight()));
        System.out.println("Stage width: " + (double) MainScreen.getStageWidth() * 0.57);
        innerBorderPaneVBox.setPrefWidth((double) MainScreen.getStageWidth() * 0.57 - 20.0);
        System.out.println("innerBorderPaneVBox dims: " + innerBorderPaneVBox.getWidth() + " x " + innerBorderPaneVBox.getHeight());

        /*this.setStyle(
                "-fx-effect: dropshadow(gaussian, black, " + 70 + ", 0, 0, 0);"
        );*/
        //This shadow adding is not working

        innerBorderPane.setLeft(optionsMenu);
        System.out.println("innerBorder dims: " + innerBorderPane.getWidth() + " x " + innerBorderPane.getHeight());
        innerBorderPane.setCenter(inputOEMenu.getRootNode());
        System.out.println("InputMenu dims: " + inputOEMenu.getRootNode().getWidth() + " x " + inputOEMenu.getRootNode().getHeight());

        innerBorderPane.setBackground(new Background(new BackgroundFill(Color
            .web("#F2F3F4", 1.0), new CornerRadii(0.0, 5.0, 5.0, 0.0, false),
            new Insets(0))));
        this.setCenter(innerBorderPane);
        System.out.println("computed width: " + this.computePrefWidth(MainScreen.getStageHeight()) * 0.57);
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
        VBox toAdd = new VBox();
        toAdd.setMinWidth(MainScreen.getStageWidth() * 0.57);
        switch (option) {
        case INPUTTEXT :
            if (isEncryptingNotDecrypting) {
                innerBorderPane.setCenter(inputOEMenu.getRootNode());
            } else {
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
        innerBorderPaneVBox.getChildren().setAll(toAdd);

    }

    public static double getStageHeight() {
        return Runner.getStageHeight();
    }

    public static double getStageWidth() {
        return Runner.getStageWidth();
    }
}
