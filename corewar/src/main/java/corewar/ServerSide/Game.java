package corewar.ServerSide;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import corewar.ObjectModel.Player;
import corewar.ObjectModel.PlayersList;
import corewar.ObjectModel.PlayersRanking;
import corewar.Network.SocketCommunication;
import corewar.ObjectModel.EventsSubscriber;

public class Game implements Serializable {

    private static final long serialVersionUID = -6158548070086506523L;
    
    private Server server;
    private PlayersList playersList;
    private EventsSubscriber socketEventsSubscriber;
    private final int ID;

    public Game(Server server) {
        this.server = server;
        this.playersList = new PlayersList();
        this.socketEventsSubscriber = new EventsSubscriber();
        this.ID = IDGenerator.get();
    }

    public void onPlayerJoin(ObjectOutputStream oosToExcept, Player player) {
        playersList.add(player);
        try {
            socketEventsSubscriber.sendAllExceptOne(new SocketCommunication(SocketCommunication.PLAYER_JOINED_GAME, player), oosToExcept);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onPlayerLeave(ObjectOutputStream oosToExcept, Player player){
        playersList.remove(player);
        socketEventsSubscriber.remove(oosToExcept);
        try {
            socketEventsSubscriber.sendAll(new SocketCommunication(SocketCommunication.PLAYER_LEFT_GAME, player));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {

    }

    public void cancel(ObjectOutputStream oosToExcept) {
        try {
            socketEventsSubscriber.sendAllExceptOne(new SocketCommunication(SocketCommunication.CANCEL_GAME, null),oosToExcept);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onEnd() {
        PlayersRanking playersRanking = server.getRanking();
        for (int x = 0; x < playersList.getSize(); x++) {
            Player currentPlayer = playersList.getByIndex(x);
            if (playersRanking.isInList(currentPlayer)) {
                playersRanking.get(currentPlayer).updateScore();
            } else {
                playersRanking.add(currentPlayer);
            }
        }
    }

    public void subscribeEvent(ObjectOutputStream oos){
        socketEventsSubscriber.add(oos);
    }

    public PlayersList getPlayersList(){
        return this.playersList;
    }

    public int getID(){
        return this.ID;
    }

    public static class IDGenerator{

        public static int ID=0;

        public static synchronized int get(){
            ID+=1;
            return ID;
        }
    }
}