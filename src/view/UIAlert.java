package view;

import resources.Resources;

import javafx.scene.control.Alert;

public class UIAlert {

    public static void show(String title, String primaryText,
        Alert.AlertType alertType) {

        Alert newAlert = new Alert(alertType);
        newAlert.setTitle(title);
        newAlert.setHeaderText(primaryText);

        if (alertType == Alert.AlertType.ERROR) {
            Resources.playSound("errorAlert.aiff");
        }

        newAlert.showAndWait();
    }
}
