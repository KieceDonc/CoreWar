package corewars.main;

public class Player{

  private String name;
  private int id;

  public Player(String name,int id){
    this.name = name;
    this.id = id;
  }

  public String getName(){
    return this.name;
  }
  
  public int getID(){
    return this.id;
  }
}
