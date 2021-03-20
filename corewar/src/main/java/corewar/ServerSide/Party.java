package corewar.ServerSide;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

import corewar.ObjectModel.Player;
import corewar.ObjectModel.PlayersList;
import corewar.ObjectModel.PlayersRanking;
import corewar.Network.SocketCommunication;
import corewar.ObjectModel.EventsSubscriber;

public class Party implements Serializable {

    private static final long serialVersionUID = -6158548070086506523L;
    
    private Server server;
    private PlayersList playersList;
    private EventsSubscriber socketEventsSubscriber;
    private final int ID;

    public Party(Server server) {
        this.server = server;
        this.playersList = new PlayersList();
        this.socketEventsSubscriber = new EventsSubscriber();
        this.ID = IDGenerator.get();
    }

    public void onPlayerJoin(Socket socketToExcept, Player player) {
        playersList.add(player);
        try {
            socketEventsSubscriber.sendAllExceptOne(new SocketCommunication(SocketCommunication.PLAYER_JOINED_PARTY, player), socketToExcept);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {

    }

    public void cancel(Socket socketToExcept) {
        try {
            socketEventsSubscriber.sendAllExceptOne(new SocketCommunication(SocketCommunication.CANCEL_PARTY, null),socketToExcept);
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
        try {
            socketEventsSubscriber.closeAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void subscribeEvent(Socket socket){
        socketEventsSubscriber.add(socket);
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