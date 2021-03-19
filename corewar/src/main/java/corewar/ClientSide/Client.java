package corewar.ClientSide;

import java.io.IOException;

import corewar.Lire;
import corewar.Network.SocketCommunication;
import corewar.ObjectModel.Player;
import corewar.ObjectModel.PlayersRanking;

public class Client {

  private Player currentPlayer;

  public Client() {
    initPlayer();
    mainMenu();
  }

  private void initPlayer(){
    String playerName = "";
    try{
      boolean isPlayerNameTaken = false;
      boolean incorrectPlayerName = false;
      do{
        if(isPlayerNameTaken){
          System.out.println("Pseudo déjà prit, dommage :(\nVeuillez en prendre un nouveau");
        }else if(incorrectPlayerName){
          System.out.println("Pseudo incorrect :(\nVeuillez en prendre un nouveau");
        }
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.print("Veuillez choisir un pseudo : ");
        playerName = Lire.S();
        System.out.println("");
        System.out.println("------------------------------------------------------------------------------------------");
        incorrectPlayerName = playerName.length()<=0;
        if(!incorrectPlayerName){
          isPlayerNameTaken = isPlayerNameTaken(playerName);
        }
      }while(isPlayerNameTaken || incorrectPlayerName);
    }catch(Exception e){
      e.printStackTrace();
    }
    currentPlayer = new Player(playerName);
  }

  public void mainMenu(){
    int choice = 0;
    int maxChoice = 4;
    do{
      System.out.println("------------------------------------------------------------------------------------------");
      System.out.println("");
      System.out.println("1 - Crée une partie");
      System.out.println("2 - Rejoindre une partie");
      System.out.println("3 - Voir le classement des joueurs");
      System.out.println("4 - Fermer");
      System.out.println("");
      System.out.print("Votre choix : ");
      choice = Lire.i();
      System.out.println("");
      System.out.println("------------------------------------------------------------------------------------------");    
    }while(choice>maxChoice || choice<1);
    
    switch(choice){
      case 1:{
        createParty();
        break;
      }
      case 2:{
        break;
      }
      case 3:{
        getRanking().print();
        break;
      }
      case 4:{
        System.exit(0);
        break;
      }
      default:{
        System.out.println("wtf, unhandled choice, max choice ="+maxChoice+", current choice = "+choice);
        System.out.println("Normally it happend when you don't incremente maxChoice in clientMainMenu()");
        System.exit(0);
      }
    };

    mainMenu();
  }

  public void createParty(){
    try{
      Party party = Party.create(currentPlayer);
      party.start();
      party.join();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public boolean isPlayerNameTaken(String playerName){
    try {
      Connexion connexion = new Connexion(new SocketCommunication(SocketCommunication.IS_PLAYER_NAME_TAKEN, playerName));
      connexion.start();
      connexion.join();
      System.out.println(connexion.getReceivedObject().toString());
      return (boolean)connexion.getReceivedObject();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Erreur fatal dans la fonction choseName()");
    System.exit(0);
    return true;
  }

  public PlayersRanking getRanking() {
    try {
      Connexion connexion = new Connexion(new SocketCommunication(SocketCommunication.GET_RANKING, null));
      connexion.start();
      connexion.join();
      return (PlayersRanking)connexion.getReceivedObject();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    return null;
  }
}