package rmiImplement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 */
public class Message extends UnicastRemoteObject implements VoteInterface {

    static int count = 1;
    String message;

    public Message() throws RemoteException {
        message = "test";
    }

    @Override
    synchronized public int login(String pass) throws RemoteException {
        if (pass.equals("pass")) {
            return count++;
        }
        else return -1;
    }

    @Override
    public String test(String pass) throws RemoteException {
        return message;
    }

}
