package distributedVote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erik Storla <estorla42@gmail.com>
 */
public class Message extends UnicastRemoteObject implements VoterInterface {

    static int count = 1;
    static String question = "";
    //static Server server;
    public static ArrayList<Voter> voterList;
    public static ArrayList<Choice> choices;
    private static boolean endVote = false;

    @Override
    public String printMenu(Voter v) throws RemoteException {
        if (endVote) {
            return finalMessage(v);
        }

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
            if (v.id == 1) {
                temp += "D: End voting\n";
            }

            temp += "0: Exit\n";
            temp += "Please make a selection: ";
        }
        return temp;
    }

    public Message(Server server) throws RemoteException {
        choices = new ArrayList<>();
        voterList = new ArrayList<>();

//        System.out.print("Starting votepusher...");
//        new Thread(new VotePusher()).start();
//        System.out.println("started");
        //Message.server = server;
        //voterList = server.voters;
        //choices = server.choices;
    }

    @Override
    public String endVoting(Voter v) {
        if (!checkKey(v)) {
            return "Invalid credentials!";
        } else if (v.id != 1) {
            return "You cannot end the voting!";
        } else {
            endVote = true;
            for (Voter voter : voterList) {
                try {
                    voter.endVoting();
                } catch (RemoteException ex) {
                    Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return "Ending voting...";
        }

    }

    @Override
    public String finalMessage(Voter v) throws RemoteException {
        if (!checkKey(v)) {
            return "Invalid credentials!";
        }
        String temp = "";
        temp += "Voting has ended!\n\n";
        temp += question + "\n";
        temp += getVotes(v);

        return temp;
    }

    @Override
    public String setQuestion(Voter v, String question) {
        if (!checkKey(v)) {
            return "Invalid credentials!";
        } else if (v.id != 1) {
            return "You cannot set the question!";
        } else if (Message.question.length() != 0) {
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
    synchronized public Voter login(String pass, Client client) throws RemoteException {
        if (pass.equals("pass")) {
            double key = Math.random();
            Voter temp = new Voter(count++, key, client);
            voterList.add(temp);
            return temp;
        } else {
            return null;
        }
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

//    public static class VotePusher implements Runnable {
//
//        @Override
//        public synchronized void run() {
//            while (true) {
//                Iterator it = voterList.iterator();
//                while (it.hasNext()) {
//                    try {
//                        ((Voter) it.next()).client.pushVotes(choices);
//                        System.out.println("pushed votes");
//                    } catch (RemoteException e) {
//
//                    }
//                }
//            }
//        }
//    }
}
