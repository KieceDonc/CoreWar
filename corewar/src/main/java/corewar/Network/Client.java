package corewar.Client;

public class Client {
  
  public Client(){
    try{
      Connexion connexion = new Connexion(new Socket(Server.ip, Server.port));
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}