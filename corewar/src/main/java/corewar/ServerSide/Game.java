package corewar.ServerSide;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeUnit;

import corewar.ObjectModel.Player;
import corewar.ObjectModel.PlayersList;
import corewar.ObjectModel.PlayersRanking;
import corewar.Network.SocketCommunication;
import corewar.Network.EventsSubscriber;

public class Game{

    private Server server;
    private PlayersList playersList;
    private EventsSubscriber socketEventsSubscriber;
    private final int ID;
    private boolean hasStart = false;

    public Game(Server server) {
        this.server = server;
        this.playersList = new PlayersList();
        this.socketEventsSubscriber = new EventsSubscriber();
        this.ID = IDGenerator.get();
    }

    public void onPlayerJoin(ObjectOutputStream oosToExcept, Player player) {
        playersList.add(player);
        try {
            socketEventsSubscriber.sendAllExceptOne(
                    new SocketCommunication(SocketCommunication.PLAYER_JOINED_GAME, player), oosToExcept);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onPlayerLeave(ObjectOutputStream oosToExcept, Player player) {
        playersList.remove(player);
        socketEventsSubscriber.remove(oosToExcept);
        try {
            socketEventsSubscriber.sendAll(new SocketCommunication(SocketCommunication.PLAYER_LEFT_GAME, player));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(ObjectOutputStream oosToExcept) {
        hasStart = true;
        try {
            socketEventsSubscriber.sendAllExceptOne(new SocketCommunication(SocketCommunication.GAME_STARTING, null),
                    oosToExcept);
        } catch (IOException e) {
            e.printStackTrace();
        }
        main();
    }

    public void main() {
        try {
            for (int x = 0; x < 10; x++) {
                TimeUnit.SECONDS.sleep(1);
                String currentStatus ="------------------------------------------------------------------------------------------\n";
                currentStatus+="\n";
                currentStatus+="Mise à jour n°"+x+"\n";
                currentStatus+="\n";
                currentStatus+="------------------------------------------------------------------------------------------";
                socketEventsSubscriber.sendAll(new SocketCommunication(SocketCommunication.GAME_UPDATE, currentStatus));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        onEnd();
    }

    public void onEnd() {

        PlayersRanking playersRanking = server.getRanking();
        for (int x = 0; x < playersList.getSize(); x++) {
            Player currentPlayer = playersList.getByIndex(x);
            playersRanking.get(currentPlayer).setGameScore((int)(Math.random()*100));
            playersRanking.get(currentPlayer).updateScore();
        }

        try {
            socketEventsSubscriber.sendAll(new SocketCommunication(SocketCommunication.GAME_STOP, server.getRanking()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.getGameList().remove(this);
    }

    public void cancel(ObjectOutputStream oosToExcept) {
        try {
            socketEventsSubscriber.sendAllExceptOne(new SocketCommunication(SocketCommunication.CANCEL_GAME, null),oosToExcept);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void subscribeEvent(ObjectOutputStream oos){
        socketEventsSubscriber.add(oos);
    }

    public void unsubscribeEvent(ObjectOutputStream oos){
        socketEventsSubscriber.remove(oos);
    }

    public PlayersList getPlayersList(){
        return this.playersList;
    }

    public int getID(){
        return this.ID;
    }

    public boolean hasStart(){
        return this.hasStart;
    }

    public static class IDGenerator{

        public static int ID=0;

        public static synchronized int get(){
            ID+=1;
            return ID;
        }
    }
}