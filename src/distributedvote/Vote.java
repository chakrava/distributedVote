package distributedvote;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 *
 * A simple object which represents a vote
 *
 * contains the vote itself and a label, an unique identifier for a voter
 */
public class Vote implements Serializable {

    String vote;
    int identifier;
    String command;

    public Vote(String c, int l, String s) {
        vote = s;
        identifier = l;
        command = c;
    }

    //turn a vote into a byte[]
    public byte[] serialize() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(this);
        return out.toByteArray();
    }

    //turn a byte[] back into a Vote
    public synchronized static Vote deserialize(byte[] d) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(d);
        ObjectInputStream is = new ObjectInputStream(in);
        is.close();
        return (Vote) is.readObject();
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
