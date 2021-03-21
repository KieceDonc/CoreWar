package corewar.ClientSide;

import java.io.IOException;

import corewar.ClientSide.EventInterface.onPartyCancel;
import corewar.ClientSide.EventInterface.onPlayerJoinParty;
import corewar.Network.SocketCommunication;
import corewar.ObjectModel.Player;
import corewar.ObjectModel.PlayersList;
import corewar.Read.ThreadReadI;

public class Party extends Thread {

    private PartyCommunicationHandler partyCommunicationHandler;
    private PlayersList playersList;
    private Player currentPlayer;
    private ThreadReadI threadReadChoice;
    private boolean isHost = false; // security issue

    private final int ID;

    public Party(int ID) {
        this.ID = ID;
        try {
            this.partyCommunicationHandler = new PartyCommunicationHandler(this.ID);
            this.partyCommunicationHandler.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fatal error");
            System.exit(0);
        }
    }

    // player joining party constructor
    public Party(int ID, Player currentPlayer, PlayersList playersList) {
        this(ID);
        this.currentPlayer = currentPlayer;
        this.playersList = playersList;
    }

    // host constructor
    public Party(int ID, boolean isHost, Player currentPlayer) {
        this(ID);
        this.isHost = isHost;
        this.currentPlayer = currentPlayer;
        this.playersList = new PlayersList();
        this.playersList.add(currentPlayer);
    }

    public void run() {
        partyCommunicationHandler.onPlayerJoinParty(new onPlayerJoinParty() {

            @Override
            public void update(Player player) {
                if (threadReadChoice != null) {
                    threadReadChoice.interrupt();
                }
                playersList.add(player);
                printWaitingMenu();
            }
        });

        Thread currentThread = this;
        partyCommunicationHandler.onPartyCancel(new onPartyCancel() {

            @Override
            public void dothis() {
                if (threadReadChoice != null) {
                    threadReadChoice.interrupt();
                }
                System.out.println("------------------------------------------------------------------------------------------");
                System.out.println("");
                System.out.println("Partie annulée");
                System.out.println("");
                System.out.println("------------------------------------------------------------------------------------------");
                currentThread.interrupt();
            }
        });

        printWaitingMenu();
    }

    private void printWaitingMenu() {
        boolean shouldPrintAgain = false;
        int choice = 0;
        do {
            System.out.println(
                    "------------------------------------------------------------------------------------------");
            System.out.println("");
            System.out.println("Partie ID : " + this.getID());
            System.out.println("");
            System.out.println("Liste des joueurs : " + playersList.getSize());
            System.out.println("");
            for (int x = 0; x < playersList.getSize(); x++) {
                Player currentPlayerInList = playersList.getByIndex(x);
                System.out.println("Joueur " + x + " : " + currentPlayerInList.getName() + " ( Programme : "
                        + currentPlayerInList.getProgram().getName() + " )");
            }
            System.out.println("");
            System.out.println("Options :");
            if (isHost) {
                System.out.println("    1- Démarrer la partie");
                System.out.println("    2- Annuler la partie");
            } else {
                System.out.println("    1- Quitter la partie");
            }
            System.out.println("");
            System.out.print("Votre choix : ");
            choice = readChoice();
            System.out.println("");
            System.out.println("------------------------------------------------------------------------------------------");
            if(isHost){
                shouldPrintAgain = choice<1||choice>2;
            }else{
                shouldPrintAgain = choice!=1;
            }
        } while (shouldPrintAgain);
        switch (choice) {
            case 1: {
                if(isHost){
                    startParty();
                }else{
                    leave();
                }
                break;
            }
            case 2: {
                if(isHost){
                    cancel();
                }
                break;
            }
            default: {
                System.out.println("wtf, unhandled choice, current choice = " + choice);
                System.out.println("Normally it happend when you don't incremente maxChoice in clientMainMenu()");
                System.exit(0);
            }
        }
    }

    private void startParty(){
        /// TODO handle when host when to start party
        // don't forget to check is playersList.size()>=2
        System.out.println("TODO");
    }

    private void leave(){
        // TODO handle when user when to leave "waiting screen"
        System.out.println("TODO");
    }

    private void cancel(){
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("Partie annulée");
        System.out.println("");
        System.out.println("------------------------------------------------------------------------------------------");
        try {
            partyCommunicationHandler.cancelParty();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.interrupt();
    }

    public int getID() {
        return this.ID;
    }

    public static Party create(Player currentPlayer) {
        Connexion connexion;
        try {
            connexion = new Connexion(new SocketCommunication(SocketCommunication.CREATE_PARTY, currentPlayer));
            connexion.start();
            connexion.join();
            return new Party((int) connexion.getReceivedObject(), true, currentPlayer);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Fatal error, failed to create party");
            System.exit(0);
        }
        return null;
    }

    public static Party join(Player currentPlayer, int partyID){
        Connexion connexion;
        try {
            Object[] allObject = {partyID,currentPlayer};
            connexion = new Connexion(new SocketCommunication(SocketCommunication.PLAYER_JOIN_PARTY, allObject));
            connexion.start();
            connexion.join();
            return new Party(partyID, currentPlayer,(PlayersList) connexion.getReceivedObject());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Fatal error, failed to create party");
            System.exit(0);
        }
        return null;
    }

    private int readChoice(){
        try {        
            int choice = 0;
            threadReadChoice = new ThreadReadI();
            threadReadChoice.start();
            threadReadChoice.join();
            choice = threadReadChoice.call();
            threadReadChoice = null;
            return choice;
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Fatal error, couldn't read choice");
            System.exit(0);
        }
        return -1;
    }
}