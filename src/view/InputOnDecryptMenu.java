package view;

import controller.Encryptor;
import resources.Resources;

public class InputOnDecryptMenu extends InputMenu {

    public InputOnDecryptMenu() {

        double stageHeight = MainScreen.getStageHeight();
        inputField.setMinHeight(((double) stageHeight * 1.0 / 3.0) - 50);
        inputField.setPromptText("Copy or type text here to be decrypted");

        outputField.setMinHeight(((double) stageHeight * 2.0 / 3.0) - 50);
        outputField.setPromptText("The decrypted text will appear here");

        runButton.getLabel().setText("Decrypt");

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
