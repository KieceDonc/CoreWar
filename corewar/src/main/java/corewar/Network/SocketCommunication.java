package corewar.Network;

import java.io.Serializable;

public class SocketCommunication implements Serializable{

  private static final long serialVersionUID = 2127495232721679744L;
  
  public static final String BAD_COMM = "BAD APICALL"; // unknow api call
  public static final int END_COMM = -1;
  public static final int GET_RANKING = 0;
  public static final int IS_PLAYER_NAME_TAKEN = 1;
  public static final int CREATE_GAME = 2;
  public static final int SUBSCRIBE_GAME_EVENT = 3;
  public static final int PLAYER_JOINED_GAME = 4;
  public static final int PLAYER_JOIN_GAME = 5;
  public static final int GET_GAME_LIST_PRINTER = 6;
  public static final int CANCEL_GAME = 7;
  public static final int PLAYER_LEAVE_GAME = 8;
  public static final int PLAYER_LEFT_GAME = 9;
  public static final int START_GAME = 10;
  public static final int GAME_STARTING = 11;
  public static final int GAME_STOP = 12;
  public static final int GAME_UPDATE = 13;
  public static final int GET_WARRIORS_RANKING = 14;
  public static final int PLAYER_ADDED_WARRIOR = 15;
  public static final int MODIFY_SETTINGS = 16;
  public static final int GET_RANKINGS = 17;

  private int APICallType;
  private Object object;  

  public SocketCommunication(int APICallType, Object object){
    this.APICallType = APICallType;
    this.object = object;
  }

  public int getAPICallType(){
    return this.APICallType;
  }

  public Object getObject(){
    return this.object;
  }

  @Override
  public String toString(){
    String toReturn ="API call type = "+APICallType+"\n";
    if(object!=null){
      toReturn+="Object send in SocketCommunication type = "+object.getClass()+"\n";
      toReturn+="Object send in SocketCommunication toString = "+object.toString();
    }else{
      toReturn+="Object send in SocketCommunication is null";
    }
    return toReturn;
  }
}