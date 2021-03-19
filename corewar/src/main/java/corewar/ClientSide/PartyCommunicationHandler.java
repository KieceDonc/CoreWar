package corewar.ClientSide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import corewar.ClientSide.EventInterface.onPlayerJoinParty;
import corewar.Network.SocketCommunication;
import corewar.ObjectModel.Player;
import corewar.ServerSide.Server;

public class PartyCommunicationHandler extends Thread{

  private Socket socket;
  private ObjectOutputStream oos;

  private ArrayList<onPlayerJoinParty> playerJoinPartyListeners = new ArrayList<>();

  public PartyCommunicationHandler() throws UnknownHostException, IOException {
    this.socket = new Socket(Server.ip,Server.port);
  }

  public void run(){
    try {
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      oos = new ObjectOutputStream(socket.getOutputStream());
      this.send(new SocketCommunication(SocketCommunication.SUBSCRIBE_PARTY_EVENT, null));
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

  public void send(SocketCommunication socketCommunication) throws IOException {
      oos.writeObject(socketCommunication);
  }
    
  private void HandleIncomingObject(SocketCommunication receivedObject){
    int InComingAPICallType = receivedObject.getAPICallType();
    Object InComingObject = receivedObject.getObject();
    
    switch(InComingAPICallType){
      case SocketCommunication.PLAYER_JOINED_PARTY:{
        Player player = (Player) InComingObject;
        PlayerJoinedPartyHandler(player);
        break;
      }
    }
  }

  private void PlayerJoinedPartyHandler(Player player){
    for(int x=0;x<this.playerJoinPartyListeners.size();x++){
      this.playerJoinPartyListeners.get(x).update(player);
    }
  }

  public void OnPlayerJoinParty(onPlayerJoinParty playerJoinPartyListener){
    this.playerJoinPartyListeners.add(playerJoinPartyListener);
  }

  public void joinParty(int partyID, Player player) throws IOException {
    Object[] listObjects = {partyID,player};
    this.send(new SocketCommunication(SocketCommunication.PLAYER_JOIN_PARTY, listObjects));
  }
}