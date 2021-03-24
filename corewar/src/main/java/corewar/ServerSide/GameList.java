package corewar.ServerSide;

import java.util.ArrayList;

public class GameList{

  private ArrayList<Game> gameList = new ArrayList<>();

  public void add(Game game){
    this.gameList.add(game);
  }

  public Game getByIndex(int index){
    if(index<0 || index>this.getSize()){
      return null;
    }else{
      return this.gameList.get(index);
    }
  }
    
  /*
    Retourne la partie de la liste avec l'id donné en paramètre
    * {int} ID 
    * return {Game} si la partie n'est pas trouvé renvoit null
  */
  public Game getByID(int ID){
    if(getSize()==0){
      return null;
    }
    Game game = null;
    int index = 0;
    boolean founded = false;

    do{
      Game currentGame = gameList.get(index);
      if(currentGame.getID()==ID){
        game = currentGame;
        founded = true;
      }
      index++;
    }while(!founded && index<this.getSize());
    
    if(!founded){
      System.err.println("Game "+ID+" not founded");
      System.out.print(this.toString());
    }
    return game;
  }

  public void remove(Game game){
    gameList.remove(game);
  }

  public int getSize(){
      return this.gameList.size();
  }

  public int[] getAllIDS(){
    int allIDS[] = new int[this.getSize()];
    for(int x=0;x<this.getSize();x++){
      allIDS[x] = this.gameList.get(0).getID();
    }
    return allIDS;
  }

  public ClientPrinterGameList getClientPrinterObject(){
    String toPrint = "";
    for(int x=0;x<gameList.size();x++){
      Game currentGame = gameList.get(x);
      toPrint+="Partie id : "+currentGame.getID()+", Nombre de joueurs : "+currentGame.getPlayersList().getSize()+"\n";
    }
    return new ClientPrinterGameList(toPrint,gameList.size(),this.getAllIDS());
  }
}