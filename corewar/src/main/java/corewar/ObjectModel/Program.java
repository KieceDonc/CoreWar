package corewar.ObjectModel;

import java.io.Serializable;

public class Program implements Serializable {
  
  private static final long serialVersionUID = 4934912641307152L;
  
  private String name;
  private String path;
  private int score;
  private int gameScore;

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

  public void updateScore(){
    this.score+=this.gameScore;
    this.resetGameScore();
  }

  public int getScore(){
    return this.score;
  }

  public int getGameScore(){
    return this.gameScore;
  }

  public void setGameScore(int gameScore){
    this.gameScore=gameScore;
  }

  public void resetGameScore(){
    this.gameScore = 0;
  }

  public boolean isValid(){
    return true;
  }

  public boolean equals(Program program){
    return this.path.equals(program.getPath());
  }

  public String toString(){
    return "Name = "+this.getName()+", Path = "+this.getPath();
  } 
}
