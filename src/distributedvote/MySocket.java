/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedvote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class which handles socket communication using data streams. Can both send
 * and receive.
 *
 * The object is initialized as a
 *
 * @author Erik Storla <estorla42@gmail.com>
 */
public class MySocket {

    private Socket socket;//a socket to initiate a connection
    private ServerSocket serverSocket;//a socket to receive connection
    private BufferedReader input;
    private PrintWriter output;

    private final int TIMEOUT = 10000;//timeout after 10 seconds

    //if given a host and port MySocket creates a socket that initiates a connection to the given host on the given port
    MySocket(String host, int port) throws IOException {
        socket = new Socket(host, port);
        setStreams();
    }

    //if only given a port MySocket creates a socket that receives conection requests
    MySocket(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(TIMEOUT);
    }

    MySocket(Socket s) throws IOException {
        socket = s;
        setStreams();
    }

    //wait for an incoming connection request, returns the connected socket
    public Socket waitForConnection() throws IOException {
        socket = serverSocket.accept();
        setStreams();
        return socket;
    }

    //initializes input and output streams
    private void setStreams() throws IOException {
        socket.setSoTimeout(TIMEOUT);

        InputStream inStream = socket.getInputStream();
        input = new BufferedReader(new InputStreamReader(inStream));
        OutputStream outStream = socket.getOutputStream();
        output = new PrintWriter(new OutputStreamWriter(outStream));

    }

    //send a message
    public void sendMessage(String message) {
        output.println(message);
        output.flush();
    }

    //receive a message
    public String recieveMessage() throws IOException {
        String message = input.readLine();
        return message;
    }
}
