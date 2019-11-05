import java.util.Scanner;


/**
 * @author Makayla Worden
 * Course: CSC 335 Fall 2019
 * Project: Cryptograms GUI
 * Cryptogram – This is the main class. When invoked with a command
 * line argument of “-text”, you will launch the text-oriented UI.
 * When invoked with a command line argument 0f “-window” you’ll
 * launch the GUI view. The default will be the GUI view.
 */
public class Cryptogram {
    public static void main (String[] args){
        CryptogramTextView textView = new CryptogramTextView();
        CryptogramGUIView GUIView = new CryptogramGUIView();
        //GUIView.setCryptCont(cryptCon);

        System.out.print("Please enter '-text' or '-window': ");
        Scanner input = new Scanner(System.in);
        String command = input.nextLine().toLowerCase();
        if (command.equals("-text"))
            textView.getCommand();
        else if (command.equals("-window"))
            GUIView.main(new String[0]);
        else
            System.out.println("Invalid input! Run me again.");
    }
}
