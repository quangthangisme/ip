package mightyduck.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import mightyduck.MightyDuck;
import mightyduck.command.CommandResult;
import mightyduck.command.CommandResultType;
import mightyduck.data.task.Task;
import mightyduck.exception.InvalidStoragePathException;
import mightyduck.exception.StorageLoadException;
import mightyduck.utils.Pair;

/**
 * Controller for the main GUI.
 */
public class MainController extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private MightyDuck mightyDuck;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image duckImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    /**
     * Default constructor.
     */
    public MainController() {
    }

    /**
     * Initializes the dialog container to automatically scroll to the bottom whenever new content
     * is added.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Initializes the MightyDuck instance and handles errors.
     */
    public void initializeMightyDuck() {
        try {
            mightyDuck = new MightyDuck();
        } catch (InvalidStoragePathException | StorageLoadException e) {
            showErrorAlert("Initialization Error", e.getMessage());
            Platform.runLater(() -> System.exit(0));
        }
    }

    /**
     * Displays an error alert with the given error message.
     */
    private void showErrorAlert(String headerText, String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(headerText);
        alert.setContentText(errorMessage);

        alert.showAndWait();
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and
     * then appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        dialogContainer.getChildren().add(
                DialogBoxController.getUserDialog(input, userImage)
        );
        CommandResult commandResult = mightyDuck.runCommand(input);
        if (commandResult.commandResultType() == CommandResultType.TERMINATING_ERROR) {
            showErrorAlert("Runtime Error", commandResult.feedback());
            Platform.runLater(() -> System.exit(0));
        }
        StringBuilder response = new StringBuilder();
        response.append(commandResult.feedback()).append("\n");
        for (Pair<Integer, Task> taskPair : commandResult.tasks()) {
            response.append("\t").append(taskPair.index() + 1).append(". ")
                    .append(taskPair.element()).append("\n");
        }
        dialogContainer.getChildren().add(
                DialogBoxController.getDuckDialog(response.toString(), duckImage)
        );
        userInput.clear();
        if (commandResult.commandResultType() == CommandResultType.TERMINATION) {
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() -> System.exit(0));
            }).start();
        }
    }
}
