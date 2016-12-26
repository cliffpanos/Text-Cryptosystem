package view;

public class InputOnEncryptMenu extends InputMenu {

    public InputOnEncryptMenu() {

        inputField.setPromptText("Paste or type text here to be encrypted");

        outputField.setPromptText("The encrypted text will appear here");

        runButton = new UIButton("encrypt.png", 100, 30, "Encrypt");

        completeSetUp(); //Finish runButton and add all to the VBox

        resize();

    }

    @Override
    public void resize() {

        super.resize();

        inputField.setPrefHeight(((double) stageHeight * 2.0 / 3.0)
            - heightDecrement);

        outputField.setPrefHeight(((double) stageHeight * 1.0 / 3.0)
            - heightDecrement);

    }

}
