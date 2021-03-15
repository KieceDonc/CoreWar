package corewar.ObjectModel;

public class Party{

  private PlayersList players;

  public Party(){
    this.players = new PlayersList();
  }

  public void start(){

  }

  public void onEnd(){    
  }

  public PlayersList getPlayersList(){
    return players;
  }
}
