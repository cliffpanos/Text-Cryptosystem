package view;

import resources.Resources;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;

public class UIAlert {

    public static void show(String title, String primaryText,
        Alert.AlertType alertType) {

        show(title, primaryText, alertType, false);

    }

    public static boolean show(String title, String primaryText,
        Alert.AlertType alertType, boolean specialAlert) {

        Alert newAlert = new Alert(alertType);
        newAlert.setTitle(title);
        newAlert.setHeaderText(primaryText);

        Resources.playSound("errorAlert.aiff");

        if (specialAlert) {
            ButtonType cancel = new ButtonType("Cancel",
                ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

            newAlert.getButtonTypes().setAll(cancel, ok);

            Optional<ButtonType> result = newAlert.showAndWait();

            if (result.get() == cancel) {
                return true;
            }
            if (result.get() == ok) {
                return false;
            }
        } else {
            newAlert.showAndWait();
        }

        return false;
    }
}
