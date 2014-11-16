package rmiImplement;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 */
public interface VoteInterface extends Remote {

    public Voter login(String pass) throws RemoteException;

    public String test(Voter v) throws RemoteException;
    
    public ArrayList<Choice> getChoices(Voter v)throws RemoteException;
    
    public boolean vote(Voter v, Choice choice) throws RemoteException;
    
    public boolean addChoice(Voter v, String choice) throws RemoteException;
    
    public String printMenu()throws RemoteException;
    
    public Choice makeChoice() throws RemoteException;
}
