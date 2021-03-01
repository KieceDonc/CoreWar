package corewars.main;

import java.util.ArrayList; 

public class PlayersList{

  private ArrayList<Player> playersList;

  public PlayersList(){
    this.playersList = new ArrayList();
  }

  public void add(Player player){
    this.playersList.add(player);
  }

  /*
    Retourne le joueur de la liste avec l'index donné en paramètre
    * {int} index: Position du joueur dans la position 
    * return {Player} si le joueur n'est pas trouvé renvoie null
  */
  public Player getByIndex(int index){
    if(index<0 && index>=playersList.size()){
      System.err.println("index "+index+" in players list out of bound");
      System.out.print(this.toString());
      return null;
    }
    return this.playersList.get(index);
  }

  /*
    Retourne le joueur de la liste avec l'id donné en paramètre
    * {int} ID 
    * return {Player} si le joueur n'est pas trouvé renvoie null
  */
  public Player getByID(int ID){
    Player player = null;
    int index = 0;
    boolean founded = false;

    do{
      Player currentPlayer = playersList.get(index);
      if(currentPlayer.getID()==ID){
        player = currentPlayer;
        founded = true;
      }
      index++;
    }while(!founded);
    
    if(!founded){
      System.err.println("Player "+ID+" not founded");
      System.out.print(this.toString());
    }
    return player;
  }

  public int getSize(){
    return this.playersList.size();
  }

  public String toString(){
    String toReturn = "";
    toReturn+="Players list info : \n";
    toReturn+=" - Size : "+this.getSize()+"\n";
    for(int x=0;x<this.getSize();x++){
      toReturn+=" - Player index n°"+x+" : "+this.getByIndex(x).toString()+"\n";
    }
    return toReturn;
  }
}
