package group27.landRegistration.utility;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CurrentPageLoader {

    public void load(String viewPath, ActionEvent event) throws IOException {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(viewPath)));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

//        stage.setTitle("Land Registration of Bangladesh");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            //exception...
        }
    }
}