package rmiImplement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;

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
            if(me==null){
                System.out.println("Invalid password!");
                System.exit(0);
            }
            System.out.println("ID: " + me.id);
            System.out.println("Pass: "+me.key);
            String input=getUserInput();
            while(!input.equals("0")){
                System.out.println(vote.printMenu());
                input=getUserInput();
                
                
                
                
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    

    public static String getUserInput() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String temp = br.readLine();
        return temp;
    }
}
