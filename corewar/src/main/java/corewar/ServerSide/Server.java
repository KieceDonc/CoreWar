package corewar.ServerSide;

import java.io.IOException;
import java.net.ServerSocket;

import corewar.Main;
import corewar.ObjectModel.Rankings;
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
  private Rankings rankings = new Rankings();
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
    boolean ok = this.getRankings().loadWarriorRankings();
    if(ok) System.out.println("Classement des warriors chargé");
    else System.out.println("Echec du chargement du classement des warriors (Normal si premier lancement du serveur)");
    
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
          System.out.println("Appuyer sur entrée pour éteindre le serveur");
          Read.S();
          stopServer = true;
          boolean ok = getRankings().saveWarriorRankings();
          if(ok) System.out.println("Sauvegarde des Warriors effectuée");
          else System.out.println("Echec de la sauvegarde des Warriors");
          server.close();
        } catch (Exception e){

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

  public Rankings getRankings(){
    return this.rankings;
  }

}
