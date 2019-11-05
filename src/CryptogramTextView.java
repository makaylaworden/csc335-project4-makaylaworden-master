/**
 * @author Makayla Worden
 * Course: CSC 335 Fall 2019
 * Project: Cryptograms GUI
 * CryptogramTextView – This is the UI that we built in project 2.
 * This plays the "origial" game that was in the command line.
 */
import java.util.*;

public class CryptogramTextView implements java.util.Observer{
    private CryptogramModel cryptmod;
    private CryptogramController cryptCont;
    public CryptogramTextView(){
        cryptCont = new CryptogramController();
        cryptmod = cryptCont.getCryptMod();
    }

    @Override
    public void update(Observable o, Object arg){
        //TODO: This lol
    }

    public void getCommand(){
        System.out.print("Enter a command (help to see commands): ");
        Scanner answer = new Scanner (System.in);
        String command = answer.nextLine().toUpperCase();
        // NOTE: you deleted a line here bc I don't think you need it

        while (!command.equals("EXIT")) {
            if ((command.startsWith("REPLACE ") && command.contains(" BY "))|| command.contains(" = ")) { //If user wants to play
                String[] values = cryptCont.getXandY(command);
                String x = values[0]; // value to be replaced
                String y = values[1]; // value replacing
                if (!values[0].equals("blah trash")) // lol I'm sorry
                    cryptmod.setGuessStr(playGame(x, y));
                if (cryptmod.getMessage().equals(cryptmod.getGuessStr())) {
                    System.out.println("\nYou got it!"); //Printed when the user wins the game.
                    break;
                }
            } else if (command.equals("FREQ")) { // Gives frequency of letters
                System.out.println(cryptCont.frequey());
            } else if (command.equals("HINT")) { // Hints one letter mapping
                giveHint();
            } else if (command.equals("HELP")) { // Prints commands
                needHelp();
            } else { // if they type something wacko
                System.out.println("Invalid command! Type 'help' to see valid commands.");
            }
            System.out.print("\nEnter a command (help to see commands): ");
            answer = new Scanner(System.in);
            command = answer.nextLine().toUpperCase();
        }
    }

    /**
     * playGame does the actual decryption  game with the user.
     * UPDATE FROM PROJECT 1: playGame now needs two String inputs, toReplace and replacer,
     * so that it can replace those letters.
     * @param toReplace the String to get replaced.
     * @param replacer the String replacing toReplace.
     */
    public String playGame(String toReplace, String replacer){
        if (toReplace.length() > 0 && replacer.length() > 0) {
            if (Character.isLetter((replacer.charAt(0))) && replacer.length() == 1) {
                cryptmod.getGuessMap().put(toReplace, replacer);
                cryptmod.setGuessStr(updateGuessStr()); // Updates their guesses
                gameOutput(); // Prints with wrapping
            }
        }
        return cryptmod.getGuessStr(); // Guesses are used later to give hints
    }
    /**
     * needHelp prints each command possible for the program.
     * IMPLEMENTED HERE BC IT'S SPECIFIC TO
     */
    public static void needHelp(){
        System.out.println("i. replace X by Y – replace letter X by letter Y in our attempted solution,\n" +
                "   X = Y – a shortcut for this same command. NOTE: You must enter your replacements in this format!");
        System.out.println("ii. freq – Display the letter frequencies in the encrypted quotation (i.e., how many of letter X appear).");
        System.out.println("hint – display one correct mapping that has not yet been guessed.");
        System.out.println("exit – Ends the game early.");
        System.out.println("help – List these commands.");
    }
    /**
     * giveHint hints a mapping for the user to help them guess. Done by
     * getting a random index, seeing if they guessed the value at that
     * index, going again if they have, then printing a guess.
     */
    public void giveHint(){
        if (cryptmod.getCipherMap().keySet().size() > 0) {
            Object[] keySet = cryptmod.getCipherMap().keySet().toArray();
            Random rand = new Random();
            int i = rand.nextInt(keySet.length);
            String k = keySet[i].toString();
            System.out.println();
            while (cryptmod.getGuessStr().contains(k)) {
                i = rand.nextInt(keySet.length);
                k = keySet[i].toString();
            }
            System.out.print(cryptmod.getCipherMap().get(k) + " encrypts " + k + "\n"); // This is the hint
        }
    }
    /**
     * This updates guessStr to be the users latest guess.
     * @return guessStr updated with the user's latest guess.
     */
    public String updateGuessStr() {
        List<String> cipherList = Arrays.asList(cryptmod.getCipherMessage().split(""));
        String[] guessList = cryptmod.getGuessStr().split(""); //splitting it to make indexing easier
        for (String key : cryptmod.getGuessMap().keySet()) {
            for(int i = 0; i < cipherList.size(); i++){
                if (cipherList.get(i).equals(key))
                    guessList[i] = cryptmod.getGuessMap().get(key); //Adds the newest letter to it's proper index
            }
        }
        return String.join("", guessList);
    }
    /**
     * gameOutput makes sure the printed output from the user playing is in the proper
     * format (no more than 80 characters on a line)
     */
    public void gameOutput(){
        String[] messageArr = cryptmod.getCipherMessage().split(" ");
        String cipherTemp = "";
        String guessTemp = "";
        for (int i = 0; i < messageArr.length; i++){
            if((cipherTemp + messageArr[i]).length() + 1 >= 80) { // adding 1 for spaces
                System.out.println(guessTemp);
                System.out.println(cipherTemp);
                System.out.println();
                cipherTemp = "";
                guessTemp = "";
            }
            for (int j = 0; j < messageArr[i].length(); j++){
                char c = messageArr[i].charAt(j);
                String letter = Character.toString(c); // Each letter in the word
                if (Character.isLetter(c))
                    guessTemp += cryptmod.getGuessMap().get(letter);
                else
                    guessTemp += letter;

            }
            cipherTemp += messageArr[i] + " ";
            guessTemp += " ";
        }
        System.out.println(guessTemp);
        System.out.println(cipherTemp);
    }
}
