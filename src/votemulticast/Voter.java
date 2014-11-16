package votemulticast;

import distributedvote.Vote;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 *
 * An object that "votes" and receives the votes of other objects via multicast
 *
 */
public class Voter {

    static int label;
    static MulticastSocket socket;
    static InetAddress group;
    static int port;
    static int timeout;
    static String message;
    static Vote vote;
    static byte[] data;

    static String listenAddress;// = "224.0.0.1";

    //simple wrapper, tired of all the Integter.parseInt()
    private static int toInt(String s) {
        return Integer.parseInt(s);
    }

    //takes in the settings given and initializes the multisender
    public void init(String[] incArgs) {
        label = toInt(incArgs[0]);
        listenAddress = incArgs[1];
        port = toInt(incArgs[2]);
        message = (incArgs[3]);
        timeout = toInt(incArgs[4]);
        vote = new Vote(label, message);

        //turn the vote into data that can be sent over Datagram
        try {
            data = vote.serialize();
        } catch (IOException ex) {

        }

        startListenerAndSend();//after initialization, start up the listen and send this voter's vote
    }

    public void startListenerAndSend() {
        try {
            group = InetAddress.getByName(listenAddress);
            socket = new MulticastSocket(port);
            DatagramPacket packet = new DatagramPacket(data, data.length, group, port);

            //start listening for other voters' messages
            Thread theThread = new Thread(new VoterReadThread(group, port, label, vote, timeout));
            System.out.println("#" + label + " Listening");
            theThread.start();

            //send my own vote
            System.out.println("#" + label + " Sending message: [" + vote + "]");
            socket = new MulticastSocket(port);
            socket.setTimeToLive(1);
            socket.send(packet);
            socket.close();
        } catch (UnknownHostException ex) {

        } catch (IOException ex) {

        }
    }

}
