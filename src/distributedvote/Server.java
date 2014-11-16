/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedvote;

import java.io.IOException;

/**
 *
 * A simple server.
 *
 * Waits for connection requests on port 60000. Upon connection sends a short
 * message, waits a random interval (to represent I/O operations), sends a
 * second short message, and finally receives a reply.
 *
 * @author Erik Storla <estorla42@gmail.com>
 */
public class Server {

    static int port = 60000;
    static int connectionCount = 1;

    public static void main(String[] args) {
        System.out.println("Server starting up...");
        Server myServer = new Server(port);
        myServer.server();
    }

    Server(int p) {
        port = p;
    }

    void server() {
        //System.out.println("server");

        try {
            MySocket serverSocket = new MySocket(port);
            while (true) {//listen for connections "endlessly" (10 second timeout)
                MySocket connectedSocket = new MySocket(serverSocket.waitForConnection());//blocking, hand off a connection to connectedSocket
                System.out.println("Connection " + connectionCount + " accepted");
                Thread thread = new Thread(new ServerThread(connectedSocket, connectionCount++));//put the connection in its own thread
                thread.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Server done");
    }

    //handles the I/O operations of a connection, allows concurrent connections
    class ServerThread implements Runnable {

        MySocket socket;
        int connectionNum;

        ServerThread(MySocket s, int i) {
            socket = s;
            connectionNum = i;
        }

        @Override
        public void run() {
            System.out.println("Theread running");
            //socket.sendMessage("You are connection #" + connectionNum);//tell the client which number it is

            String messageIn = "#" + connectionNum + ": ";
            try {
                messageIn += socket.recieveMessage();//get a response from the client
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(messageIn);
            String reply = processMessage(messageIn);
        }

    }
    
    public String processMessage(String in ){
//        if(){
//            
//        }
        
        return "reply";
    }
}
