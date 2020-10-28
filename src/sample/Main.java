package sample;

import com.sun.prism.Image;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.FileInputStream;


public class Main extends Application {
    Stage window;

    Scene mainScene;
    static DictionaryCommandline dict = new DictionaryCommandline();
    ListView<String> englishWords;
    ListView<String> vietnameseWords;


    public static void main(String[] args) throws Exception {
        dict.insertFile();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("DichTionary");
        Label choseWord = new Label("0");


        //handle action on this button.
        BorderPane secondBorderPane = new BorderPane();

        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(Color.rgb(250, 252, 194), CornerRadii.EMPTY, Insets.EMPTY)));

        //name input
        Label savedLookedUp = new Label();
        Label savedMeaning = new Label();

        TextField lookUpWord = new TextField();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);
        gridPane.setGridLinesVisible(true);

        HBox topBox = new HBox();
        topBox.setPadding(new Insets(10, 10, 10, 10));
        topBox.setBackground(new Background(new BackgroundFill(Color.rgb(204, 246, 200), CornerRadii.EMPTY, Insets.EMPTY)));
        Label topBoxMessage = new Label("DICTIONARY");
        Font font = new Font("Arial", 20);
        topBoxMessage.setFont(font);
        topBox.getChildren().addAll(topBoxMessage);
        //GridPane.setConstraints(topBox, 0, 0);
        borderPane.setTop(topBox);

        VBox leftBox = new VBox();
        leftBox.setBackground(new Background(new BackgroundFill(Color.rgb(246, 214, 173), CornerRadii.EMPTY, Insets.EMPTY)));
        leftBox.setAlignment(Pos.CENTER);
        leftBox.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setLeft(leftBox);

        //List view;
        englishWords = new ListView<>();
        vietnameseWords = new ListView<>();
        englishWords.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println((String) englishWords.getSelectionModel().getSelectedItem());
                lookUpWord.setText((String) englishWords.getSelectionModel().getSelectedItem());
                choseWord.setText(String.valueOf(englishWords.getSelectionModel().getSelectedIndex()));
            }
        });

        dict.getWords(englishWords, vietnameseWords);
        GridPane.setConstraints(englishWords, 0, 1);
        GridPane.setConstraints(vietnameseWords, 1, 1);


        lookUpWord.setPromptText("Look up Word");
        lookUpWord.setOnKeyPressed(new EventHandler<KeyEvent>() {
            //boolean checkEmpty = false;
            Label typedWord = new Label();
            @Override
            public void handle(KeyEvent keyEvent) {

                typedWord.setText(lookUpWord.getText() + keyEvent.getText());
                //System.out.println(lookUpWord.getText());
                if (typedWord.getText() == "" ) {
                    englishWords.getItems().clear();
                    vietnameseWords.getItems().clear();
                    dict.getWords(englishWords, vietnameseWords);
                } else {
                    englishWords.getItems().clear();
                    vietnameseWords.getItems().clear();
                    dict.getWords(englishWords, vietnameseWords, typedWord.getText());
                }
            }
        });

        GridPane.setConstraints(lookUpWord, 0, 0);

        Button findButton = new Button("Find");
        findButton.setOnAction(e -> {
            try {
                //System.out.println(lookUpWord.getText());
                boolean found = false;
                found = dict.dictionaryGUI(lookUpWord.getText(), found);
                savedLookedUp.setText(lookUpWord.getText());
                savedMeaning.setText(vietnameseWords.getItems().get(Integer.parseInt(choseWord.getText())));
                if(!found) {
                    AlertBox.display("Error", "Cannot find " + lookUpWord.getText());
                } else {
                    mainScene.setRoot(secondBorderPane);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        });
        findButton.setAlignment(Pos.TOP_LEFT);

        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            if(AddBox.display("Adding Word", dict.getDictMan())) {
                englishWords.getItems().clear();
                vietnameseWords.getItems().clear();
                dict.getWords(englishWords, vietnameseWords);
            }
        });
        addButton.setAlignment(Pos.CENTER);

        Button delete = new Button("Delete");
        delete.setOnAction(event -> {
            DeleteBox.display("Delete Word", dict.getDictMan());
            englishWords.getItems().clear();
            vietnameseWords.getItems().clear();
            dict.getWords(englishWords, vietnameseWords);
        });
        addButton.setAlignment(Pos.TOP_RIGHT);

        Button update = new Button("Update");
        update.setOnAction(event -> {
            boolean found = false;
            if(lookUpWord.getText() == null){
                AlertBox.display("Error", "You didn't type anything.");
            } else {
                found = dict.dictionaryGUI(lookUpWord.getText(), found);
                if(!found) {
                    AlertBox.display("Error", "Cannot find " + lookUpWord.getText());

                } else {
                    UpdateBox.display("Update Word", dict.getDictMan(), lookUpWord.getText());
                }
                englishWords.getItems().clear();
                vietnameseWords.getItems().clear();
                dict.getWords(englishWords, vietnameseWords);
            }

        });
        addButton.setAlignment(Pos.CENTER_RIGHT);

        HBox buttonBox = new HBox(10);
        buttonBox.setPrefSize(lookUpWord.getWidth(), lookUpWord.getHeight());
        buttonBox.getChildren().addAll(findButton, addButton, delete, update);
        GridPane.setConstraints(buttonBox, 1 , 0);

        gridPane.getChildren().addAll(buttonBox, lookUpWord, englishWords, vietnameseWords);
        gridPane.setAlignment(Pos.CENTER);
        borderPane.setCenter(gridPane);

        secondBorderPane.setBackground(new Background(new BackgroundFill(Color.rgb(250, 252, 194), CornerRadii.EMPTY, Insets.EMPTY)));

        GridPane secondGridPane = new GridPane();
        secondGridPane.setPadding(new Insets(10, 10, 10, 10));
        secondGridPane.setVgap(8);
        secondGridPane.setHgap(10);
        secondGridPane.setGridLinesVisible(true);

        Label englishWord = savedLookedUp;
        Font fontEnglishWord = new Font("Arial", 40);
        englishWord.setFont(fontEnglishWord);
        englishWord.setAlignment(Pos.TOP_LEFT);
        GridPane.setConstraints(englishWord, 0, 0);

        Label vietnameseWord = savedMeaning;
        Font vietnameseFont = new Font("Arial", 30);
        vietnameseWord.setFont(vietnameseFont);
        vietnameseWord.setAlignment(Pos.TOP_LEFT);
        GridPane.setConstraints(vietnameseWord, 0, 1);

        secondGridPane.getChildren().addAll(englishWord, vietnameseWord);

        Button backButton = new Button("Back");
        backButton.setAlignment(Pos.TOP_RIGHT);
        backButton.setOnAction(event -> {
            mainScene.setRoot(borderPane);
        });

        secondBorderPane.setTop(backButton);
        secondBorderPane.setCenter(secondGridPane);

        //scene2 = new Scene(secondBorderPane, 720, 600);
        //scene1 = new Scene(borderPane, 720, 600);
        mainScene = new Scene(borderPane, 720, 600);

        window.setScene(mainScene);
        window.show();
    }

    public void closeWindow() {
        boolean result = ConfirmBox.display("Exit", "Are you sure want to quit?");
        if(result) {
            window.close();
        }
    }

    public void saveString(String a, String b) {
        a = b;
        System.out.println(a);
    }
}
