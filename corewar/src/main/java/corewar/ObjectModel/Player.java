package corewar.ObjectModel;

public class Player{

  private Program program;
  private String name;
  private int score;

  public Player(String name){
    this.name = name;
  }

  public void setProgram(Program program){
    this.program = program;
  }

  public Program getProgram(){
    return program;
  }

  public String getName(){
    return this.name;
  }

  public void updateScore(int partyScore){
    score+=partyScore;
  }

  public int getScore(){
    return score;
  }

  public boolean equals(Player player2){
    return this.getName().equals(player2.getName());
  }

  public String toString(){
    return "Player "+this.getName()+", score = "+this.getScore();
  }
}
