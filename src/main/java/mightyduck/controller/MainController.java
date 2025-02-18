package mightyduck.controller;

import java.util.Objects;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import mightyduck.MightyDuck;
import mightyduck.command.CommandResult;
import mightyduck.command.CommandResultType;
import mightyduck.exception.InvalidStoragePathException;
import mightyduck.exception.StorageLoadException;
import mightyduck.utils.Messages;

/**
 * Controller for the main GUI.
 */
public class MainController extends AnchorPane {

    private static final String ERROR_TITLE = "Error";
    private static final String INITIALIZATION_ERROR_HEADER = "Initialization Error";
    private static final String RUNTIME_ERROR_HEADER = "Runtime Error";
    private static final String USER_IMAGE_PATH = "/images/user.png";
    private static final String DUCK_IMAGE_PATH = "/images/duck.png";

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private MightyDuck mightyDuck;

    private final Image userImage = new Image(Objects.requireNonNull(
            this.getClass().getResourceAsStream(USER_IMAGE_PATH)));
    private final Image duckImage = new Image(Objects.requireNonNull(
            this.getClass().getResourceAsStream(DUCK_IMAGE_PATH)));

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
        assert scrollPane != null : "ScrollPane is not initialized!";
        assert dialogContainer != null : "DialogContainer is not initialized!";
        assert userInput != null : "UserInput field is not initialized!";
        assert sendButton != null : "SendButton is not initialized!";

        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        VBox.setVgrow(dialogContainer, Priority.ALWAYS);
    }

    /**
     * Initializes the MightyDuck instance and handles errors.
     */
    public void initializeMightyDuck() {
        try {
            mightyDuck = new MightyDuck();
        } catch (InvalidStoragePathException | StorageLoadException e) {
            showErrorAlert(INITIALIZATION_ERROR_HEADER, e.getMessage());
            Platform.runLater(() -> System.exit(0));
        }
        dialogContainer.getChildren().add(
                DialogBoxController.getDialog(Messages.WELCOME, duckImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and
     * then appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        assert mightyDuck != null : "MightyDuck instance is not initialized!";

        String input = userInput.getText();
        addUserDialog(input);
        processCommand(input);
        userInput.clear();
    }

    /**
     * Adds a dialog box for the user with the specified input.
     *
     * @param input The input from the user.
     */
    private void addUserDialog(String input) {
        dialogContainer.getChildren().add(
                DialogBoxController.getDialog(input, userImage)
        );
    }

    /**
     * Processes the command entered by the user and updates the dialog container with the
     * response.
     *
     * @param input The command entered by the user.
     */
    private void processCommand(String input) {
        CommandResult commandResult = mightyDuck.runCommand(input);

        if (commandResult.commandResultType() == CommandResultType.TERMINATING_ERROR) {
            showErrorAlert(RUNTIME_ERROR_HEADER, commandResult.feedback());
            exitApplication();
        }

        addDuckDialog(commandResult);

        if (commandResult.commandResultType() == CommandResultType.TERMINATION) {
            userInput.setDisable(true);
            sendButton.setDisable(true);
        }
    }

    /**
     * Creates and adds a dialog box for the duck with the command result feedback and tasks.
     */
    private void addDuckDialog(CommandResult commandResult) {
        DialogBoxController dialogBox = DialogBoxController.getDialog(commandResult.feedback(),
                duckImage, commandResult.tasks());
        if (commandResult.commandResultType() == CommandResultType.HELP) {
            dialogBox.addGuideLink();
        }
        if (commandResult.commandResultType() == CommandResultType.ERROR) {
            dialogBox.setErrorDialog();
        }
        dialogContainer.getChildren().add(dialogBox);
    }

    /**
     * Terminates the application after a short delay.
     */
    private void exitApplication() {
        Platform.runLater(() -> System.exit(0));
    }

    /**
     * Displays an error alert with the given error message.
     */
    private void showErrorAlert(String headerText, String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(ERROR_TITLE);
        alert.setHeaderText(headerText);
        alert.setContentText(errorMessage);

        alert.showAndWait();
    }
}
