package corewar.Network;

import java.io.Serializable;

public class SocketCommunication implements Serializable{

  private static final long serialVersionUID = 2127495232721679744L;
  
  public static final String BAD_COMM = "BAD APICALL"; // unknow api call
  public static final int END_COMM = -1;
  public static final int GET_RANKING = 0;
  public static final int IS_PLAYER_NAME_TAKEN = 1;
  public static final int CREATE_PARTY = 2;
  public static final int SUBSCRIBE_PARTY_EVENT = 3;
  public static final int PLAYER_JOINED_PARTY = 4;
  public static final int PLAYER_JOIN_PARTY = 5;
  public static final int GET_PARTY_LIST_PRINTER = 6;
  public static final int CANCEL_PARTY = 7;

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