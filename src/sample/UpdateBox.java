package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UpdateBox {
    public static void display(String title, DictionaryManagement dictionaryManagement, String wordToChange) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(310);

        GridPane updatePane = new GridPane();
        updatePane.setPadding(new Insets(10, 10, 10, 10));
        updatePane.setVgap(8);
        updatePane.setHgap(10);

        Label englishUpdateBox = new Label("English:");
        GridPane.setConstraints(englishUpdateBox, 0, 0);
        Label meaningBox = new Label("Meaning:");
        GridPane.setConstraints(meaningBox, 0, 1);
        Label notification = new Label();
        GridPane.setConstraints(notification, 1, 2);

        TextField englishUpdate = new TextField();
        englishUpdate.setText(wordToChange);
        GridPane.setConstraints(englishUpdate, 1, 0);

        TextField meaningUpdate = new TextField();
        meaningUpdate.setText(dictionaryManagement.getDictionary().findWord(wordToChange, true));
        GridPane.setConstraints(meaningUpdate, 1, 1);

        Button updateButton = new Button("Update");
        updateButton.setOnAction(event -> {
            dictionaryManagement.updateWord(wordToChange, englishUpdate.getText(), meaningUpdate.getText());
            notification.setTextFill(Color.GREEN);
            notification.setText("Updated!");
        });
        GridPane.setConstraints(updateButton, 2, 0);

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            window.close();
        });
        GridPane.setConstraints(backButton, 2, 1);

        updatePane.getChildren().addAll(englishUpdateBox, meaningBox, englishUpdate, meaningUpdate, backButton, updateButton, notification);
        Scene scene = new Scene(updatePane, 100, 100);
        window.setScene(scene);
        window.showAndWait();

    }
}
