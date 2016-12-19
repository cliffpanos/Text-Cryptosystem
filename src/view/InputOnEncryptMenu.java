package view;

import controller.Encryptor;

public class InputOnEncryptMenu extends InputMenu {

    private String outputtedEncryptedText;

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
                }
            });
    }

    public static String getInputFieldText() {
        return inputField.getText();
    }

}
