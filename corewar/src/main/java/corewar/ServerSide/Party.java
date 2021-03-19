package corewar.ServerSide;

import corewar.ObjectModel.Player;
import corewar.ObjectModel.PlayersList;
import corewar.ObjectModel.PlayersRanking;

public class Party{

    private Server server;
    private PlayersList playersList;
    private final int ID; 

    public Party(Server server){
        this.server = server;
        this.playersList = new PlayersList();
        this.ID=IDGenerator.get();
    }

    public void onPlayerJoin(Player player){
        player.resetPartyScore();
    }
    public void start(){

    }

    public void cancel(){

    }

    public void onEnd(){
        PlayersRanking playersRanking = server.getRanking();
        for(int x=0;x<playersList.getSize();x++){
            Player currentPlayer = playersList.getByIndex(x);
            if(playersRanking.isInList(currentPlayer)){
                playersRanking.get(currentPlayer).updateScore();
            }else{
                playersRanking.add(currentPlayer);
            }
        }
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