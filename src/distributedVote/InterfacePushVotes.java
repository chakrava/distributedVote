package distributedVote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 * @deprecated
 */
public interface InterfacePushVotes extends Remote {

    public void pushVotes(ArrayList<Choice> votesInc) throws RemoteException;

    public void endVoting() throws RemoteException;
}
