package corewar.ClientSide;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import corewar.ClientSide.EventInterface.onPartyCancel;
import corewar.ClientSide.EventInterface.onPlayerJoinParty;
import corewar.Network.SocketCommunication;
import corewar.ObjectModel.Player;
import corewar.ServerSide.Server;

public class PartyCommunicationHandler extends Thread{

  private Socket socket;
  private ObjectOutputStream oos;
  private int partyID;

  private ArrayList<onPlayerJoinParty> playerJoinPartyListeners = new ArrayList<>();
  private ArrayList<onPartyCancel> partyCancelListeners = new ArrayList<>();

  public PartyCommunicationHandler(int partyID) throws UnknownHostException, IOException {
    this.socket = new Socket(Server.ip,Server.port);
    this.partyID = partyID;
  }

  public void run(){
    try {
      oos = new ObjectOutputStream(socket.getOutputStream());
      this.send(new SocketCommunication(SocketCommunication.SUBSCRIBE_PARTY_EVENT, partyID));
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
      case SocketCommunication.PLAYER_JOINED_PARTY:{
        Player player = (Player) InComingObject;
        playerJoinedPartyHandler(player);
        break;
      }
      case SocketCommunication.CANCEL_PARTY:{
        partyCancelHandler();
      }
    }
  }

  private void playerJoinedPartyHandler(Player player){
    for(int x=0;x<this.playerJoinPartyListeners.size();x++){
      this.playerJoinPartyListeners.get(x).update(player);
    }
  }

  private void partyCancelHandler(){
    for(int x=0;x<this.partyCancelListeners.size();x++){
      this.partyCancelListeners.get(x).dothis();
    }
  }

  public void onPlayerJoinParty(onPlayerJoinParty playerJoinPartyListener){
    this.playerJoinPartyListeners.add(playerJoinPartyListener);
  }

  public void onPartyCancel(onPartyCancel partyCancelistener){
    this.partyCancelListeners.add(partyCancelistener);
  }

  public void joinParty(Player player) throws IOException {
    Object[] listObjects = {partyID,player};
    this.send(new SocketCommunication(SocketCommunication.PLAYER_JOIN_PARTY, listObjects));
  }

  public void cancelParty() throws IOException{
    this.send(new SocketCommunication(SocketCommunication.CANCEL_PARTY, partyID));
    this.send(new SocketCommunication(SocketCommunication.END_COMM, null));
  }
}