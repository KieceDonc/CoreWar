package corewars.main;

public class Party{

  private PlayersList players;
  private PlayerRanking playerRanking;
  
  public Party(PlayersList players, PlayerRanking playerRanking){
    this.players = players;
    this.playerRanking = playerRanking;
  }

  public void onEnd(){
    for(int x=0;x<players.getSize();x++){
      players.getByIndex(x).updateScore(0);
    }
    playerRanking.sort();
  }
}
