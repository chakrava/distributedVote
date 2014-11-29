package distributedVote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 */
public interface InterfacePushVotesToClient extends Remote {

    public void pushVotes(ArrayList<Choice> votesInc) throws RemoteException;
}
