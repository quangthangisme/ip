package mightyduck;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mightyduck.controller.MainController;

/**
 * A GUI for MightyDuck using FXML.
 */
public class Main extends Application {

    private static final String VIEW_PATH = "/view/MainView.fxml";
    private static final int MIN_WIDTH = 400;
    private static final int MIN_LENGTH = 600;

    /**
     * Default constructor.
     */
    public Main() {
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(VIEW_PATH));
        AnchorPane ap = fxmlLoader.load();
        Scene scene = new Scene(ap, MIN_WIDTH, MIN_LENGTH);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_LENGTH);
        stage.show();
        MainController controller = fxmlLoader.getController();
        controller.initializeMightyDuck();
    }
}
