package group27.landRegistration.utility;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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


    public <T> void loadWithData(String viewPath, ActionEvent event, ControllerConsumer<T> consumer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
            Parent root = loader.load();

            T controller = loader.getController();
            consumer.accept(controller);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    public interface ControllerConsumer<T> {
        void accept(T controller);
    }

}