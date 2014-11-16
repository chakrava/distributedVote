/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clientserver;

/**
 *
 * Shows the operation of the server and multiple clients.
 * 
 * By default the server is started and then 10 clients are created and connect.
 * 
 * An argument can be passed to specify how many clients should be created.
 * 
 * @author Erik Storla <estorla42@gmail.com>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) throws InterruptedException {
        int numClients=10;
        if(args.length>0){
            numClients=Integer.parseInt(args[0]);//command line parameter sets number of clients to connect with
        }
        
        Thread server = new Thread(new Runnable(){public void run(){Server.main(args);}});
        server.start();//start the server
        
        for(int i=0;i<numClients;i++){
            Thread.sleep((long) (Math.random()*200));//wait between starting clients, staggers their connection
            
            Thread client=new Thread(new Runnable(){public void run(){Client.main(args);}});
            client.start();//start a new client
        }
    }
    
}
