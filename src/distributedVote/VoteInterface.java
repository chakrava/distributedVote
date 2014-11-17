package distributedVote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 */
public interface VoteInterface extends Remote {

    public Voter login(String pass) throws RemoteException;

    public String finalMessage(Voter v) throws RemoteException;

    public String endVoting(Voter v) throws RemoteException;

    public String setQuestion(Voter v, String question) throws RemoteException;

    public String getChoices(Voter v) throws RemoteException;

    public String getVotes(Voter v) throws RemoteException;

    public String addChoice(Voter v, String choice) throws RemoteException;

    public String printMenu(Voter v) throws RemoteException;

    public String vote(Voter v, int i) throws RemoteException;

    public String unVote(Voter v) throws RemoteException;
}
