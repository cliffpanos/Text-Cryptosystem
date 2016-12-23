package view;

public class InputOnEncryptMenu extends InputMenu {

    public InputOnEncryptMenu() {

        double stageHeight = MainScreen.getStageHeight();
        inputField.setPrefHeight(((double) stageHeight * 2.0 / 3.0)
            - heightDecrement);
        inputField.setPromptText("Copy or type text here to be encrypted");

        outputField.setPrefHeight(((double) stageHeight * 1.0 / 3.0)
            - heightDecrement);
        outputField.setPromptText("The encrypted text will appear here");

        runButton = new UIButton("encrypt.png", 100, 30, "Encrypt");

        completeSetUp(); //Finish runButton and add all to the VBox

    }

}
