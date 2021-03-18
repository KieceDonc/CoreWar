package corewar.ClientSide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import corewar.Network.SocketCommunication;

public class Connexion extends Thread{
  
  private Socket socket;
  private SocketCommunication toSendCom;
  private SocketCommunication receivedCom;
  
  public Connexion(Socket socket, SocketCommunication toSendCom) throws IOException {
    this.socket = socket;
    this.toSendCom = toSendCom;
  }
  
  public void run(){
    try {
      ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(toSendCom);
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      boolean shouldStop = false;
      while (!shouldStop) {
        receivedCom = (SocketCommunication) ois.readObject();
        if (toSendCom.getAPICallType()==receivedCom.getAPICallType()){
          shouldStop = true;
          if(receivedCom.getObject().equals(SocketCommunication.BAD_COMM)){
            receivedCom = null;
          }
        }
      }
      oos.close();
      ois.close();
      socket.close();
    }catch(IOException | ClassNotFoundException e){
      System.err.println(e); 
      e.printStackTrace();
    }
  }

  public Object getReceivedObject(){
    return receivedCom.getObject();
  }
/*
  public void APIHandler(SocketCommunication receivedObject){
    switch(receivedObject.getAPICallType()){
      case SocketCommunication.GET_RANKING:{
        break;
      }
    }
  }

  public void getRanking(){
    socket.writeObject(new SocketCommunication(SocketCommunication.GET_RANKING));
  }*/

  /*
  package corewar.Client;

  import java.io.IOException;
  import java.io.ObjectOutputStream;
  import java.net.Socket;
  import java.net.UnknownHostException;

  import corewar.CoreWar;
  import corewar.Server.Server;

  public class Connexion{
  
  private Socket socket;
  private ObjectOutputStream oss;


  public Connexion() throws UnknownHostException, IOException{
    socket = new Socket(Server.ip, Server.port);
    oss = new ObjectOutputStream(socket.getOutputStream());
    CoreWar.DebugMessage("Nouvelle connexion : "+socket.getPort());
  }

  public void test() throws IOException{
    oss.writeObject("hello world");
  }

  public void end() throws IOException{
    oss.writeObject("END");
    oss.close();
    socket.close();
  }
}
*/
}
