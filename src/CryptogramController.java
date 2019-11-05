import java.util.*;

/**
 * @author Makayla Worden
 * Course: CSC 335 Fall 2019
 * Project: Cryptograms GUI
 * CryptogramController – This class contains all of the game
 * logic, and must be shared by the textual and graphical UIs. You may
 * not call into different controllers from the different UIs and
 * all methods provided must be useful to both front ends.
*/
public class CryptogramController {
    private CryptogramModel cryptMod;
    public CryptogramController(){
        // Creates the model
        cryptMod = new CryptogramModel();
        cryptMod.selectQuote();
        cryptMod.setCipherMap(MakeCipherMap());
        cryptMod.setCipherMessage(scrambleMessage(cryptMod.getCipherMap(), cryptMod.getMessage()));
        cryptMod.setGuessStr(makeGuessStr()); // This is the string with all the user guesses
        cryptMod.setGuessMap(makeGuessMap()); // This is a mapping of all the user guesses, sets Model guessMap in method
    }

    /* Crytogram Model setter/getter */
    public void setCryptMod (CryptogramModel newMod) { cryptMod = newMod; }
    public CryptogramModel getCryptMod() { return cryptMod; }

    public String getMessage() { return cryptMod.getMessage(); }
    public String getCipherMessage() { return cryptMod.getCipherMessage(); }
    /**
     * getCipherMap() creates a map of the alphabet in order to it's random cipher translation.
     * alpha is a List<String> that is the alphabet in order. Ex. [a, b, c,...].
     * alphaScramble is a List<String> that is the alphabet scrambled. Ex. [s, e, y...].
     * PROJ 4 UPDATE: No longer returns map, just sets cipherMap in MODEL
     */
    public HashMap<String, String> MakeCipherMap(){
        List<String> alpha = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
        List<String> alphaScramble = new ArrayList<>(alpha); //Makes a new list with same elems
        Collections.shuffle(alphaScramble);

        HashMap<String, String> cipherMap = new HashMap<>();

        //This will map each letter in alpha to a letter in the corresponding
        // index in alphaScramble:
        for (int i = 0; i < alpha.size(); i ++){
            if (!alpha.get(i).equals(alphaScramble.get(i))) // Gets rid of letters mapping to themselves
                cipherMap.put(alpha.get(i), alphaScramble.get(i));
        }
        return cipherMap;
    }
    /**
     * This calculates how many times each letter occurs in cipherMessage
     * and then prints out each letter and how many times they're there.
     * @return HashMap<String, Integer> countAmount, that is a  map
     * of [letter: times occurred]
     */
    public String frequey() {
        //This counts the occurrence of each letter
        HashMap<String, Integer> countAmount = new HashMap<>();
        for (int i = 0; i < cryptMod.getCipherMessage().length(); i++) {
            char temp = cryptMod.getCipherMessage().charAt(i);
            if (Character.isLetter(temp)) { // Skips over things that aren't letters
                String letter = Character.toString(temp);
                if (countAmount.containsKey(letter))
                    countAmount.put(letter, countAmount.get(letter) + 1);
                else
                    countAmount.put(letter, 1);
            }
        }
        // In original version, it would now print, but this is the newer,
        // cooler version. so it just counts them all up for ya
        String tempo = "";
        // The following prints in the format requested (7x4):
        int j = 0;
        for (String key: countAmount.keySet()){
            if (j == 7){ // 7 per line
                tempo += "\n";
                j = 0;
            }
            tempo += key + " " + countAmount.get(key) + " ";
            j++;
        }
        return tempo;
    }
    /**
     * scrambleMessage takes in the String message and the HashMap cipherMap,
     * then uses the cipherMap to encrypt the message.
     * UPDATE FROM PROJ 1: A letter cannot map to itself.
     * PROJ 4 UPDATE: Sends cipherMessage to MODEL
     * @return cipherMap is a HashMap that is the encryption key.
     */
    public String scrambleMessage(HashMap<String, String> cipherMap, String message){
        ArrayList<String> toScramble = new ArrayList<>(Arrays.asList(message.split("")));
        for (int i = 0; i < toScramble.size(); i++){
            String val = toScramble.get(i);
            // Ensures every letter is mapped to something other than itself
            if (cipherMap.keySet().contains(val)){
                String newVal = cipherMap.get(val);
                toScramble.set(i, newVal);
            }
        }
        return String.join("", toScramble);
    }

    /**
     * Creates an empty map of every letter in the alphabet mapped to an empty String.
     * PROJ 4 UPDATE: No longer returns guessMap, just sets it in the MODEL
     */
    public HashMap<String, String> makeGuessMap(){
        HashMap <String, String> guessMap = new HashMap<>();
        List<String> alpha = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
        for (int i = 0; i < alpha.size(); i++){
            guessMap.put(alpha.get(i), " "); // {letter: " "}
        }
        return guessMap;
    }
    /**
     * This is the string printed to show the user what they have guessed in which location.
     * @return guessStr is a String of spaces and user guesses that is printed.
     */
    public String makeGuessStr (){
        String guessStr = "";
        //Makes string of spaces and non-letter characters:
        for(int i = 0; i < cryptMod.getCipherMessage().length(); i++){
            if (Character.isLetter(cryptMod.getCipherMessage().charAt(i)))
                guessStr = (guessStr + " ");
            else
                guessStr = (guessStr + cryptMod.getCipherMessage().charAt(i));
        }
        return guessStr; //This is basically just a string with spaces and non-letter characters
    }
    /**
     * getXandY gets the x and y values when a user wants to do the
     * replace command (or the shortcut x = y).
     * @param command a String of the users' command.
     * @return values a String[] of the values [x, y]
     */
    public static String[] getXandY(String command){
        String[] values = new String[2];
        int temp; // initialize temp value as zero
        if (command.contains("REPLACE") && command.contains("BY")){
            temp = command.indexOf(" BY ");
            values[0] = Character.toString(command.charAt(temp - 1));
            values[1] = Character.toString(command.charAt(temp + 4)); // " by ".size
        } else if (command.contains("=")){
            temp = command.indexOf(" = ");
            values[0] = Character.toString(command.charAt(temp - 1));
            values[1] = Character.toString(command.charAt(temp + 3)); // " = ".size
        } else {
            System.out.println("Invalid input"); // If wacko
            values[0] = "blah trash";
        }
        return values;
    }
    /**
     * giveHint hints a mapping for the user to help them guess. Done by
     * getting a random index, seeing if they guessed the value at that
     * index, going again if they have, then printing a guess to the console.
     */
    public void giveHint(){
        if (cryptMod.getCipherMap().keySet().size() > 0) {
            Object[] keySet = cryptMod.getCipherMap().keySet().toArray();
            Random rand = new Random();
            int i = rand.nextInt(keySet.length);
            String k = keySet[i].toString();
            System.out.println();
            while (cryptMod.getGuessStr().contains(k)) {
                i = rand.nextInt(keySet.length);
                k = keySet[i].toString();
            }
            System.out.print(cryptMod.getCipherMap().get(k) + " encrypts " + k + "\n"); // This is the hint
        }
    }
}
