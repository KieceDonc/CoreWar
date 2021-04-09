package corewar.ServerSide;

import java.util.ArrayList;

public class GameList{

  private ArrayList<Game> gameList = new ArrayList<>();

  public synchronized void add(Game game){
    this.gameList.add(game);
  }

  public synchronized Game getByIndex(int index){
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
  public synchronized Game getByID(int ID){
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
    
    return game;
  }

  public synchronized void remove(Game game){
    gameList.remove(game);
  }

  public synchronized int getSize(){
    return this.gameList.size();
  }

  // retourne le nombre de partie qui sont dans l'attente de joueur
  public synchronized int getWaitingGameLength(){
    int cmpt = 0;
    for(int x=0;x<this.getSize();x++){
      Game currentGame = this.gameList.get(x);
      if(!currentGame.hasStart() && currentGame.getPlayersList().getSize()<4){
        cmpt+=1;
      }
    }
    return cmpt;
  }

  public synchronized int[] getAllIDS(){
    int allIDS[] = new int[this.getWaitingGameLength()];
    for(int x=0;x<this.getSize();x++){
      Game currentGame = this.gameList.get(x);
      if(!currentGame.hasStart() && currentGame.getPlayersList().getSize()<4){
        allIDS[x] = currentGame.getID();
      }
    }
    return allIDS;
  }

  public synchronized ClientPrinterGameList getClientPrinterObject(){
    String toPrint = "";
    for(int x=0;x<gameList.size();x++){
      Game currentGame = gameList.get(x);
      if(!currentGame.hasStart() && currentGame.getPlayersList().getSize()<4){
        toPrint+="Partie id : "+currentGame.getID()+", Nombre de joueurs : "+currentGame.getPlayersList().getSize()+"\n";
      }
    }
    return new ClientPrinterGameList(toPrint,this.getAllIDS());
  }
}