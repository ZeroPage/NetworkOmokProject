package omok;

import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class OmokGame {
  protected OmokSocket mySocket;
  OmokGui gameGui;
	protected boolean end;
	protected int[][] board; //0 non, -1 white, 1 black
	protected int ver,hor;
	protected int turn;
  public boolean myTurn;
  public boolean solo;
  private static final String[] answer = {"YES", "NO"};

  public OmokGame(int lineNum){
	   dataInit(lineNum,lineNum);
     String input = (String) JOptionPane.showInputDialog(null, "if you want solo play. just enter 1. otherwise enter the other key", "Input", JOptionPane.QUESTION_MESSAGE, null, answer, answer[0]);
     if(input.equals("YES")){
       solo = true;
     }
     if(!solo){
       socketInit();
     }
     gameGui = new OmokGui(lineNum, this);
     if(!solo && !myTurn){
       otherPut();
     }
   }
   private void socketInit(){
     mySocket = new OmokSocket();
     String input = (String) JOptionPane.showInputDialog(null, "if you want to be a host. just enter 1. otherwise enter the other key", "Input", JOptionPane.QUESTION_MESSAGE, null, answer, answer[0]);
//------------------------------------------------------------------------ should be upgrade

    if(input.equals("YES")){
      String portNum = JOptionPane.showInputDialog("Enter portNum");
      mySocket.beServer(Integer.parseInt(portNum));
      myTurn = true;
    }
    else{
    	String ipNum = JOptionPane.showInputDialog("Enter ServerIP");
      String portNum = JOptionPane.showInputDialog("Enter portNum");
      mySocket.beClient(ipNum, Integer.parseInt(portNum));
      myTurn = false;
    }
//----------------------------------------------------------------------should ve upgrade
  }

	private void dataInit(int ver, int hor){
		end = false;
		turn = 1;
		board = new int[ver+2][hor+2];
		for(int i = 1; i <= ver; i++){
			for(int j = 1; j<= hor; j++){
				board[i][j] = 0;
			}
		}
	    for (int i = 0; i < ver; i++){
	    	board[0][i] = 4;
	    	board[ver + 1][i] = 4;
	    }
	    for (int i = 0; i <hor; i++){
	    	board[i][0] = 3;
	    	board[i][hor + 1] = 3;
	    }
	}

	public void put(int x, int y){
		if(board[x][y] != 0){
			return;
		}
		board[x][y] = turn;
		turn *= -1;
    myTurn = false;
    gameGui.put(x, y);
    if(!solo){
      try{
    	  mySocket.sender.writeInt(x);
    	  mySocket.sender.writeInt(y);
      }
      catch(IOException ioex){
      	System.out.println(ioex);
      }
    }
	  if(WOL(x,y)){
		    end = true;
        gameGui.gameEnd();
		}
    if(!solo){
      otherPut();
    }
	}

  public void otherPut(){
	  int x = 0, y = 0;
	  try{
		  x = mySocket.reciever.readInt();
		  y = mySocket.reciever.readInt();
	  }
	  catch(IOException ioex){
	  	System.out.println(ioex);
	  }
		board[x][y] = turn;
		turn *= -1;
    myTurn = true;
    gameGui.put(x, y);
    if(WOL(x,y)){
			end = true;
      gameGui.gameEnd();
	  }
  }

	private boolean WOL(int x, int y){
	  int[] ex = new int[2];
	  int[] ey = new int[2];
	  int i, a, b, k;

    for (i = 0; i< 2; i++){
	    ex[i] = x;
	    ey[i] = y;
	  }
	  k = 1;

	  for (b = -1; b < 2; b++){
	    for (a = 0; a < 2; a++){
	      for (i = 1; i < 5; i++){
	        if (board[ex[a] + (-1)*k][ey[a] + b*k] == turn*(-1)){
	          ex[a] = ex[a] + (-1)*k;
	          ey[a] = ey[a] + b*k;
	        }
	        else{   break;  }
	      } k = k*(-1);
	    }
	    if (ex[1] - ex[0] + 1 >= 5){
	      return true;
	    }
	    for (i = 0; i< 2; i++){
		    ex[i] = x;
		    ey[i] = y;
		  }
		  k = 1;
	  }

	  for (i = 0; i< 2; i++){
	      ex[i] = x;
	      ey[i] = y;
	  }
	  k = 1;

	  k = -1;
	  for (a = 0; a < 2; a++){
	      for (i = 1; i < 5; i++){
	          if (board[ex[a]][ey[a] + k] == turn*(-1)){
	              ex[a] = ex[a];
	              ey[a] = ey[a] + k;
	          }
	          else{ break; }
	      }
	      k = k*-1;
	  }
	  if (ey[1] - ey[0] + 1 >= 5){
	      return true;
	  }

	  return false;
	}

	public int whoIsTurnIsIt(){
		return turn;
	}
	public boolean isItend(){
		return end;
	}
}
