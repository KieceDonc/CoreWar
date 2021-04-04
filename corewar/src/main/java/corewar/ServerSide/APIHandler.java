package corewar.ServerSide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import corewar.Network.SocketCommunication;
import corewar.ObjectModel.Player;
import corewar.ObjectModel.PlayersList;
import corewar.ObjectModel.Program;
import corewar.ObjectModel.ProgramRanking;

public class APIHandler extends Thread{
  
  private Server server;
  private Socket socket;
  private ObjectOutputStream oos;
  
  public APIHandler(Server server, Socket socket) throws IOException {
    this.server = server;
    this.socket = socket;
  }
  
  public void run(){
    try {
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      oos = new ObjectOutputStream(socket.getOutputStream());
      boolean shouldStop = false;
      while (!shouldStop){
        Object object = ois.readObject();
        SocketCommunication receivedObject = (SocketCommunication) object;
        if(receivedObject!=null){
          if(receivedObject.getAPICallType()==SocketCommunication.END_COMM){
            shouldStop = true;
            if(receivedObject.getObject()!=null){
              int gameID = (int)receivedObject.getObject();
              Game currentGame = server.getGameList().getByID(gameID);
              if(currentGame!=null && currentGame.hasStart()){
                currentGame.unsubscribeEvent(this.oos);
              }
            }
          }else{
            HandleIncomingObject(receivedObject);
          }
        }
      }
      oos.close();
      ois.close();
      socket.close();
    }catch(IOException | ClassNotFoundException e){
      e.printStackTrace();
    }
  }

  private void HandleIncomingObject(SocketCommunication receivedObject){
    int InComingAPICallType = receivedObject.getAPICallType();
    Object InComingObject = receivedObject.getObject();
    switch(InComingAPICallType){
      case SocketCommunication.GET_RANKING:{
        getRankingHandler(InComingAPICallType);
        break;
      }
      case SocketCommunication.IS_PLAYER_NAME_TAKEN:{
        isPlayerNameTakenHandler(InComingAPICallType, InComingObject);
        break;
      }
      case SocketCommunication.CREATE_GAME:{
        createGameHandler(InComingAPICallType,InComingObject);
        break;
      }
      case SocketCommunication.SUBSCRIBE_GAME_EVENT:{
        subscribeGameEventHandler(InComingAPICallType, InComingObject);
        break;
      }
      case SocketCommunication.PLAYER_JOIN_GAME:{
        playerJoinGameHandler(InComingAPICallType,InComingObject);
        break;
      }
      case SocketCommunication.GET_GAME_LIST_PRINTER:{
        getGameListPrinterHandler(InComingAPICallType);
        break;
      }
      case SocketCommunication.CANCEL_GAME:{
        cancelGameHandler(InComingObject);
        break;
      }
      case SocketCommunication.PLAYER_LEAVE_GAME:{
        playerLeaveGameHandler(InComingObject);
        break;
      }
      case SocketCommunication.START_GAME:{
        startGameHandler(InComingObject);
        break;
      }
      case SocketCommunication.GET_PROGRAM_RANKING:{
        getProgramRankingHandler(InComingAPICallType);
        break;
      }
      case SocketCommunication.PLAYER_ADDED_PROGRAM:{
        playerAddedProgramHandker(InComingAPICallType, InComingObject);
        break;
      }
    }
  }

  private void getRankingHandler(int InComingAPICallType){
    respond(new SocketCommunication(InComingAPICallType, server.getRanking()));
  }

  private void isPlayerNameTakenHandler(int InComingAPICallType, Object InComingObject){
    String playerName = (String)InComingObject;

    boolean isPlayerNameTaken = server.getRanking().isInList(playerName);

    if(!isPlayerNameTaken){
      GameList gameList = server.getGameList();
      for(int x=0;x<gameList.getSize();x++){
        Game currentGame = gameList.getByIndex(x);
        PlayersList playersList = currentGame.getPlayersList();
        if(playersList.isInList(playerName)){
          isPlayerNameTaken = true;
        }
      }
    }

    if(!isPlayerNameTaken){
      server.getRanking().add(new Player(playerName));
    }

    respond(new SocketCommunication(InComingAPICallType, isPlayerNameTaken));
  }

  private void createGameHandler(int InComingAPICallType, Object InComingObject){
    Player player = (Player) InComingObject;
    Game game = new Game(this.server);
    server.getGameList().add(game);
    game.getPlayersList().add(player);
    respond(new SocketCommunication(InComingAPICallType, game.getID()));
  }

  private void subscribeGameEventHandler(int InComingAPICallType, Object InComingObject){
    int gameID = (Integer)InComingObject;
    server.getGameList().getByID(gameID).subscribeEvent(this.oos);
  }

  private void playerJoinGameHandler(int InComingAPICallType, Object InComingObject){
    Object[] allObjects = (Object[]) InComingObject;
    int gameID = (int) allObjects[0];
    Player player = (Player) allObjects[1];
    Game currentGame = server.getGameList().getByID(gameID);
    if(!currentGame.hasStart()){
      currentGame.onPlayerJoin(this.oos,player);
      respond(new SocketCommunication(InComingAPICallType, currentGame.getPlayersList()));
    }else{
      respond(new SocketCommunication(InComingAPICallType, -1));
    }
  }

  private void getGameListPrinterHandler(int InComingAPICallType){
    ClientPrinterGameList gameList = server.getGameList().getClientPrinterObject();
    respond(new SocketCommunication(InComingAPICallType, gameList));
  }

  private void cancelGameHandler(Object InComingObject){
    int gameID = (int) InComingObject;
    Game currentGame = server.getGameList().getByID(gameID);
    currentGame.cancel(this.oos);
    server.getGameList().remove(currentGame);
  }

  private void playerLeaveGameHandler(Object InComingObject){
    Object[] allObjects = (Object[]) InComingObject;
    int gameID = (int) allObjects[0];
    Player player = (Player) allObjects[1];
    Game currentGame = server.getGameList().getByID(gameID);
    currentGame.onPlayerLeave(this.oos, player);
  }

  private void startGameHandler(Object InComingObject){
    int gameID = (int) InComingObject;
    Game currentGame = server.getGameList().getByID(gameID);
    currentGame.start(this.oos);
  }

  private void getProgramRankingHandler(int InComingAPICallType){
    ProgramRanking programRanking = server.getProgramRanking();
    respond(new SocketCommunication(InComingAPICallType, programRanking));
  }

  private void playerAddedProgramHandker(int InComingAPICallType, Object InComingObject){
    Program program = (Program) InComingObject;

    if(!server.getProgramRanking().isInList(program)){
      server.getProgramRanking().add(program);
    }
    
    respond(new SocketCommunication(InComingAPICallType, null));
  }

  private void respond(SocketCommunication toSendObject){
    try{
      this.oos.writeObject(toSendObject);
    }catch(IOException e){
      e.printStackTrace();
    }
  }
}