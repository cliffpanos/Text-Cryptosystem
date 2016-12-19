package view;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


public abstract class InputMenu {

    protected VBox menu;
    protected static TextField inputField = new TextField();
    protected static TextField outputField = new TextField();

    public InputMenu() {

        menu = new VBox(10);
        menu.setMaxWidth((double) MainScreen.getStageWidth() * 0.57);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);

        inputField.setPrefWidth(menu.getWidth() - 20);
        inputField.setAlignment(Pos.TOP_LEFT);
        //inputField.getText().setWrappingWidth(inputField.getWidth() - 10);
        //TODO

        outputField.setPrefWidth(menu.getWidth() - 20);
        outputField.setAlignment(Pos.TOP_LEFT);

        menu.getChildren().addAll(inputField, outputField);
    }

    public VBox getRootNode() {
        return this.menu;
    }

}
