package rmiImplement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.util.ArrayList;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            VoteInterface vote = (VoteInterface) Naming.lookup("//localhost:60000/vote");
            Voter me = vote.login("pass");
            if (me == null) {
                System.out.println("Invalid password!");
                System.exit(0);
            }
            System.out.println("ID: " + me.id);
            System.out.println("Pass: " + me.key);
            String input = " ";

            ArrayList<Choice> choices;

            while (!input.equals("0")) {
                System.out.print(vote.printMenu());
                input = getUserInput();

                switch (input) {
                    case "1":
                        System.out.println(vote.getVotes(me));
                        break;
                    case "2":
                        System.out.println(vote.getChoices(me));
                        break;
                    case "3":
                        System.out.print("Please enter the choice you wish to add: ");
                        input = getUserInput();
                        if (!vote.addChoice(me, input)) {
                            System.out.println("Error, choice not added");
                        }
                        input = " ";
                        break;
                    case "4":
                        System.out.println(vote.getChoices(me));
                        System.out.print("Select your choice: ");
                        input = getUserInput();
                        try {
                            if (!vote.vote(me, Integer.parseInt(input))) {
                                System.out.println("Error, vote not recorded");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println(e);
                        }
                        input = " ";
                        break;
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String getUserInput() {
        String temp = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            temp = br.readLine();
        } catch (IOException ex) {

        }
        return temp;

    }
}
