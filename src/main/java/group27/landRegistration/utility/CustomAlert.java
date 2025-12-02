package group27.landRegistration.utility;

import javafx.scene.control.Alert;
//import javafx.scene.control.ButtonType;

public class CustomAlert {

    public static void show(Alert.AlertType type, String title, String header, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

