package rmiImplement;

import java.io.Serializable;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 */
public class Voter implements Serializable{
    public int id;
    public double key;

    public Voter(int id, double key) {
        this.id = id;
        this.key = key;
    }
    
}
