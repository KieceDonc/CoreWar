package corewar.ObjectModel;

import java.util.Collections;
import java.util.Comparator;

public class PlayersRanking extends PlayersList{

  private static final long serialVersionUID = -5644134169553267238L;

  public PlayersRanking() {
    super();
  }

  public synchronized void sort(){
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

  public synchronized void print(){
    this.sort();
    System.out.println("------------------------------------------------------------------------------------------");
    System.out.println("");
    System.out.println("Classement des joueurs :");
    System.out.println("");
    if(this.getSize()==0){
      System.out.println("Aucun joueur dans le classement");
    }else{
      for(int x=0;x<this.getSize();x++){
        Player currentPlayer = this.getByIndex(x);
        System.out.println("Joueur n°"+x+": "+currentPlayer.getName()+", score : "+currentPlayer.getScore());
      }
    }
    System.out.println("");
    System.out.println("------------------------------------------------------------------------------------------");
  }
  
}
