package votemulticast;

import distributedvote.Message;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 *
 * A listener for incoming votes
 *
 */
class VoterReadThread implements Runnable {

    private static final boolean VERBOSE = true;

    private static final int MAX_LEN = 1000;
    private static InetAddress group;
    private static int port;
    private static int timeout;

    int label;
    private final Message vote;
    private final ArrayList<Message> list = new ArrayList<>();
    private final ArrayList<Integer> voted = new ArrayList<>();

    public VoterReadThread(InetAddress g, int p, int l, Message v, int t) {
        group = g;
        port = p;
        timeout = t;
        label = l;
        vote = v;
        list.add(v);
        voted.add(v.getLabel());
    }

    @Override
    public void run() {
        try {
            MulticastSocket socket = new MulticastSocket(port);
            socket.joinGroup(group);

            //listen loop
            while (true) {
                byte[] data = new byte[MAX_LEN];
                DatagramPacket packet = new DatagramPacket(data, data.length, group, port);
                socket.setSoTimeout(timeout);

                //listen for an incoming vote
                socket.receive(packet);

                //process the vote
                byte[] incomingBytes = packet.getData();
                Message incomingVote = Message.deserialize(incomingBytes);
                if (VERBOSE) {
                    System.out.print("#" + label + " rec: " + " " + incomingVote + "\t");
                }

                if (!voted.contains(incomingVote.getLabel())) {//if I haven't seen this vote before...
                    if (VERBOSE) {
                        System.out.println("new!");
                    }
                    voted.add(incomingVote.getLabel());//add the voter to my list
                    list.add(incomingVote);//add it to my vote list
                    sendMessage();//send out my own vote again (the new voter needs to see my vote)
                } else {
                    if (VERBOSE) {
                        System.out.println("repeat");
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            //System.out.println(label + " " + e.getMessage());
            //e.printStackTrace();
        }

        printFinal(label, list);
    }

    //resends my vote, performed as thread so that listener can resume ASAP
    public void sendMessage() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    MulticastSocket socket;
                    group = InetAddress.getByName(group.getHostAddress());

                    byte[] data = vote.serialize();
                    DatagramPacket packet = new DatagramPacket(data, data.length, group, port);

                    if (VERBOSE) {
                        System.out.println(label + " Sending message: [" + vote + "]");
                    }

                    socket = new MulticastSocket(port);
                    socket.setTimeToLive(1);
                    socket.send(packet);
                    socket.close();
                } catch (UnknownHostException ex) {

                } catch (IOException ex) {
                }
            }
        });
        thread.start();
    }

    //prints the final vote results with a tally
    //synchronized so that the outputs don't overlap
    private synchronized static void printFinal(int l, ArrayList lst) {
        System.out.println("#" + l + " ending, received[" + lst.size() + "]:");
        int choice1 = 0;
        int choice2 = 0;
        System.out.print("\t");
        for (Object s : lst) {
            if (VERBOSE) {
                System.out.print("[" + s + "] ");
            }
            if (s.getClass() == Message.class) {
                Message v = (Message) s;
                if (v.getVote().equals("1")) {
                    choice1++;
                } else if (v.getVote().equals("2")) {
                    choice2++;
                }
            }
        }
        System.out.println();
        System.out.println("\tTotal choice 1: " + choice1);
        System.out.println("\tTotal choice 2: " + choice2);
    }

}
