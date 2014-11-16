package rmiImplement;

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
            VoteInterface vote = (VoteInterface)Naming.lookup("//localhost:60000/vote");
            System.out.println(vote.test());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}
