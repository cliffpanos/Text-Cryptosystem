package view;

public class InputOnEncryptMenu extends InputMenu {

    public InputOnEncryptMenu() {

        inputField.setMinHeight(((double) menu.getHeight() * 2.0 / 3.0) - 20);
        inputField.setPromptText("Copy or type text here to be encrypted");

        outputField.setMinHeight(((double) menu.getHeight() * 1.0 / 3.0) - 20);
        outputField.setPromptText("The Encrypted Text will appear here");

    }



}
