package distributedVote;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Server starting up...");
        try {
            LocateRegistry.createRegistry(60000);
            Registry registry = LocateRegistry.getRegistry("localhost", 60000);
            registry.bind("vote", new Message());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
