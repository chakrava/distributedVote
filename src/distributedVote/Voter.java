package distributedVote;

import java.io.Serializable;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 */
public class Voter implements Serializable {

    public int id;
    public double key;

    public Voter(int id, double key) {
        this.id = id;
        this.key = key;
    }

    @Override
    public String toString() {
        return ""+id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Voter other = (Voter) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
