package corewars.main;

public class Player{

  private Program program;
  private String name;
  private int id;
  private int score;

  public Player(String name,int id){
    this.name = name;
    this.id = id;
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
  
  public int getID(){
    return this.id;
  }

  public void updateScore(int partyScore){
    score+=partyScore;
  }

  public int getScore(){
    return score;
  }

  public boolean equals(Player player2){
    return this.getID()==player2.getID();
  }

  public String toString(){
    return "Player "+this.getID()+":\nname = "+this.getName()+"\nscore = "+this.getScore();
  }
}
