package rmiImplement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 */
public class Message extends UnicastRemoteObject implements VoteInterface {

    static int count = 1;
//    String message;
    private ArrayList<Voter> voterList;
    ArrayList<Choice> choices;

    @Override
    public String printMenu() {
        String temp = "";
        temp += "1: View current voting\n";
        temp += "2: View current choices\n";
        temp += "3: Add a new choice\n";
        temp += "4: Select a choice\n";

        temp += "0: Exit\n";
        temp += "Please make a selection: ";

        return temp;
    }

    public Message() throws RemoteException {
//        message = "test";
        choices = new ArrayList<>();
        voterList = new ArrayList<>();
    }

    private boolean checkKey(Voter checkVoter) {
        if (voterList.size() > 0) {
            for (Voter v : voterList) {
                if (v.id == checkVoter.id) {
                    if (v.key == checkVoter.key) {
                        System.out.println("Verified");
                        return true;
                    } else {
                        break;
                    }
                }
            }
        }
        System.out.println("Verification error");
        return false;
    }

    @Override
    synchronized public Voter login(String pass) throws RemoteException {
        if (pass.equals("pass")) {
            double key = Math.random();
            Voter temp = new Voter(count++, key);
            voterList.add(temp);
            return temp;
        } else {
            return null;
        }
    }

    @Override
    public String test(Voter v) throws RemoteException {
//        if (checkKey(v)) {
//            return message;
//        }
        return null;
    }

    @Override
    synchronized public boolean vote(Voter v, int i) {
        if (checkKey(v)) {
            for (Choice c : choices) {
                System.out.println("Choice "+c.getName()+" containing "+c.votedBy);
                c.votedBy.remove(v);
            }

            if (choices.size() > i) {
                choices.get(i).voteFor(v);
                return true;
            }
        }
        return false;
    }

    @Override
    //public ArrayList<Choice> getChoices(Voter v) throws RemoteException {
    public String getChoices(Voter v) throws RemoteException {
        String temp = "";
        if (checkKey(v)) {
            if (choices.isEmpty()) {
                return "No choices";
            }
            //for (Choice c : choices) {
            for (int i = 0; i < choices.size(); i++) {
                temp += i + " " + choices.get(i).getName() + "\n";
            }
        }
        return temp;
    }

    @Override
    //public ArrayList<Choice> getChoices(Voter v) throws RemoteException {
    public String getVotes(Voter v) throws RemoteException {
        String temp = "";
        if (checkKey(v)) {
            if (choices.isEmpty()) {
                return "No choices";
            }
            for (Choice c : choices) {
                temp += c.getName() + " " + c.getPicked() + "\n";
            }
        }
        return temp;
    }

    @Override
    synchronized public boolean vote(Voter v, Choice c) throws RemoteException {
        if (checkKey(v)) {
            return c.voteFor(v);
        } else {
            return false;
        }
    }

    @Override
    public boolean addChoice(Voter v, String choiceName) throws RemoteException {
        if (checkKey(v)) {
            for (Choice c : choices) {
                if (c.getName().equals(choiceName)) {
                    return false;
                }
            }

            //Choice choice = new Choice(choiceName);
            choices.add(new Choice(choiceName));
            return true;
        } else {
            return false;
        }
    }
}
