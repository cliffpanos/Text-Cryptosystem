package view;

import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;


public class EncryptDecryptMenu {

    private static VBox menu = new VBox(10);

    public EncryptDecryptMenu() {
        //change the height and width and arc arguments to be a percentage of something
        Rectangle whiteBackground = new Rectangle(75, 25,
            Color.web("#F7F7F7", 0.72));
        whiteBackground.setArcWidth(7);
        whiteBackground.setArcHeight(7);
    }

    public static VBox getRoodNode() {
        return menu;
    }

}
