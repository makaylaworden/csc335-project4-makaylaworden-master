import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author Makayla Worden
 * Course: CSC 335 Fall 2019
 * Project: Cryptograms GUI
 * CryptogramModel – This class contains all of the game
 * state and must be also shared between the two front ends.
*/
public class CryptogramModel extends java.util.Observable {
    // Things the model and view will want
    private String message;
    private String cipherMessage;
    private String guessStr;
    private HashMap<String, String> cipherMap;
    private HashMap<String, String> guessMap;
    /*
    TODO:
        Hold the quote and scrambled version
        Keep track of game state
            What guesses have been made (use observable)
        Has frequency and swap (from, to)

     */
    public CryptogramModel(){

    }
    /************ SETTERS AND GETTERS ******************/
    public String getMessage(){ return message; }
    public void setMessage(String newMessage) {message = newMessage; }

    public String getCipherMessage() { return cipherMessage; }
    public void setCipherMessage (String newMessage) { cipherMessage = newMessage; }

    public String getGuessStr() { return guessStr; }
    public void setGuessStr(String newMessage) { guessStr = newMessage; }

    public HashMap<String, String> getCipherMap() { return cipherMap; }
    public void setCipherMap(HashMap<String, String> newMap) { cipherMap = newMap; }

    public HashMap<String, String> getGuessMap() { return guessMap; }
    public void setGuessMap (HashMap<String, String> newMap) { guessMap = newMap; }

    /************ END SETTERS AND GETTERS ***************/

    /**
     * selectQuote takes a random quote from quotes.txt to be
     * used in the game.
     * PROJ 4 UPDATE: No longer returns quote, just sets message in MODEL
     */
    public void selectQuote(){
        File file = new File("quotes.txt");
        Scanner scan = null;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<String> quotes = new ArrayList<>();
        assert scan != null;
        while (scan.hasNextLine()){
            quotes.add(scan.nextLine());
        }
        Collections.shuffle(quotes); // Makes the selection random
        this.message = quotes.get(0).toUpperCase();
    }
}
