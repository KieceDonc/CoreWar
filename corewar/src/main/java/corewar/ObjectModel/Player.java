package corewar.ObjectModel;

import java.io.Serializable;

public class Player implements Serializable {

  private static final long serialVersionUID = 5300637892606913207L;
  
  private Warrior warrior;
  private String name;
  private int score;

  public Player(String name){
    this.name = name;
  }

  public void setWarrior(Warrior warrior){
    this.warrior = warrior;
  }

  public Warrior getWarrior(){
    return this.warrior;
  }

  public String getName(){
    return this.name;
  }

  public void setScore(int score){
    this.score = score;
  }

  public int getScore(){
    return this.score;
  }

  public boolean equals(Player player2){
    return this.getName().equals(player2.getName());
  }

  public String toString(){
    return "Player "+this.getName()+", score = "+this.getScore();
  }
}
