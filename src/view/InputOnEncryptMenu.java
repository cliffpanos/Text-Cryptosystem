package view;

import controller.Encryptor;
import resources.Resources;

public class InputOnEncryptMenu extends InputMenu {

    public InputOnEncryptMenu() {

        double stageHeight = MainScreen.getStageHeight();
        inputField.setMinHeight(((double) stageHeight * 2.0 / 3.0) - 50);
        inputField.setPromptText("Copy or type text here to be encrypted");

        outputField.setMinHeight(((double) stageHeight * 1.0 / 3.0) - 50);
        outputField.setPromptText("The encrypted text will appear here");

        runButton.getLabel().setText("Encrypt");

        runButton.getClickable().setOnMouseClicked(e -> {
                Encryptor.setText(inputField.getText());
                String output = Encryptor.run();
                if (output != null) {
                    outputField.setText(output);
                    Resources.playSound("encryptionComplete.aiff");
                }
            });
    }

    public String getInputFieldText() {
        return inputField.getText();
    }

}
