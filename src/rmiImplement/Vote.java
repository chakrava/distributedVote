package rmiImplement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 */
public class Vote extends UnicastRemoteObject implements VoteInterface {

    String message;

    public Vote() throws RemoteException{
        message = "test";
    }

    @Override
    public String test() {
        return message;
    }

}
