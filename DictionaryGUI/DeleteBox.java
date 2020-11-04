package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DeleteBox {
    static boolean deleted = false;

    public static boolean display(String title, DictionaryManagement dictionaryManagement) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label wordToDelete = new Label();
        Label warning = new Label();

        GridPane deletePane = new GridPane();
        deletePane.setPadding(new Insets(10, 10, 10, 10));
        deletePane.setVgap(8);
        deletePane.setHgap(10);

        GridPane.setConstraints(warning, 0, 1);

        TextField deleteWord = new TextField();
        deleteWord.setPromptText("Delete Word");
        GridPane.setConstraints(deleteWord, 0, 0);

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(dictionaryManagement.deleteWord(deleteWord.getText())) {
                   warning.setText("Deleted " + deleteWord.getText());
                   warning.setTextFill(Color.GREEN);
                   deleted = true;
                } else {
                    warning.setText("Not found " + deleteWord.getText());
                    warning.setTextFill(Color.RED);
                }
            }
        });
        GridPane.setConstraints(deleteButton, 1, 0);

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            window.close();
        });
        GridPane.setConstraints(backButton, 1, 1);

        deletePane.getChildren().addAll(backButton, deleteWord, deleteButton, warning);
        Scene scene = new Scene(deletePane, 100, 100);
        window.setScene(scene);
        window.showAndWait();

        return deleted;
    }

}
