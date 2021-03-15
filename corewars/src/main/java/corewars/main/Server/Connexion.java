package corewars.main.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Connexion extends Thread{
  private Socket socket;
  
  public Connexion(Socket socket){
    this.socket = socket;
  }
  
  public void run(){
    try {
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream ());
      boolean shouldStop = false;
      while (!shouldStop) {
        Object o = ois.readObject();
        if (o.equals("END")){
          shouldStop = true;
        };
        System.out.println(o);
      }
      ois.close();
      socket.close();
    }catch(IOException | ClassNotFoundException e){
      System.err.println(e); 
      e.printStackTrace();
    }
  }
}
