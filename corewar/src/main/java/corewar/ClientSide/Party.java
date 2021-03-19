package corewar.ClientSide;

import java.io.IOException;

import corewar.Lire;
import corewar.Network.SocketCommunication;
import corewar.ObjectModel.Player;
import corewar.ObjectModel.PlayersList;

public class Party extends Thread {

    private PlayersList playersList;
    private Player currentPlayer;
    private boolean isHost = false; // security issue
    
    private final int ID;

    // player joining party constructor
    public Party(int ID, Player currentPlayer, PlayersList playersList){
        this.ID=ID;
        this.currentPlayer = currentPlayer;
        this.playersList = playersList;
    }

    // host constructor
    public Party(int ID, boolean isHost, Player currentPlayer){
        this.ID=ID;
        this.isHost = isHost;
        this.currentPlayer = currentPlayer;
        this.playersList = new PlayersList();
    }

    public void run(){
        if(isHost){
            showHostMenuInWaitingMenu();
        }else{
            showNormalMenuInWaitMenu();
        }
    }

    public void showHostMenuInWaitingMenu(){
        int maxChoice = 2;
        int choice = 0;
        do{
            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println("");
            System.out.println("Partie ID : "+this.getID());
            System.out.println("");
            System.out.println("Liste des joueurs : "+playersList.getSize());
            System.out.println("");
            for(int x=0;x<playersList.getSize();x++){
                Player currentPlayerInList = playersList.getByIndex(x);
                System.out.println("Joueur "+x+" : "+currentPlayerInList.getName()+" ( Programme : "+currentPlayerInList.getProgram().getName()+" )");
            }
            System.out.println("");
            System.out.println("Options :");
            System.out.println("    1- Démarrer la partie");
            System.out.println("    2- Annuler la partie");
            System.out.println("");
            System.out.print("Votre choix : ");
            choice = Lire.i();
            System.out.println("");
            System.out.println("------------------------------------------------------------------------------------------");    
        }while(choice<1 || choice>maxChoice);
        switch(choice){
            case 1:{
              break;
            }
            case 2:{
                break;
            }
            default:{
              System.out.println("wtf, unhandled choice, max choice ="+maxChoice+", current choice = "+choice);
              System.out.println("Normally it happend when you don't incremente maxChoice in clientMainMenu()");
              System.exit(0);
            }
        }
    }

    public void showNormalMenuInWaitMenu(){

    }

    public int getID(){
        return this.ID;
    }
    

    public static Party create(Player currentPlayer){
        Connexion connexion;
        try {
            connexion = new Connexion(new SocketCommunication(SocketCommunication.CREATE_PARTY, null));
            connexion.start();
            connexion.join();
            return new Party((int)connexion.getReceivedObject(),true,currentPlayer);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Fatal error, failed to create party");
            System.exit(0);
        }
        return null;
    }
}