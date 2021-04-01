package corewar.ClientSide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;

import corewar.Network.SocketCommunication;
import corewar.ServerSide.Server;

public class Connexion extends Thread{
  
  private Socket socket;
  private SocketCommunication toSendCom;
  private SocketCommunication receivedCom;
  private int timeout = 10000; // temps accordé pour une requête en millisecondes
  private boolean respondReceived = false;
  
  public Connexion(SocketCommunication toSendCom) throws IOException {
    this.socket = new Socket(Server.ip, Server.port);
    this.toSendCom = toSendCom;
  }
  
  public void run(){
    try {
      ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(toSendCom);
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      timeoutHandler();
      boolean shouldStop = false;
      while (!shouldStop) {
        receivedCom = (SocketCommunication) ois.readObject();
        if (toSendCom.getAPICallType()==receivedCom.getAPICallType()){
          respondReceived = true;
          shouldStop = true;
          if(receivedCom.getObject().equals(SocketCommunication.BAD_COMM)){
            System.out.println("Bad communication");
            System.out.println(toSendCom.toString());
            System.exit(0);
          }
        }
      }
      oos.writeObject(new SocketCommunication(SocketCommunication.END_COMM, null));
      oos.close();
      ois.close();
      socket.close();
    }catch(IOException | ClassNotFoundException e){
      System.err.println(e); 
      e.printStackTrace();
    }
  }

  private void timeoutHandler(){
    Timer timer = new Timer();
    timer.schedule(new java.util.TimerTask(){
      @Override
      public void run() {
        if(!respondReceived){
          System.out.println("Pas de réponse du server après "+timeout+" ms");
          System.out.println("On suppose le serveur mort");
          System.out.println("Votre requête :");
          System.out.println(toSendCom.toString());
          System.exit(0);
        }
        timer.cancel();
      }
    }, timeout);
  }

  public Object getReceivedObject(){
    return receivedCom.getObject();
  }

  public int getAPICallType(){
    return receivedCom.getAPICallType();
  }
}