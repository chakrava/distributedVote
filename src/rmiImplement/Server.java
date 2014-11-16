package rmiImplement;

import java.rmi.Naming;
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
            //java.rmi.server.hostname = "127.0.0.1";
            Registry registry = LocateRegistry.getRegistry("localhost", 60000);
            //Naming.rebind("vote", new Vote());
            registry.bind("vote", new Vote());
        } catch (Exception e) {
            System.out.println(e);
        }

        //while (true) {
        //}
    }

}
