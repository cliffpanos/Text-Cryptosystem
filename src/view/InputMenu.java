package view;

import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


public abstract class InputMenu {

    protected VBox menu;
    protected TextArea inputField = new TextArea();
    protected TextArea outputField = new TextArea();
    protected UIButton runButton = new UIButton(60, 25, "setInSubclass");

    public InputMenu() {

        menu = new VBox(10);
        menu.setPrefWidth((double) MainScreen.getStageWidth() * 0.57 - 40.0);
        menu.setMaxWidth((double) MainScreen.getStageWidth() * 0.57 - 40.0);
        System.out.println("InputMenu dims: " + menu.getWidth() + " x " + menu.getHeight());
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);
        menu.setFillWidth(false);

        inputField.setPrefWidth(MainScreen.getStageWidth() * 0.57 - 20.0);
        //inputField.setMinWidth(MainScreen.getStageWidth() * 0.57 - 20.0);
        outputField.setPrefWidth(MainScreen.getStageWidth() * 0.57 - 20.0);
        //outputField.setPrefWidth(MainScreen.getStageWidth() * 0.57 - 20.0);
        System.out.println("InputField dims are: " + inputField.getWidth() + " x " + inputField.getHeight());
        inputField.setWrapText(true);
        outputField.setWrapText(true);

        menu.getChildren().addAll(inputField, runButton, outputField);
        System.out.println("InputMenu dims again: " + menu.getWidth() + " x " + menu.getHeight());

    }

    public VBox getRootNode() {
        System.out.println(menu.getWidth());
        System.out.println(menu.getHeight());
        return this.menu;
    }

}
