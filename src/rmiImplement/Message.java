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
    static String question = "";
    private static ArrayList<Voter> voterList;
    private static ArrayList<Choice> choices;

    @Override
    public String printMenu(Voter v) {
        String temp = "";
        temp += question + "\n\n";
        if (checkKey(v)) {
            if (v.id == 1 && question.isEmpty()) {
                temp += "Q: Set the question\n";
            } else {
                temp += "1: Add a new choice\n";
                temp += "2: View current choices\n";
                temp += "3: Select a choice\n";
                temp += "4: Rescind your vote\n";
                temp += "5: View current voting results\n";
            }

            temp += "0: Exit\n";
            temp += "Please make a selection: ";
        }
        return temp;
    }

    public Message() throws RemoteException {
        choices = new ArrayList<>();
        voterList = new ArrayList<>();
    }

    @Override
    public String setQuestion(Voter v, String question) {
        if (!checkKey(v)) {
            return "Invalid credentials!";
        } else if (v.id != 1) {
            return "You cannot set the question!";
        } else if (question.length()!=0) {
            return "The question has already been set!";
        } else {
            Message.question = question;
            return "Question set to \"" + question + "\"";
        }
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

    /**
     * @deprecated
     */
    @Override
    public String test(Voter v) throws RemoteException {
//        if (checkKey(v)) {
//            return message;
//        }
        return null;
    }

    @Override
    synchronized public String vote(Voter v, int choiceInt) {
        if (checkKey(v) && choices.size() > choiceInt) {
            for (Choice c : choices) {
                c.getVotedBy().remove(v);
            }
            choices.get(choiceInt).voteFor(v);
            return "Vote for " + choices.get(choiceInt).getName() + " logged";
        }
        return "Vote not recorded";
    }

    synchronized public String unVote(Voter v) {
        if (checkKey(v)) {
            for (Choice c : choices) {
                if (c.getVotedBy().remove(v)) {
                    return "Your vote has been rescinded";
                }
            }
        }
        return "Vote not rescinded";
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
    public String addChoice(Voter v, String choiceName) throws RemoteException {
        if (checkKey(v)) {
            for (Choice c : choices) {
                if (c.getName().equals(choiceName)) {
                    return "Choice \"" + choiceName + "\" already exists";
                }
            }

            //Choice choice = new Choice(choiceName);
            choices.add(new Choice(choiceName));
            return "Choice \"" + choiceName + "\" added";
        } else {
            return "Choice not added";
        }
    }
}
