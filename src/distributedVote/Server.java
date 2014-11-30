package distributedVote;

import java.rmi.RemoteException;
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
    //public ArrayList<Voter> voters = new ArrayList<>();
    //public ArrayList<Choice> choices = new ArrayList<>();
    static Server self;
    static Message message;

    public static void main(String[] args) throws RemoteException {
        Server self = new Server();

    }

    public Server() {
        System.out.println("Server starting up...");
        try {
            LocateRegistry.createRegistry(60000);
            Registry registry = LocateRegistry.getRegistry("localhost", 60000);
            message = new Message(this);
            registry.bind("vote", message);

//            System.out.print("Starting votepusher...");
//            new Thread(new VotePusher()).start();
//            new Thread(new Message.VotePusher()).start();
//            System.out.println("started");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
