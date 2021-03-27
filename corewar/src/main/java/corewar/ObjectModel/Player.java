package corewar.ObjectModel;

import java.io.Serializable;

public class Player implements Serializable {

  private static final long serialVersionUID = 5300637892606913207L;
  
  private Program program;
  private String name;
  private int score;
  private int gameScore;

  public Player(String name){
    this.name = name;
  }

  public void setProgram(Program program){
    this.program = program;
  }

  public Program getProgram(){
    return this.program;
  }

  public String getName(){
    return this.name;
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

  public boolean equals(Player player2){
    return this.getName().equals(player2.getName());
  }

  public String toString(){
    return "Player "+this.getName()+", score = "+this.getScore();
  }
}
