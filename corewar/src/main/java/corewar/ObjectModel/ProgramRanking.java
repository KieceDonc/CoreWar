package corewar.ObjectModel;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;

public class ProgramRanking extends ProgramList implements Serializable{

  private static final long serialVersionUID = 1L;

  public ProgramRanking() {
    super();
  }

  public synchronized void sort(){
    Collections.sort(this.programList,new Comparator<Program>() {
      @Override
      public int compare(Program program1, Program program2) {
        //return -1 for less than, 0 for equals, and 1 for more than
        int program1Score = program1.getScore();
        int program2Score = program2.getScore();
        if(program1Score>program2Score){
          return 1;
        }else if(program1Score<program2Score){
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
    System.out.println("Classement des programmes :");
    System.out.println("");
    if(this.getSize()==0){
      System.out.println("Aucun programme dans le classement");
    }else{
      for(int x=0;x<this.getSize();x++){
        Program currentProgram = this.getByIndex(x);
        System.out.println("Programme n°"+x+": "+currentProgram.getName()+", score : "+currentProgram.getScore());
      }
    }
    System.out.println("");
    System.out.println("------------------------------------------------------------------------------------------");
  }
  
}
