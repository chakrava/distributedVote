package distributedvote;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 *
 * A simple object which represents a vote
 *
 * contains the vote itself and a label, an unique identifier for a voter
 */
public class Message implements Serializable {

    String vote;
    int identifier;
    String command;
    ArrayList<String> votes;

    public Message(String c, int l, String s) {
        command = c;
        vote = s;
        identifier = l;
        votes = new ArrayList<>();
    }

    //turn a vote into a byte[]
    public byte[] serialize() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(this);
        return out.toByteArray();
    }

    //turn a byte[] back into a Vote
    public synchronized static Message deserialize(byte[] d) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(d);
        ObjectInputStream is = new ObjectInputStream(in);
        is.close();
        return (Message) is.readObject();
    }

    public String toString() {
        return identifier + "|" + vote;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public int getLabel() {
        return identifier;
    }

    public void setLabel(int label) {
        this.identifier = label;
    }

}
