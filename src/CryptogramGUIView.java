import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.xml.stream.events.Characters;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * @author Makayla Worden
 * Course: CSC 335 Fall 2019
 * Project: Cryptograms GUI
 * CryptogramGUIView – This is the JavaFX GUI. It plays
 * the same game as originally done, however this uses
 * a GUI to show the user what they're guesses turn into.
 */
public class CryptogramGUIView extends Application implements java.util.Observer {
    private Stage primaryStage;
    private static CryptogramController cryptCont;
    public static void main(String[] args){
        cryptCont = new CryptogramController();
        launch(args);
    }

    /**
     * start runs the GUI output.
     * @param primaryStage the stage
     */
    @Override
    public void start (Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Cryptograms");
        BorderPane border = new BorderPane(); // screen layout
        VBox vBox = new VBox(); // Right col

        makeRight(vBox); // Adds buttons and checkbox on right side
        VBox game = makeGameBoard(); // Creates the game board

        playGame(game);

        border.setRight(vBox);
        border.setCenter(game);

        Scene scene = new Scene(border, 900, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void playGame(VBox game){

    }
    /**
     * This creates the right side of the playing board.
     * (New puzzle, hint, freq)
     * @param vBox a VBox for the right side
     * @return VBox, and updated version of the parameter
     */
    private VBox makeRight(VBox vBox) {
        Button newGame = new Button();
        newGame.setText("New Puzzle");
        newGame.setOnAction(event -> {
            cryptCont = new CryptogramController();
            start(primaryStage);
        });

        Button giveHint = new Button();
        giveHint.setText("Hint");
        giveHint.setOnAction(event -> {
            cryptCont.giveHint(); // Prints hint to the console
        });

        CheckBox cBox = new CheckBox("Show Freq");
        HBox hBox = new HBox();
        VBox col1 = new VBox();
        VBox col2 = new VBox();

        cBox.setOnAction(event -> {
            // if isSelected, show the frequency
            if (cBox.isSelected()) {
                String temp = cryptCont.frequey();
                temp = temp.replace("\n", "");
                String[] freqList = temp.split(" ");
                int j = 0;
                Label lab = new Label("");
                for (String s : freqList) {
                    if (Character.isLetter(s.charAt(0)))
                        lab = new Label(s);
                    else {
                        String oldLab = lab.getText();
                        Label newLab = new Label(oldLab + s);
                        if (j <= freqList.length / 2)
                            col1.getChildren().addAll(newLab);
                        else {
                            col2.getChildren().addAll(newLab);
                            lab = newLab;
                        }
                    }
                    j++;
                }
            }
            else {
                col1.getChildren().clear();
                col2.getChildren().clear();
            }
        });
        ObservableList kiddo = hBox.getChildren();
        kiddo.addAll(col1, col2); // Adds the cols of labels to the HBox

        ObservableList list = vBox.getChildren();
        list.addAll(newGame, giveHint, cBox, hBox);
        return vBox;
    }

    /**
     * makeGameBoard creates the format for the game to be played on.
     * @return VBOX, the completed game board
     */
    private VBox makeGameBoard() {
        String c = cryptCont.getCipherMessage();
        String[] cipher = c.split(" "); // Splitting on words
        String by30 = "";
        List<String> bigBoy = new ArrayList<>();
        for (int i = 0; i < cipher.length; i++) {
            if ((by30 + cipher[i] + 1).length() <= 30) {
                by30 += cipher[i] + " ";
            } else {
                bigBoy.add(by30);
                by30 = "";
            }
        }
        VBox cols = new VBox(); // Game board
        HBox row = new HBox();
        for (String s: bigBoy) { // most outter (full ~30 length line)
            for (int j = 0; j < s.length(); j++) { // middle (words)
                char letter = s.charAt(j); // inside (individual letters)
                Label lab = new Label(Character.toString(letter));
                VBox letterBox = new VBox();
                letterBox.setAlignment(Pos.BASELINE_CENTER);
                TextField text = new TextField();
                if (!Character.isLetter(letter))
                    text.setDisable(true);
                letterBox.getChildren().addAll(text, lab);
                row.getChildren().addAll(letterBox);

            }
            cols.getChildren().addAll(row);
            row = new HBox(); // new row after ~30
        }
        return cols;
    }
    @Override
    public void update(Observable o, Object arg){

    }
}
