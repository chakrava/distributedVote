/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientserver;

import java.io.IOException;

/**
 *
 * A simple client.
 *
 * Connects to a server on the localhost, port 60000. Receives two messages and
 * sends one reply.
 *
 * @author Erik Storla <estorla42@gmail.com>
 */
public class Client {

    String hostName;
    int port;

    static int defaultPort = 60000;

    public static void main(String[] args) {
        int port = defaultPort;
        Client myClient = new Client("localhost", port);
        myClient.client();
    }

    Client(String h, int p) {
        port = p;
        hostName = h;
    }

    void client() {
        //System.out.println("client");

        try {
            MySocket socket = new MySocket(hostName, port);//create connection socket

            String message = "";

            message = socket.recieveMessage();//recieve an initial message
            System.out.println("\t"+message);

            message = socket.recieveMessage();//recieve a follow-up message
            System.out.println("\t"+message);

            socket.sendMessage("You sent me:\t" + message);//send a reply

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //System.out.println("done");
    }
}
