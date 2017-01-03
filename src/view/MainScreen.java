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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;


public class MainScreen extends BorderPane {

    private static BorderPane innerBorderPane = new BorderPane();
    private static OptionsMenu optionsMenu = new OptionsMenu();
    private static EncryptDecryptMenu edMenu = new EncryptDecryptMenu();
    private static InputMenu inputODMenu = new InputOnDecryptMenu();
    private static InputMenu inputOEMenu = new InputOnEncryptMenu();
    private static FilePane filePane = new FilePane();
    private static FolderPane folderPane = new FolderPane();

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
        innerBorderPane.setBorder(new Border(new BorderStroke(Color
            .web("#B3B3B3"), Color.web("#B3B3B3"), Color.web("#B3B3B3"), Color
            .TRANSPARENT, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
            BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
            new CornerRadii(0.0, 5.0, 5.0, 0.0, false), new BorderWidths(0.4),
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

        switch (option) {
        case INPUTTEXT :
            if (isEncryptingNotDecrypting) {
                innerBorderPane.setCenter(inputOEMenu.getRootNode());
            } else {
                innerBorderPane.setCenter(inputODMenu.getRootNode());
            }
            //if EncryptDecryptMenu's Encrypt button is pressed, set the
            //right pane to be the inputOEMenu, otherwise the inputODMenu
            selectedMenu = MenuOptions.INPUTTEXT;
            break;
        case CHOOSEFILE :
            innerBorderPane.setCenter(filePane);
            selectedMenu = MenuOptions.CHOOSEFILE;
            break;
        case CHOOSEFOLDER :
            innerBorderPane.setCenter(folderPane);
            selectedMenu = MenuOptions.CHOOSEFOLDER;
            break;
        }

    }

    public static InputOnEncryptMenu getInputOEMenu() {
        return (InputOnEncryptMenu) inputOEMenu;
    }

    public static InputOnDecryptMenu getInputODMenu() {
        return (InputOnDecryptMenu) inputODMenu;
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

    public static Resizable[] getResizables() {

        Resizable[] resizables = {(Resizable) edMenu, (Resizable) optionsMenu,
            (Resizable) inputOEMenu, (Resizable) inputODMenu,
            (Resizable) filePane, (Resizable) folderPane};

        return resizables;
    }

}
