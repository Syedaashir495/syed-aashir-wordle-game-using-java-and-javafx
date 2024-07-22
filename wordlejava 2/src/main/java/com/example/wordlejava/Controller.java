package com.example.wordlejava;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.util.ArrayList;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;



public class Controller {

    @FXML
    private Button enterButton;

    @FXML
    private GridPane wordGrid1;

    @FXML
    private GridPane wordGrid2;

    @FXML
    private GridPane wordGrid3;

    @FXML
    private GridPane wordGrid4;

    @FXML
    private GridPane wordGrid5;

    @FXML
    private GridPane wordGrid6;

    private final int MAX_WORD_SIZE = 5;
    private final int MAX_TRIES = 6;  //EQUIVALENT TO THE NUMBER OF ROWS SET

    private ArrayList<GridPane> wordGrids;

    GridPane currentGridpane;
    int currentGridPaneCount;
    String currentWord;

    Alert invalidInputAlert;
    Alert notAWordAlert;
    Alert winGameAlert;
    Alert loseGameAlert;

    String secretWord;

    WordUtil wordUtil;

    private final boolean IS_EASY_MODE = true; //Change me to update difficulty


    public Controller() {
        wordGrids = new ArrayList<>();

        currentGridPaneCount = 0;
        currentWord = "";


        wordUtil = new WordUtil(IS_EASY_MODE);
        secretWord = wordUtil.getRandomWord();
        System.out.println("Secret Word: " + secretWord); //DEBUGGING

        invalidInputAlert = new Alert(AlertType.ERROR);
        invalidInputAlert.setTitle("Invalid Input");
        invalidInputAlert.setContentText("Invalid input. Please make sure that every box has only one english letter per box.");

        notAWordAlert = new Alert(AlertType.ERROR);
        notAWordAlert.setTitle("Not A Word");
        notAWordAlert.setContentText("Invalid input. Not a word.");

        winGameAlert = new Alert(AlertType.CONFIRMATION);
        winGameAlert.setTitle("You win");
        winGameAlert.setContentText("Congratulations you won. The secret word was " + secretWord + ".");


        loseGameAlert = new Alert(AlertType.CONFIRMATION);
        loseGameAlert.setTitle("You loose");
        loseGameAlert.setContentText("Sorry you loose. The secret word was " + secretWord + ".");



    }

    @FXML
    void onEnterClick(ActionEvent event) {

        currentWord = "";
        String currentLetter = "";
        TextField currentTextField;

        for(int i=0; i < MAX_WORD_SIZE; i++){

            currentTextField = ((TextField)currentGridpane.getChildren().get(i));
            currentLetter = currentTextField.getText().toLowerCase().trim();

            //System.out.println("Current letter: " + currentLetter);

            if (isValidLetter(currentLetter)){
                currentWord += currentLetter;

            }

            else {
                invalidInputAlert.showAndWait();
                return;
            }

        }
        System.out.println("Current word input: " + currentWord);

        if(!wordUtil.isWord(currentWord)){
            notAWordAlert.showAndWait();
            return;
        }
        setColors();

        //Check if player wins game
        if(secretWord.equalsIgnoreCase(currentWord)){
            winGameAlert.showAndWait();
            resetGame();
            return;
        }

        //Check if player loses game(i.e. is in last row and guess is wrong)
        if (currentGridPaneCount == MAX_TRIES - 1){
            loseGameAlert.showAndWait();
            resetGame();
            return;
        }

        updateCurrentGridPane();

    }

    boolean isValidLetter(String letter){
        int len = letter.length();

        //System.out.println("Letter Length: " + len);
        if (len != 1)
            return false;

        int asciiValue =  letter.charAt(0);

        //System.out.println("Ascii value for: " + letter + "-> " + asciiValue);

        boolean isLowerCasedLetter =  asciiValue > 64 && asciiValue < 91 ;
        boolean isUpperCasedLetter =  asciiValue > 96 && asciiValue < 123;

        return isLowerCasedLetter || isUpperCasedLetter;
    }

    void setColors(){

        TextField currentTextField;
        String currentLetter;

        for(int i=0; i < MAX_WORD_SIZE; i++){

            currentTextField = ((TextField)currentGridpane.getChildren().get(i));
            currentLetter = currentTextField.getText().toLowerCase().trim();

            if (currentLetter.charAt(0) == secretWord.charAt(i))
                currentTextField.setStyle("-fx-background-color: green;");
            else if (secretWord.contains(currentLetter))
                currentTextField.setStyle("-fx-background-color: yellow;");
            else
                currentTextField.setStyle("-fx-background-color: #808080;");
        }
    }
    public void initialize() {

        wordGrids.add(wordGrid1);
        wordGrids.add(wordGrid2);
        wordGrids.add(wordGrid3);
        wordGrids.add(wordGrid4);
        wordGrids.add(wordGrid5);
        wordGrids.add(wordGrid6);

        currentGridpane = wordGrids.get(currentGridPaneCount);


    }


    void updateCurrentGridPane(){
        currentGridPaneCount++;
        // currentGridpane.setDisable(true);
        currentGridpane = wordGrids.get(currentGridPaneCount);
        currentGridpane.setDisable(false);
    }


    void printWordGrids(){
        for(GridPane pane : wordGrids){
            System.out.println(pane);
        }

        System.out.println("Current pane: " + currentGridpane);
    }


    void resetGame(){
        currentGridPaneCount = 0;
        currentWord = "";

        for(GridPane row : wordGrids){

            for(Node currentNode : row.getChildren()){
                //System.out.println("Evaluating: " + currentNode);
                ((TextField)currentNode).setText("");
                ((TextField)currentNode).setStyle("-fx-background-color: -fx-background; -fx-border-color: black;");
            }

            row.setDisable(true);
        }

        wordGrids.get(0).setDisable(false);
        secretWord = wordUtil.getRandomWord();
        System.out.println("Secret word: " + secretWord); //DEBUGGING

        winGameAlert.setContentText("Congratulations you won. The secret word was " + secretWord + ".");
        loseGameAlert.setContentText("Sorry you loose. The secret word was " + secretWord + ".");

        currentGridpane = wordGrids.get(0);
    }
}
