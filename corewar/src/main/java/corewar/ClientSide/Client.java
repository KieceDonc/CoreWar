package corewar.ClientSide;

import java.io.IOException;
import java.net.Socket;

import corewar.Network.SocketCommunication;
import corewar.ObjectModel.PlayerRanking;
import corewar.ServerSide.Server;

public class Client {

  private Socket socket;

  public Client() {
    initSocket();
    System.out.println(getRanking());
  }

  public void initSocket(){
    try {
      socket = new Socket(Server.ip, Server.port);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public PlayerRanking getRanking() {
    try {
      Connexion connexion = new Connexion(socket, new SocketCommunication(SocketCommunication.GET_RANKING, null));
      connexion.start();
      connexion.join();
      return (PlayerRanking)connexion.getReceivedObject();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    return null;
  }
}