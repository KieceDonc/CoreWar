package corewar.Client;

public class Client {
  
  public Client(){
    try{
      Connexion connexion = new Connexion();
      connexion.test();
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}