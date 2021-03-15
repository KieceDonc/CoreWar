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
