package view;

import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


public abstract class InputMenu {

    protected VBox menu;
    protected static TextArea inputField = new TextArea();
    protected static TextArea outputField = new TextArea();
    protected static UIButton runButton = new UIButton(60, 25, "setInSubclass");

    public InputMenu() {

        menu = new VBox(10);
        menu.setMaxWidth((double) MainScreen.getStageWidth() * 0.57);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);

        inputField.setPrefWidth(menu.getWidth() - 20);
        outputField.setPrefWidth(menu.getWidth() - 20);
        inputField.setWrapText(true);
        outputField.setWrapText(true);

        menu.getChildren().addAll(inputField, runButton, outputField);
    }

    public VBox getRootNode() {
        return this.menu;
    }

}
