package distributedVote;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 */
public class Voter implements InterfacePushVotes, Serializable {

    public Client client;
    public int id;
    public double key;

    public Voter(int id, double key, Client c) {
        this.id = id;
        this.key = key;
        this.client = c;
    }

    @Override
    public String toString() {
        return "" + id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Voter other = (Voter) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    /**
     * @deprecated @param votesInc
     * @throws RemoteException
     */
    @Override
    public synchronized void pushVotes(ArrayList<Choice> votesInc) throws RemoteException {
//        votes = votesInc;
//        System.out.println("Recieved new votes! " + votesInc.size());
        for (Choice c : votesInc) {
            System.out.println(c.getName());
//            votes.add(c);
        }
    }

    @Override
    public void endVoting() throws RemoteException {
        client.ended = true;
    }
}
