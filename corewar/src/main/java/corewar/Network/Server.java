package corewar.Server;

import java.io.IOException;
import java.net.ServerSocket;

import corewar.ObjectModel.PlayerRanking;

public class Server{

  public static String ip = "localhost";
  public static int port = 5487;

  private PlayerRanking ranking = new PlayerRanking();
  
  public Server(){
    try{
      ServerSocket serveur = new  ServerSocket(port);
      while(true){
        Connexion connexion = new  Connexion(serveur.accept());
        connexion.start();
      }
    }catch(IOException e){
      e.printStackTrace();
    }
  }

/*
public void partyStart(Party party){
    if(party.getPlayersList().getSize()>1){
      party.start();
    }
  }

  public void onPartyEnded(Party party){
    
  }

  public void onPlayerConnected(Player player){
    if(!ranking.isInList(player)){
      ranking.add(player);
    }
  }

  public void onPlayerSubmitProgram(Player player, Program program){
    player.setProgram(program);
  }
*/
}
