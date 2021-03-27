package corewar.ClientSide;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import corewar.ClientSide.EventInterface.onGameCancel;
import corewar.ClientSide.EventInterface.onGameStarting;
import corewar.ClientSide.EventInterface.onGameStop;
import corewar.ClientSide.EventInterface.onPlayerJoinGame;
import corewar.ClientSide.EventInterface.onPlayerLeftGame;
import corewar.Network.SocketCommunication;
import corewar.ObjectModel.Player;
import corewar.ObjectModel.PlayersRanking;
import corewar.ServerSide.Server;

public class GameCommunicationHandler extends Thread{

  private Socket socket;
  private ObjectOutputStream oos;
  private int gameID;

  private ArrayList<onPlayerJoinGame> playerJoinGameListeners = new ArrayList<>();
  private ArrayList<onGameCancel> gameCancelListeners = new ArrayList<>();
  private ArrayList<onPlayerLeftGame> playerLeftGameListeners = new ArrayList<>();
  private ArrayList<onGameStarting> gameStartingListeners = new ArrayList<>();
  private ArrayList<onGameStop> gameStopListeners = new ArrayList<>();

  public GameCommunicationHandler(int gameID) throws UnknownHostException, IOException {
    this.socket = new Socket(Server.ip,Server.port);
    this.gameID = gameID;
  }

  public void run(){
    try {
      oos = new ObjectOutputStream(socket.getOutputStream());
      this.send(new SocketCommunication(SocketCommunication.SUBSCRIBE_GAME_EVENT, gameID));
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      boolean shouldStop = false;
      while (!shouldStop){
        try{
          Object object = ois.readObject();
          SocketCommunication receivedObject = (SocketCommunication) object;
        
          if(receivedObject.getAPICallType()==SocketCommunication.END_COMM){
            shouldStop = true;
          }else{
            HandleIncomingObject(receivedObject);
          }
        }catch(EOFException e){
          
        }
      }
      ois.close();
      socket.close();
    }catch(IOException | ClassNotFoundException e){
      e.printStackTrace();
    }
  
  }

  public void send(SocketCommunication socketCommunication) throws IOException {
    oos.writeObject(socketCommunication);
  }
    
  private void HandleIncomingObject(SocketCommunication receivedObject){
    int InComingAPICallType = receivedObject.getAPICallType();
    Object InComingObject = receivedObject.getObject();
    
    switch(InComingAPICallType){
      case SocketCommunication.PLAYER_JOINED_GAME:{
        Player player = (Player) InComingObject;
        playerJoinedGameHandler(player);
        break;
      }
      case SocketCommunication.CANCEL_GAME:{
        gameCancelHandler();
        break;
      }
      case SocketCommunication.PLAYER_LEFT_GAME:{
        Player player = (Player) InComingObject;
        playerLeftGameHandler(player);
        break;
      }
      case SocketCommunication.GAME_STARTING:{
        gameStartingHandler();
        break;
      }
      case SocketCommunication.GAME_STOP:{
        PlayersRanking ranking = (PlayersRanking) InComingObject;
        gameStopHandler(ranking);
      }
    }
  }

  private void playerJoinedGameHandler(Player player){
    for(int x=0;x<this.playerJoinGameListeners.size();x++){
      this.playerJoinGameListeners.get(x).update(player);
    }
  }

  private void gameCancelHandler(){
    for(int x=0;x<this.gameCancelListeners.size();x++){
      this.gameCancelListeners.get(x).dothis();
    }
  }

  private void playerLeftGameHandler(Player player){
    for(int x=0;x<this.playerLeftGameListeners.size();x++){
      this.playerLeftGameListeners.get(x).update(player);
    }
  }

  private void gameStartingHandler(){
    for(int x=0;x<gameStartingListeners.size();x++){
      this.gameStartingListeners.get(x).dothis();
    }
  }

  private void gameStopHandler(PlayersRanking ranking){
    for(int x=0;x<gameStartingListeners.size();x++){
      this.gameStopListeners.get(x).dothis(ranking);
    }
  }

  public void onPlayerJoinGame(onPlayerJoinGame playerJoinGameListener){
    this.playerJoinGameListeners.add(playerJoinGameListener);
  }

  public void onGameCancel(onGameCancel gameCancelistener){
    this.gameCancelListeners.add(gameCancelistener);
  }

  public void onPlayerLeftGame(onPlayerLeftGame playerLeftGameListener){
    this.playerLeftGameListeners.add(playerLeftGameListener);
  }

  public void onGameStarting(onGameStarting onGameStartingListener){
    this.gameStartingListeners.add(onGameStartingListener);
  }

  public void onGameStop(onGameStop onGameStopListener){
    this.gameStopListeners.add(onGameStopListener);
  }

  public void joinGame(Player player) throws IOException {
    Object[] listObjects = {gameID,player};
    this.send(new SocketCommunication(SocketCommunication.PLAYER_JOIN_GAME, listObjects));
  }

  public void cancelGame() throws IOException{
    this.send(new SocketCommunication(SocketCommunication.CANCEL_GAME, gameID));
    this.send(new SocketCommunication(SocketCommunication.END_COMM, null));
  }

  public void leaveGame(Player player) throws IOException{
    Object[] listObjects = {gameID,player};
    this.send(new SocketCommunication(SocketCommunication.PLAYER_LEAVE_GAME, listObjects));
    this.send(new SocketCommunication(SocketCommunication.END_COMM, null));
  }

  public void startGame() throws IOException{
    this.send(new SocketCommunication(SocketCommunication.START_GAME, gameID));
  }
}