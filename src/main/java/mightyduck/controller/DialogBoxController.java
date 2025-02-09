package mightyduck.controller;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face and a label
 * containing text from the speaker.
 */
public class DialogBoxController extends HBox {
    private static final String VIEW_PATH = "/view/DialogBoxView.fxml";

    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

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
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a dialog box for the user with the specified text and image.
     *
     * @param text The text to be displayed in the dialog box.
     * @param img  The image to be displayed as the user's avatar in the dialog box.
     * @return A new {@code DialogBoxController} representing the user's dialog.
     */
    public static DialogBoxController getUserDialog(String text, Image img) {
        return new DialogBoxController(text, img);
    }

    /**
     * Creates a dialog box for {@code MightyDuck} with the specified text and image.
     *
     * @param text The text to be displayed in the dialog box.
     * @param img  The image to be displayed as {@code MightyDuck}'s avatar in the dialog box.
     * @return A new {@code DialogBoxController} representing {@code MightyDuck}r's dialog.
     */
    public static DialogBoxController getDuckDialog(String text, Image img) {
        DialogBoxController db = new DialogBoxController(text, img);
        db.flip();
        return db;
    }
}
