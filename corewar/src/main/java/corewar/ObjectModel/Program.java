package corewar.ObjectModel;

import java.io.Serializable;

public class Program implements Serializable {
  
  private static final long serialVersionUID = 4934912641307152L;
  
  private String name;
  private String path;

  public Program(String name,String path){
    this.name = name;
    this.path = path;
  }

  public String getName(){
    return name;
  }

  public String getPath(){
    return path;
  }

  public boolean equals(Program program){
    return this.path.equals(program.getPath());
  }

  public String toString(){
    return "Name = "+this.getName()+", Path = "+this.getPath();
  }
}
