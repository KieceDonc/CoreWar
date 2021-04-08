package corewar.ClientSide;

import java.io.IOException;

import corewar.Read;
import corewar.ClientSide.EventInterface.onGameCancel;
import corewar.ClientSide.EventInterface.onGameStarting;
import corewar.ClientSide.EventInterface.onGameStop;
import corewar.ClientSide.EventInterface.onGameUpdate;
import corewar.ClientSide.EventInterface.onPlayerJoinGame;
import corewar.ClientSide.EventInterface.onPlayerLeftGame;
import corewar.Network.SocketCommunication;
import corewar.ObjectModel.Player;
import corewar.ObjectModel.PlayersList;
import corewar.ObjectModel.PlayersRanking;

public class Game extends Thread {

    private GameCommunicationHandler gameCommunicationHandler;
    private PlayersList playersList;
    private Player currentPlayer;
    private boolean isHost = false; // security issue
    private boolean stop = false;
    private boolean gameHasStart = false;
    private boolean gameHasFinish = false;

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

        gameCommunicationHandler.onGameStarting(new onGameStarting(){
        
            @Override
            public void dothis() {
                gameHasStart = true;
                System.out.println("");
                System.out.println("");
                begin();
            }
        });

        gameCommunicationHandler.onGameStop(new onGameStop(){
        
            @Override
            public void dothis(PlayersRanking ranking) {
                end(ranking);
                gameHasFinish = true;
            }
        });

        gameCommunicationHandler.onGameUpdate(new onGameUpdate(){
        
            @Override
            public void dothis(String status) {
                update(status);
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
        gameCommunicationHandler.endCom();
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
            Player currentPlayerInList = playersList.get(x);
            String toPrint = "";
            if(x==0){
                toPrint+="-- Hôte de la partie --";
            }else{
                toPrint+="--    Joueur n° "+(x+1)+"    --";
            }
            toPrint+= " "+currentPlayerInList.getName() + " ( Warrior : " + currentPlayerInList.getWarrior().getNom() + " )";
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
            if(gameHasStart){
                stop = true;
            }

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
        if(playersList.getSize()<2){
            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println("");
            System.out.println("Impossible de lancer la partie, pas assez de joueur");
            System.out.println("");
            System.out.println("------------------------------------------------------------------------------------------");
        }else{
            try {
                gameCommunicationHandler.startGame();
                gameHasStart = true;
                begin();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    private void begin(){
        if(isHost){
            do{
                System.out.println("------------------------------------------------------------------------------------------");
                System.out.println("");
                System.out.println("La partie commence, vous ne pouvez pas quitter la partie");
                System.out.println("( il s'agit d'un bug que je n'ai toujours pas trouvé qui empêche l'host de partir )");
                System.out.println("( La requête pour partir est bien envoyé mais n'est reçu qu'à la fin de game du côté serveur )");
                System.out.println("( Hors socket.close() est appelé avant du côté client et fait tout planté puisque le serveur continue d'envoyer des données )");
                System.out.println("( Si vous voyez ceci je n'ai pas réussi à fix bug, j'ai déjà passer 10h dessus et j'en ai marre )");
                System.out.println("( Donc l'host n'a pas le droit de quitter la game )");
                System.out.println("");
                System.out.println("------------------------------------------------------------------------------------------");
                Read.i();
            }while(!gameHasFinish);
            stop = true;
        }else{
            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println("");
            System.out.println("La partie commence, vous pouvez appuyer sur n'importe qu'elle touche d'un entier pour quitter la partie");
            System.out.println("Votre score de cette partie sera tout de même comptabilisé");
            System.out.println("");
            System.out.println("------------------------------------------------------------------------------------------");
        }
    }

    private void update(String status){
        System.out.println(status);
    }

    private void end(PlayersRanking ranking){
        ranking.print();
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("La partie est terminé, vous pouvez appuyer sur n'importe qu'elle touche d'un entier pour quitter la partie");
        System.out.println("");
        System.out.println("------------------------------------------------------------------------------------------");
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
            if(connexion.getReceivedObject() instanceof Integer){
                System.out.println("------------------------------------------------------------------------------------------");
                System.out.println("");
                System.out.println("Trop tard, la partie a déjà commencé");
                System.out.println("");
                System.out.println("------------------------------------------------------------------------------------------");
                return null;
            }else{
                return new Game(gameID, currentPlayer, (PlayersList) connexion.getReceivedObject());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Fatal error, failed to create game");
            System.exit(0);
        }
        return null;
    }
}