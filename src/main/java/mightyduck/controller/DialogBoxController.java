package mightyduck.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import mightyduck.task.Task;
import mightyduck.utils.Config;
import mightyduck.utils.Pair;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face and a label
 * containing text from the speaker.
 */
public class DialogBoxController extends HBox {
    private static final String VIEW_PATH = "/view/DialogBoxView.fxml";
    private static final String ERROR_COLOR = "red";
    private static final String ERROR_TITLE = "Error";
    private static final String ERROR_GUIDE_LINK_HEADER = "Unable to open the guide";
    private static final String ERROR_GUIDE_LINK_CONTENT = "There was an error while trying to "
            + "open the guide. Please try again later.";


    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;
    @FXML
    private VBox container;

    /**
     * Creates a new {@code DialogBoxController} with the given text and image.
     *
     * @param text The text to be displayed in the dialog box.
     * @param img  The image to be displayed as the avatar in the dialog box.
     */
    private DialogBoxController(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    MainController.class.getResource(VIEW_PATH));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Error loading DialogBox FXML: " + e.getMessage(), e);
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Creates a dialog box with the specified text and image, and displays the tasks as a numbered
     * list.
     *
     * @param text     The text to be displayed in the dialog box.
     * @param img      The image to be displayed as the avatar in the dialog box.
     * @param taskList The list of tasks to be displayed as a numbered list.
     * @return A new {@code DialogBoxController} representing the dialog.
     */
    public static DialogBoxController getDialog(String text, Image img,
                                                List<Pair<Integer, Task>> taskList) {
        DialogBoxController dialogBox = new DialogBoxController(text, img);
        dialogBox.addNumberedTaskList(taskList);
        return dialogBox;
    }

    /**
     * Creates a dialog box with the specified text and image.
     *
     * @param text The text to be displayed in the dialog box.
     * @param img  The image to be displayed as the avatar in the dialog box.
     * @return A new {@code DialogBoxController} representing the dialog.
     */
    public static DialogBoxController getDialog(String text, Image img) {
        return new DialogBoxController(text, img);
    }

    /**
     * Adds a numbered list of tasks to the dialog box.
     *
     * @param taskList The list of tasks to be displayed.
     */
    public void addNumberedTaskList(List<Pair<Integer, Task>> taskList) {
        for (Pair<Integer, Task> taskPair : taskList) {
            HBox hbox = new HBox();
            Text numberText = new Text(taskPair.key() + 1 + ". ");
            TextFlow textFlow = new TextFlow();
            Text taskText = new Text(taskPair.value().toString());
            taskText.setWrappingWidth(hbox.getPrefWidth());
            textFlow.getChildren().add(taskText);
            hbox.getChildren().addAll(numberText, textFlow);
            container.getChildren().add(hbox);
        }
    }

    /**
     * Sets the color of the dialog text to error.
     */
    public void setErrorDialog() {
        dialog.setStyle("-fx-text-fill: " + ERROR_COLOR + ";");
    }

    /**
     * Adds a hyperlink to the guide to the dialog box.
     */
    public void addGuideLink() {
        Hyperlink guideLink = new Hyperlink(Config.GUIDE_LINK);
        guideLink.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(new URI(Config.GUIDE_LINK));
            } catch (IOException | URISyntaxException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle(ERROR_TITLE);
                errorAlert.setHeaderText(ERROR_GUIDE_LINK_HEADER);
                errorAlert.setContentText(ERROR_GUIDE_LINK_CONTENT);
                errorAlert.showAndWait();
            }
        });
        container.getChildren().add(guideLink);
    }
}
