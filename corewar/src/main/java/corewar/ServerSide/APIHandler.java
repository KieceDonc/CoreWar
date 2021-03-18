package corewar.ServerSide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import corewar.Network.SocketCommunication;

public class APIHandler extends Thread{
  
  private Server server;
  private Socket socket;
  
  public APIHandler(Server server,Socket socket) throws IOException {
    this.server = server;
    this.socket = socket;
  }
  
  public void run(){
    try {
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      boolean shouldStop = false;
      while (!shouldStop) {
        SocketCommunication receivedObject = (SocketCommunication) ois.readObject();
        HandleIncomingObject(receivedObject);
      }
      ois.close();
      socket.close();
    }catch(IOException | ClassNotFoundException e){
      e.printStackTrace();
    }
  }

  public void HandleIncomingObject(SocketCommunication receivedObject){
    int InComingAPICallType = receivedObject.getAPICallType();
    switch(InComingAPICallType){
      case SocketCommunication.GET_RANKING:{
        respond(new SocketCommunication(InComingAPICallType, server.getRanking()));
        break;
      }
      default:{
        respond(new SocketCommunication(InComingAPICallType,SocketCommunication.BAD_COMM));
      }
    }
  }

  public void respond(SocketCommunication toSendObject){
    try{
      ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(toSendObject);
    }catch(IOException e){
      e.printStackTrace();
    }
  }
}
