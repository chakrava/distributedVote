package rmiImplement;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 */
public interface VoteInterface extends Remote {

    public int login(String pass) throws RemoteException;

    public String test(String pass) throws RemoteException;
}
