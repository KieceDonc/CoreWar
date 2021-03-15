package corewars.main.ObjectModel;

import java.util.Collections;
import java.util.Comparator;

public class PlayerRanking extends PlayersList {
  
  public PlayerRanking(){
    super();
  }

  public void sort(){
    Collections.sort(this.playersList,new Comparator<Player>() {
      @Override
      public int compare(Player player1, Player player2) {
        //return -1 for less than, 0 for equals, and 1 for more than
        int player1Score = player1.getScore();
        int player2Score = player2.getScore();
        if(player1Score>player2Score){
          return 1;
        }else if(player1Score<player2Score){
          return -1;
        }else{
          return 0;
        }
      }
    });
  }
  
}
