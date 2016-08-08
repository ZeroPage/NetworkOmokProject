package omok;

public class Omok {
	protected boolean end;
	protected int[][] board; //0이면 안둔것 -1이면 흑돌, 1이면 흰돌
	protected int ver,hor;
	protected int turn;
	
	Omok(int ver, int hor){
		init(ver,hor);
	}
	
	public void init(int ver, int hor){
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
	
	public boolean put(int x, int y){
		if(board[x][y] != 0){
			return false;
		}
		board[x][y] = turn;
		turn *= -1;
		if(WOL(x,y)){
			end = true;
		}
		return true;
	}

	
	
	boolean WOL(int x, int y){//게임이 끝났는지 확인하는 함수
	    int[] ex = new int[2];
	    int[] ey = new int[2];
	    int i, a, b, k;

	    for (i = 0; i< 2; i++){
	        ex[i] = x;
	        ey[i] = y;
	    }
	    k = 1;

	    for (b = -1; b < 2; b++){   //ey값의 변화와 관련
	        for (a = 0; a < 2; a++){ //ex,ey의 [0]과 [1] 체크([0]과[1]은 k를 이용하여  서로 반대  방향으로 체크합니다.)
	            for (i = 1; i < 5; i++){ //플레이어가 둔자리로부터 한방향에 최대 4개까지 체크
	                if (board[ex[a] + (-1)*k][ey[a] + b*k] == turn*(-1)){//벽을 만나거나 상대병의 돌을 만나면 break
	                    ex[a] = ex[a] + (-1)*k;
	                    ey[a] = ey[a] + b*k;
	                }
	                else{   break;  }
	            } k = k*(-1);   //반대 방향을 체크하기 위해 -1을 곱함
	        }
	        if (ex[1] - ex[0] + 1 >= 5){//5개 이상이면 승리
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
	    for (a = 0; a < 2; a++){    //x의 변화량은 0이고 y의 변화량만 있을 경우, 아래의 코드는 위와 같음
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
