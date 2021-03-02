package corewars.main;

import java.util.ArrayList; 

public class ProgramList{

  protected ArrayList<Program> programList;

  public ProgramList(){
    this.programList = new ArrayList<Program>();
  }

  public void add(Program program){
    this.programList.add(program);
  }

  public Program getByPath(String path){
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
    }while(!founded);
    
    if(!founded){
      System.err.println("Program with path="+path+" not founded");
      System.out.print(this.toString());
    }
    return program;
  }

  public int getSize(){
    return this.programList.size();
  }
}
