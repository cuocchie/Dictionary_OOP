package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

import com.darkprograms.speech.synthesiser.SynthesiserV2;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class Main extends Application {
    Stage window;

    Scene mainScene;
    static DictionaryCommandline dict = new DictionaryCommandline();
    BorderPane borderPane = new BorderPane();
    BorderPane secondBorderPane = new BorderPane();
    TextField lookUpWord = new TextField();
    GridPane gridPane = new GridPane();
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

        borderPane.setBackground(new Background(new BackgroundFill(Color.rgb(250, 252, 194), CornerRadii.EMPTY, Insets.EMPTY)));

        //name input
        Label savedLookedUp = new Label();
        Label savedMeaning = new Label();
        initGridPane(gridPane);

        HBox topBox = new HBox();
        initTopBox(topBox, borderPane);

        //List view;
        englishWords = new ListView<>();
        vietnameseWords = new ListView<>();
        initList(lookUpWord, choseWord);

        lookUpWord.setPromptText("Look up Word");
        lookUpWord.setOnKeyPressed(new EventHandler<KeyEvent>() {
            //boolean checkEmpty = false;
            Label typedWord = new Label();
            @Override
            public void handle(KeyEvent keyEvent) {

                typedWord.setText(lookUpWord.getText() + keyEvent.getText());
                //System.out.println(lookUpWord.getText());
                if (typedWord.getText() == "" ) {
                    resetList();
                } else {
                    resetList(typedWord.getText());
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
            AddBox.display("Adding Word", dict.getDictMan());
            resetList();
        });
        addButton.setAlignment(Pos.CENTER);

        Button delete = new Button("Delete");
        delete.setOnAction(event -> {
            DeleteBox.display("Delete Word", dict.getDictMan());
            resetList();
        });
        delete.setAlignment(Pos.TOP_RIGHT);

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
                resetList();
            }

        });

        HBox buttonBox = new HBox(10);
        buttonBox.setPrefSize(lookUpWord.getWidth(), lookUpWord.getHeight());
        buttonBox.getChildren().addAll(findButton, addButton, delete, update);
        GridPane.setConstraints(buttonBox, 1 , 0);

        gridPane.getChildren().addAll(buttonBox, lookUpWord, englishWords, vietnameseWords);
        gridPane.setAlignment(Pos.CENTER);
        borderPane.setCenter(gridPane);
        borderPane.setTop(topBox);

        secondBorderPane.setBackground(new Background(new BackgroundFill(Color.rgb(250, 252, 194), CornerRadii.EMPTY, Insets.EMPTY)));

        GridPane secondGridPane = new GridPane();
        secondGridPane.setPadding(new Insets(10, 10, 10, 10));
        secondGridPane.setVgap(8);
        secondGridPane.setHgap(10);


        Label englishWord = savedLookedUp;
        initLabel(englishWord, 60, 0);

        Label vietnameseWord = savedMeaning;
        initLabel(vietnameseWord, 30, 1);

        Button speakButton = new Button("Speak!");
        speakButton.setOnAction(event -> speak(englishWord.getText()));
        GridPane.setConstraints(speakButton, 1, 0);

        secondGridPane.getChildren().addAll(englishWord, vietnameseWord, speakButton);


        Button backButton = new Button("Back");
        backButton.setAlignment(Pos.TOP_RIGHT);
        backButton.setOnAction(event -> {
            mainScene.setRoot(borderPane);
        });

        HBox secondTopBox = new HBox();
        initTopBox(secondTopBox, borderPane);
        secondTopBox.setSpacing(500);
        secondTopBox.getChildren().add(backButton);

        secondBorderPane.setTop(secondTopBox);
        secondBorderPane.setCenter(secondGridPane);

        //scene2 = new Scene(secondBorderPane, 720, 600);
        //scene1 = new Scene(borderPane, 720, 600);
        mainScene = new Scene(borderPane, 720, 600);

        window.setOnCloseRequest(windowEvent -> {
            try {
                windowEvent.consume();
                closeWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        window.setScene(mainScene);
        window.show();
    }

    public void closeWindow() throws Exception {
        boolean result = ConfirmBox.display("Exit", "Are you sure want to quit?");
        if(result) {
            dict.getDictMan().dictionaryExportToFile();
            window.close();
        }
    }

    public void resetList() {
        englishWords.getItems().clear();
        vietnameseWords.getItems().clear();
        dict.getWords(englishWords, vietnameseWords);
    }

    public void resetList(String subWord) {
        englishWords.getItems().clear();
        vietnameseWords.getItems().clear();
        dict.getWords(englishWords, vietnameseWords, subWord);
    }

    public void initGridPane(GridPane gridPane) {
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);
        //gridPane.setGridLinesVisible(true);
    }

    public void initTopBox(HBox topBox, BorderPane borderPane) {
        topBox.setPadding(new Insets(10, 10, 10, 10));
        topBox.setBackground(new Background(new BackgroundFill(Color.rgb(204, 246, 200), CornerRadii.EMPTY, Insets.EMPTY)));
        Label topBoxMessage = new Label("DICTIONARY");
        Font font = new Font("Arial", 20);
        topBoxMessage.setFont(font);
        topBox.getChildren().addAll(topBoxMessage);
    }

    public void initList(TextField lookUpWord, Label choseWord) {
        englishWords.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                lookUpWord.setText((String) englishWords.getSelectionModel().getSelectedItem());
                choseWord.setText(String.valueOf(englishWords.getSelectionModel().getSelectedIndex()));
            }
        });

        dict.getWords(englishWords, vietnameseWords);
        GridPane.setConstraints(englishWords, 0, 1);
        GridPane.setConstraints(vietnameseWords, 1, 1);
    }

    public void initLabel(Label word, int size, int pos) {
        Font font = new Font("Arial", size);
        word.setFont(font);
        word.setAlignment(Pos.TOP_LEFT);
        GridPane.setConstraints(word, 0, pos);
    }

    public void speak(String Word) {
        SynthesiserV2 synthesizer = new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");

        //Create a new Thread because JLayer is running on the current Thread and will make the application to lag
        Thread thread = new Thread(() -> {
            try {

                //Create a JLayer instance
                AdvancedPlayer player = new AdvancedPlayer(synthesizer.getMP3Data(Word));
                player.play();

                System.out.println("Successfully got back synthesizer data");

            } catch (IOException | JavaLayerException e) {

                e.printStackTrace(); //Print the exception ( we want to know , not hide below our finger , like many developers do...)

            }
        });

        //We don't want the application to terminate before this Thread terminates
        thread.setDaemon(false);

        //Start the Thread
        thread.start();
    }
}
