package corewar.Network;

import java.io.Serializable;

public class SocketCommunication implements Serializable{

  public static final String BAD_COMM = "BAD APICALL"; // unknow api call
  public static final int END_COMM = -1;
  public static final int GET_RANKING = 0;
  public static final int NEW_PLAYER = 1;

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
}

