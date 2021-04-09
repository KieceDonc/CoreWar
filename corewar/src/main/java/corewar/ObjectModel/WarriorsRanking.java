package corewar.ObjectModel;

import java.util.Collections;
import java.util.Comparator;

public class WarriorsRanking extends Warriors{

  private static final long serialVersionUID = 4850257802483521442L;

  public WarriorsRanking() {
    super();
  }

  public synchronized void sort(){
    Collections.sort(this.warriors,new Comparator<Warrior>() {
      @Override
      public int compare(Warrior warrior1, Warrior warrior2) {
        //return -1 for less than, 0 for equals, and 1 for more than
        int warrior1Score = warrior1.getScore();
        int warrior2Score = warrior2.getScore();
        if(warrior1Score>warrior2Score){
          return 1;
        }else if(warrior2Score<warrior2Score){
          return -1;
        }else{
          return 0;
        }
      }
    });
  }

  public void print(){
    this.sort();
    System.out.println("------------------------------------------------------------------------------------------");
    System.out.println("");
    System.out.println("Classement des warrios :");
    System.out.println("");
    if(this.getSize()==0){
      System.out.println("Aucun warrior dans le classement");
    }else{
      for(int x=0;x<this.getSize();x++){
        Warrior currentWarrior = this.get(x);
        System.out.println("Warrior n°"+x+": "+currentWarrior.getNom()+", score : "+currentWarrior.getScore());
      }
    }
    System.out.println("");
    System.out.println("------------------------------------------------------------------------------------------");
  }
  
}
