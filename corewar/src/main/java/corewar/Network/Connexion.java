package corewar.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import corewar.ObjectModel.SocketCommunication;

public class Connexion extends Thread{
  
  private Socket socket;
  private ObjectOutputStream oss;
  
  public Connexion(Socket socket){
    this.socket = socket;
    oss = new ObjectOutputStream(socket.getOutputStream());
  }
  
  public void run(){
    try {
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      boolean shouldStop = false;
      while (!shouldStop) {
        SocketCommunication receivedObject = (SocketCommunication) ois.readObject();
        if (receivedObject.getAPICallType()==SocketCommunication.END_COMM){
          shouldStop = true;
        }else{
          APIHandler(receivedObject);
        }
        System.out.println(o);
      }
      ois.close();
      socket.close();
    }catch(IOException | ClassNotFoundException e){
      System.err.println(e); 
      e.printStackTrace();
    }
  }

  public void APIHandler(SocketCommunication receivedObject){
    switch(receivedObject.getAPICallType()){
      case SocketCommunication.GET_RANKING:{
        break;
      }
    }
  }

  public void getRanking(){
    socket.writeObject(new SocketCommunication(SocketCommunication.GET_RANKING));
  }

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
