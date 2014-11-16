/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedvote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        System.out.println("Client starting up...");
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

            while (true) {
                System.out.print("Enter command: ");
                String message = getInput();
                socket.sendMessage(message);
                
                //socket.sendMessage("You sent me:\t" + message);//send a reply
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //System.out.println("done");
    }

    private String getInput() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String temp = br.readLine();
        return temp;
    }
}
