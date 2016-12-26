package view;

public class InputOnDecryptMenu extends InputMenu {

    public InputOnDecryptMenu() {

        inputField.setPromptText("Paste or type text here to be decrypted");

        outputField.setPromptText("The decrypted text will appear here");

        runButton = new UIButton("decrypt.png", 100, 30, "Decrypt");

        completeSetUp(); //Finish runButton and add all to the VBox

        resize();

    }

    @Override
    public void resize() {

        super.resize();

        inputField.setPrefHeight(((double) stageHeight * 1.0 / 3.0)
            - heightDecrement);

        outputField.setPrefHeight(((double) stageHeight * 2.0 / 3.0)
            - heightDecrement);

    }

}
