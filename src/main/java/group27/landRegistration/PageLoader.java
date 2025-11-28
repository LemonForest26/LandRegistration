package group27.landRegistration;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PageLoader {

    public void start(String fxmlFile, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(fxmlFile)
        );

        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Land Registration of Bangladesh");
        stage.setScene(scene);
        stage.show();
    }
}
