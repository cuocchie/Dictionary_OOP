package sample;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;

public class AddBox {
    static boolean added;

    public static boolean display(String title, DictionaryManagement dictionaryManagement) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        GridPane addGridPane = new GridPane();
        addGridPane.setPadding(new Insets(10, 10, 10, 10));
        addGridPane.setVgap(8);
        addGridPane.setHgap(10);

        TextField addingWord = new TextField();
        addingWord.setPromptText("Word to Add");
        GridPane.setConstraints(addingWord, 0, 0);
//        addingWord.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            Label typedWord = new Label();
//
//            @Override
//            public void handle(KeyEvent keyEvent) {
//                typedWord.setText(addingWord.getText() + keyEvent.getText());
//            }
//        });

        TextField addingMeaning = new TextField();
        addingMeaning.setPromptText("Meaning");
        GridPane.setConstraints(addingMeaning, 1, 0);

        //Create two buttons
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            System.out.println(addingWord.getText());
            dictionaryManagement.addWord(addingWord.getText(), addingMeaning.getText());
            added = true;
            window.close();
        });
        GridPane.setConstraints(addButton, 0, 1);

        Button quitButton = new Button("Back");
        quitButton.setOnAction(e -> {
            added = false;
            window.close();
        });
        GridPane.setConstraints(quitButton, 1, 1);

        addGridPane.setAlignment(Pos.CENTER);
        addGridPane.getChildren().addAll(addButton, quitButton, addingMeaning, addingWord);

        Scene scene = new Scene(addGridPane, 100, 100);
        window.setScene(scene);
        window.showAndWait();

        return added;
    }
}
