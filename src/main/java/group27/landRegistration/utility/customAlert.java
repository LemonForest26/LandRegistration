package group27.landRegistration.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class customAlert {

    public void message(Alert.AlertType a, String title, String headText, String message) {
        Alert alert = new Alert(a);
        alert.setTitle(title);
        alert.setHeaderText(headText);
        alert.setContentText(message);
        alert.showAndWait();

    }
}
