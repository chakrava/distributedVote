package distributedVote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 */
public class Choice implements Serializable {

    private String name;
    public ArrayList<Voter> votedBy;

    public Choice(String name) {
        this.name = name;
        votedBy = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getPicked() {
        return votedBy.size();
    }

    public ArrayList<Voter> getVotedBy() {
        return votedBy;
    }

    synchronized public boolean voteFor(Voter v) {
        for (Voter prevVoters : votedBy) {
            if (prevVoters.id == v.id) {
                return false;
            }
        }
        votedBy.add(v);
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.name);
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
        final Choice other = (Choice) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

}
