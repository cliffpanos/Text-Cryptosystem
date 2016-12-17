package view;

import javafx.scene.control.Alert;

public class UIAlert {

    public static void show(String title, String primaryText,
        Alert.AlertType alertType) {

        Alert newAlert = new Alert(alertType);
        newAlert.setTitle(title);
        newAlert.setHeaderText(primaryText);

        //Resources.playSound("INSERT HERE for confirmation or error alert");
        newAlert.showAndWait();
    }
}
