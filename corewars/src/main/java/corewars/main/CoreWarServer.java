package corewars.main;

public class CoreWarServer {
  
  PlayerRanking ranking = new PlayerRanking();

  public CoreWarServer(){

  }

  public void partyStart(Party party){
    if(party.getPlayersList().getSize()>1){
      party.start();
    }
  }

  public void onPartyEnded(Party party){
    
  }

  public void onPlayerConnected(Player player){
    if(!ranking.isInList(player)){
      ranking.add(player);
    }
  }

  public void onPlayerSubmitProgram(Player player, Program program){
    player.setProgram(program);
  }
}
