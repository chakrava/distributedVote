package votemulticast;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 * 
 * Initializes a number of multicast clients that "vote" either "1" or "2"
 * 
 */
public class VoteMulticast {
    static String listenAddress = "224.0.0.1";
    static int port = 3456;
    static int timeout=2000;
    static int choices=2;

    public static void main(String[] args) throws InterruptedException {
        Voter[] list = new Voter[4];
        for (int i = 0; i < list.length; i++) {
            list[i] = new Voter();//create multicast objects
        }

        for (int i = 0; i < list.length; i++) {
            Thread.sleep((long) (Math.random() * (timeout-50)));//wait between starting clients, staggers their connection
            int voteFor=(int)(Math.random()*choices);//choose whom the client will vote for
            
            //String[] {client #, listen on IP, listen on port, vote choice, timeout}
            String[] toMulti = {("" + (i + 1)), listenAddress, "" + port, ""+(voteFor+1),""+timeout};
            list[i].init(toMulti);
        }
    }

}
