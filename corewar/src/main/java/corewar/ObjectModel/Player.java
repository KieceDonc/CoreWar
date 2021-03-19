package corewar.ObjectModel;

import java.io.Serializable;

public class Player implements Serializable {

  private Program program;
  private String name;
  private int score;
  private int partyScore;

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
    this.score+=this.partyScore;
  }

  public int getScore(){
    return this.score;
  }

  public int getPartyScore(){
    return this.partyScore;
  }

  public void updatePartyScore(int partyScore){
    this.partyScore+=partyScore;
    this.resetPartyScore();
  }

  public void resetPartyScore(){
    this.partyScore = 0;
  }

  public boolean equals(Player player2){
    return this.getName().equals(player2.getName());
  }

  public String toString(){
    return "Player "+this.getName()+", score = "+this.getScore();
  }
}
