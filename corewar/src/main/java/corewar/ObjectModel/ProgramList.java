package corewar.ObjectModel;

import java.io.Serializable;
import java.util.ArrayList; 

public class ProgramList implements Serializable{

  private static final long serialVersionUID = 1188504167358879685L;
  
  protected ArrayList<Program> programList;

  public ProgramList(){
    this.programList = new ArrayList<Program>();
  }

  public synchronized void add(Program program){
    this.programList.add(program);
  }

  public synchronized Program getByPath(String path){
    if(this.getSize()==0){
      return null;
    }
    Program program = null;
    int index = 0;
    boolean founded = false;

    do{
      Program currentProgram = programList.get(index);
      if(currentProgram.getPath().equals(path)){
        program = currentProgram;
        founded = true;
      }
      index++;
    }while(!founded && index<this.getSize());
    
    if(!founded){
      System.err.println("Program with path="+path+" not founded");
      System.out.print(this.toString());
    }
    return program;
  }

  /*
    Retourne le programme de la liste avec l'index donné en paramètre
    * {int} index: Position du joueur dans la position 
    * return {Program} si le joueur n'est pas trouvé renvoie null
  */
  public synchronized Program getByIndex(int index){
    if(getSize()==0){
      return null;
    }
    if(index<0 && index>=programList.size()){
      System.err.println("index "+index+" in program list out of bound");
      System.out.print(this.toString());
      return null;
    }
    return this.programList.get(index);
  }

  public synchronized Program get(Program program){
    return this.getByPath(program.getPath());
  }

  public synchronized boolean isInList(Program program){
    return this.get(program)!=null;
  }

  public synchronized int getSize(){
    return this.programList.size();
  }
}
