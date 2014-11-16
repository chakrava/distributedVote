package rmiImplement;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 */
public interface VoteInterface extends Remote{
    public String test() throws RemoteException;
}
