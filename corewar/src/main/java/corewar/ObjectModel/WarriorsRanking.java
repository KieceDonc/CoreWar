package corewar.ObjectModel;

import java.util.Collections;
import java.util.Comparator;

public class WarriorsRanking extends Warriors{

  private static final long serialVersionUID = 4850257802483521442L;

  public WarriorsRanking() {
    super();
  }

  public synchronized void sort(){
  }

  public void print(){
    this.sort();
    System.out.println("------------------------------------------------------------------------------------------");
    System.out.println("");
    System.out.println("Classement des warrios :");
    System.out.println("");
    if(this.getSize()==0){
      System.out.println("Aucun warrior dans le classement");
    }
    System.out.println("");
    System.out.println("------------------------------------------------------------------------------------------");
  }
  
}
