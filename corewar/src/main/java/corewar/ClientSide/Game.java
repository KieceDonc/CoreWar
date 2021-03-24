package corewar.ClientSide;

import java.io.IOException;

import corewar.Read;
import corewar.ClientSide.EventInterface.onGameCancel;
import corewar.ClientSide.EventInterface.onPlayerJoinGame;
import corewar.ClientSide.EventInterface.onPlayerLeftGame;
import corewar.Network.SocketCommunication;
import corewar.ObjectModel.Player;
import corewar.ObjectModel.PlayersList;

public class Game extends Thread {

    private GameCommunicationHandler gameCommunicationHandler;
    private PlayersList playersList;
    private Player currentPlayer;
    private boolean isHost = false; // security issue
    private boolean stop = false;

    private final int ID;

    public Game(int ID) {
        this.ID = ID;
        try {
            this.gameCommunicationHandler = new GameCommunicationHandler(this.ID);
            this.gameCommunicationHandler.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fatal error");
            System.exit(0);
        }

        gameCommunicationHandler.onPlayerJoinGame(new onPlayerJoinGame() {

            @Override
            public void update(Player player) {
                playersList.add(player);
                System.out.println("");
                System.out.println("");
                printWaitingMenuFirstPart();
            }
        });

        gameCommunicationHandler.onGameCancel(new onGameCancel() {

            @Override
            public void dothis() {
                System.out.println("");
                System.out.println("");
                System.out.println("------------------------------------------------------------------------------------------");
                System.out.println("");
                System.out.println("Partie annulée");
                System.out.println("Appuyer sur n'importe quel entier pour continuer");
                System.out.println("");
                System.out.println("------------------------------------------------------------------------------------------");
                stop = true;
            }
        });

        gameCommunicationHandler.onPlayerLeftGame(new onPlayerLeftGame(){
        
            @Override
            public void update(Player player) {
                playersList.remove(player);
                System.out.println("");
                System.out.println("");
                printWaitingMenuFirstPart();
            }
        });
    }

    // player joining game constructor
    public Game(int ID, Player currentPlayer, PlayersList playersList) {
        this(ID);
        this.currentPlayer = currentPlayer;
        this.playersList = playersList;
    }

    // host constructor
    public Game(int ID, boolean isHost, Player currentPlayer) {
        this(ID);
        this.isHost = isHost;
        this.currentPlayer = currentPlayer;
        this.playersList = new PlayersList();
        this.playersList.add(currentPlayer);
    }

    public void run() {
        while(!stop){
            printWaitingMenu();
        }
    }

    private void printWaitingMenuFirstPart(){
        System.out.println(
            "------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("Partie ID : " + this.getID());
        System.out.println("");
        System.out.println("Liste des joueurs : " + playersList.getSize());
        System.out.println("");
        for (int x = 0; x < playersList.getSize(); x++) {
            Player currentPlayerInList = playersList.getByIndex(x);
            String toPrint = "";
            if(x==0){
                toPrint+="-- Hôte de la partie --";
            }else{
                toPrint+="--    Joueur n° "+(x+1)+"    --";
            }
            toPrint+= " "+currentPlayerInList.getName() + " ( Programme : " + currentPlayerInList.getProgram().getName() + " )";
            System.out.println(toPrint);
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
    }

    private void printWaitingMenu() {
        boolean shouldPrintAgain = false;
        int choice = 0;
        do {
            printWaitingMenuFirstPart();
            choice = Read.i();

            if(!stop){
                System.out.println("");
                System.out.println("------------------------------------------------------------------------------------------");
                if (isHost) {
                    shouldPrintAgain = choice < 1 || choice > 2;
                } else {
                    shouldPrintAgain = choice != 1;
                }
            }else{
                // On a reçu l'ordre d'arrêter la partie mais on attendait que le joueur appuit sur une touche donc on sort
                shouldPrintAgain = false;
            }
        } while (shouldPrintAgain);

        if(!stop){
            switch (choice) {
                case 1: {
                    if (isHost) {
                        startGame();
                    } else {
                        leave();
                    }
                    break;
                }
                case 2: {
                    if (isHost) {
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
    }

    private void startGame() {
        /// TODO handle when host when to start game
        // don't forget to check is playersList.size()>=2
        System.out.println("TODO");
    }

    private void leave() {
        stop = true;
        try {
            gameCommunicationHandler.leaveGame(currentPlayer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cancel() {
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("Partie annulée");
        System.out.println("");
        System.out.println("------------------------------------------------------------------------------------------");
        try {
            gameCommunicationHandler.cancelGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stop = true;
    }

    public int getID() {
        return this.ID;
    }

    public static Game create(Player currentPlayer) {
        Connexion connexion;
        try {
            connexion = new Connexion(new SocketCommunication(SocketCommunication.CREATE_GAME, currentPlayer));
            connexion.start();
            connexion.join();
            return new Game((int) connexion.getReceivedObject(), true, currentPlayer);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Fatal error, failed to create game");
            System.exit(0);
        }
        return null;
    }

    public static Game join(Player currentPlayer, int gameID) {
        Connexion connexion;
        try {
            Object[] allObject = { gameID, currentPlayer };
            connexion = new Connexion(new SocketCommunication(SocketCommunication.PLAYER_JOIN_GAME, allObject));
            connexion.start();
            connexion.join();
            return new Game(gameID, currentPlayer, (PlayersList) connexion.getReceivedObject());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Fatal error, failed to create game");
            System.exit(0);
        }
        return null;
    }
}