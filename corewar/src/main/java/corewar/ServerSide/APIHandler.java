package corewar.ServerSide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import corewar.Network.SocketCommunication;
import corewar.ObjectModel.Player;

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
        
        if(receivedObject.getAPICallType()==SocketCommunication.END_COMM){
          shouldStop = true;
        }else{
          HandleIncomingObject(receivedObject);
        }
      }
      oos.close();
      ois.close();
      socket.close();
    }catch(IOException | ClassNotFoundException e){
      e.printStackTrace();
    }
  }

  public void HandleIncomingObject(SocketCommunication receivedObject){
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
      case SocketCommunication.CREATE_PARTY:{
        createPartyHandler(InComingAPICallType);
        break;
      }
      case SocketCommunication.SUBSCRIBE_PARTY_EVENT:{
        subscribePartyEventHandler(InComingAPICallType, InComingObject);
        break;
      }
      case SocketCommunication.PLAYER_JOIN_PARTY:{
        playerJoinPartyHandler(InComingObject);
        break;
      }
      case SocketCommunication.GET_PARTY_LIST:{
        getPartyListHandler(InComingAPICallType);
      }
    }
  }

  public void getRankingHandler(int InComingAPICallType){
    respond(new SocketCommunication(InComingAPICallType, server.getRanking()));
  }

  public void isPlayerNameTakenHandler(int InComingAPICallType, Object InComingObject){
    String playerName = (String)InComingObject;
    boolean isPlayerNameTaken = server.getRanking().isInList(playerName);
    respond(new SocketCommunication(InComingAPICallType, isPlayerNameTaken));
  }

  public void createPartyHandler(int InComingAPICallType){
    Party party = new Party(this.server);
    server.getPartyList().add(party);
    respond(new SocketCommunication(InComingAPICallType, party.getID()));
  }

  public void subscribePartyEventHandler(int InComingAPICallType, Object InComingObject){
    int partyID = (Integer)InComingObject;
    server.getPartyList().getByID(partyID).subscribeEvent(socket);
  }

  public void playerJoinPartyHandler(Object InComingObject){
    Object[] allObjects = (Object[]) InComingObject;
    int partyID = (int) allObjects[0];
    Player player = (Player) allObjects[1];
    server.getPartyList().getByID(partyID).onPlayerJoin(player);
  }

  public void getPartyListHandler(int InComingAPICallType){
    PartyList partyList = server.getPartyList();
    respond(new SocketCommunication(InComingAPICallType, partyList));
  }

  public void respond(SocketCommunication toSendObject){
    try{
      oos.writeObject(toSendObject);
    }catch(IOException e){
      e.printStackTrace();
    }
  }
}