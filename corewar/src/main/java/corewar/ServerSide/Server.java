package corewar.ServerSide;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;

import corewar.Lire;
import corewar.Main;
import corewar.ObjectModel.PlayerRanking;

public class Server {

  public static final String ip="localhost";
  public static final int port = 5487;

  private PlayerRanking ranking = new PlayerRanking();
  private ServerSocket server;

  private boolean stopServer = false;

  public Server() {
    apiHandler();
    stopHandler();
  }

  public void apiHandler() {
    Server thisServer = this;
    new Thread(new Runnable() {

      @Override
      public void run() {
        try {
          server = new ServerSocket(port);
          while (!stopServer) {
            APIHandler apiHandler = new APIHandler(thisServer, server.accept());
            apiHandler.start();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  public void stopHandler() {
    Thread stopHandler = new Thread(new Runnable() {

      @Override
      public void run() {
        try {
          System.out.println("Appuyer sur entrée pour étiendre le serveur");
          Lire.S();
          stopServer = true;
          server.close();
        } catch (SocketException e){
          /* 
            On arrête le server alors qu'il attend d'accepter de nouveau stocket
            Cela va afficher une erreur
            Pour éviter cela on ajoute ce bloc 
            Comme ça on a l'impression qu'on a bien codé et aucune erreur apparaît 
            TODO fix SocketException cuz while u close the server, he's waiting to accept new socket
          */
        } catch (IOException e) {
          e.printStackTrace();
        }
        Main.printLogo();
      }
    });

    try {
      stopHandler.start();
      stopHandler.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public PlayerRanking getRanking(){
    return this.ranking;
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
