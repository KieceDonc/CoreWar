package corewar.ObjectModel;

import java.io.Serializable;
import java.util.ArrayList; 

public class PlayersList implements Serializable{

  private static final long serialVersionUID = 4421202624407538837L;
  
  protected ArrayList<Player> playersList;

  public PlayersList(){
    this.playersList = new ArrayList<Player>();
  }

  public synchronized void add(Player player){
    if(isInList(player)){
      System.err.println("player already in list");
      System.out.println(this.toString());
      System.out.println(player.toString());
    }else{
      this.playersList.add(player);
    }
  }

  /*
    Retourne le joueur de la liste avec l'index donné en paramètre
    * {int} index: Position du joueur dans la position 
    * return {Player} si le joueur n'est pas trouvé renvoie null
  */
  public synchronized Player get(int index){
    if(getSize()==0){
      return null;
    }
    if(index<0 && index>=playersList.size()){
      System.err.println("index "+index+" in players list out of bound");
      System.out.print(this.toString());
      return null;
    }
    return this.playersList.get(index);
  }

  /*
    Retourne le joueur de la liste avec le nom donné en paramètre
    * {String} PlayerName 
    * return {Player} si le joueur n'est pas trouvé renvoie null
  */
  public synchronized Player get(String PlayerName){
    if(getSize()==0){
      return null;
    }
    
    Player player = null;
    int index = 0;
    boolean founded = false;

    do{
      Player currentPlayer = playersList.get(index);
      if(currentPlayer.getName().equals(PlayerName)){
        player = currentPlayer;
        founded = true;
      }
      index++;
    }while(!founded && index<this.getSize());
    
    return player;
  }

  public synchronized int getPlayerIndex(Player player){
    if(getSize()==0){
      return 0;
    }
    
    int index = 0;
    boolean founded = false;

    do{
      Player currentPlayer = playersList.get(index);
      if(currentPlayer.getName().equals(player.getName())){
        founded = true;
      }
      index++;
    }while(!founded && index<this.getSize());
    index-=1;

    if(!founded){
      return -1;
    }else{
      return index;
    }
  }

  public synchronized Player get(Player player){
    return this.get(player.getName());
  }

  public synchronized boolean isInList(Player player){
    return this.get(player.getName())!=null;
  }

  public synchronized boolean isInList(String playerName){
    return this.get(playerName)!=null;
  }

  public synchronized void remove(Player player){
    int playerIndex = this.getPlayerIndex(player);
    if(playerIndex==-1){
      System.out.println("Fatal error, trying to remove a player from playersList but player isn't in list");
      System.out.println("Player trying to find : "+player.toString());
      System.out.println("Player List : "+playersList.toString());
      System.exit(0);
    }
    playersList.remove(playerIndex);
  }

  public synchronized int getSize(){
    return this.playersList.size();
  }

  public synchronized ArrayList<Player> getPlayersList(){
    return this.playersList;
  }

  @Override
  public synchronized String toString(){
    String toReturn = "";
    toReturn+="Players list info : \n";
    toReturn+="Size = "+this.getSize()+"\n";
    for(int x=0;x<this.getSize();x++){
      toReturn+="Player index n°"+x+" = "+this.get(x).toString()+"\n";
    }
    return toReturn;
  }
}
