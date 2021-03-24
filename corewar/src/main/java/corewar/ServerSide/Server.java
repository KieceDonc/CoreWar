package corewar.ServerSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

import corewar.Main;
import corewar.ObjectModel.PlayersRanking;
import corewar.Read;

/*
  Représente le serveur qui reçoit / envoit des données
  Il est également chargé d'exécuté les parties / s'occuper des connexions / gérer les appels de l'api
  Son ip et son port sont les deux premiers attributs de cette classe
*/
public class Server{

  public static final String ip = "localhost";
  public static final int port = 5487;
  
  private GameList gameList = new GameList();
  private PlayersRanking ranking = new PlayersRanking();
  private ServerSocket server;

  private boolean stopServer = false;

  public Server() {
    apiHandler();
    stopHandler();
  }

  /*
    Méthode qui gère l'arriver de toutes nouvelles connexion
    Pour cela elle démarre le thread APIHandler 
  */
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
          Read.S();
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

  public GameList getGameList(){
    return this.gameList;
  }

  public PlayersRanking getRanking(){
    return this.ranking;
  }

}
