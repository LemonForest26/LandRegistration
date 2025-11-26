module group27.landRegistration {
    requires javafx.controls;
    requires javafx.fxml;


    opens group27.landRegistration to javafx.fxml;
    exports group27.landRegistration;
    exports group27.landRegistration.controllers;
    opens group27.landRegistration.controllers to javafx.fxml;
}