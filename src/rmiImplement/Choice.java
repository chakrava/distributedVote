package rmiImplement;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 */
public class Choice implements Serializable {
    private String name;
    public ArrayList<Voter> votedBy;

    public Choice(String name) {
        this.name = name;
        votedBy=new ArrayList<>();
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
    
    synchronized public boolean voteFor(Voter v){
        for(Voter prevVoters:votedBy){
            if(prevVoters.id==v.id){
                return false;
            }
        }
        votedBy.add(v);
        return true;
    }
    
}
