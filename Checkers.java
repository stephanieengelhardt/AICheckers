import java.util.ArrayList;
import java.util.List;

public class Checkers {
	Checker[][] board; 
	Player playerBlack;
	Player playerRed;
	State currentState;
	
	/**
	 * Main initializer of the checkers game
	 */
	public Checkers(){
		board = new Checker[8][8];
		initializeBoard();
		playerBlack= new Player(CheckerType.BLACK);
		playerRed= new Player(CheckerType.RED);
		currentState = new State(board, playerRed);
	}
	
	/**
	 * 
	 * @return current state of the game
	 */
	public State getCurrentState(){
		return currentState;
	}
	
	/**
	 * 
	 * @param s state to set the game to
	 */
	public void setCurrentState(State s) {
		for(int row=0; row< 8 ; row++) {
			for(int column=0; column< 8; column++) {
				board[row][column]= s.getBoard()[row][column];
			}
		}
		currentState= new State(board, s.getCurrentPlayer());
		
	}
	
	/**
	 * Initialize the board to how checkers is usually set up
	 */
	public void initializeBoard(){
		for(int row=0; row<8; row++){
			for(int column=0; column<8; column++){
				if(row==0 || row == 2){
					if(column==1 || column == 3 || column == 5 || column == 7){
						 board[row][column]=(new Checker(CheckerType.RED, column, row));
					}
					else{
						board[row][column]= (new Checker(CheckerType.EMPTY,column, row));
					}
				}
				else if(row==1){
					if(column==0 || column == 2 || column == 4 || column == 6){
						board[row][column]=(new Checker(CheckerType.RED, column, row));
					}
					else{
						board[row][column]= (new Checker(CheckerType.EMPTY, column, row));
					}
				}
				else if(row == 6){
					if(column==1 || column == 3 || column == 5 || column == 7){
						board[row][column]=(new Checker(CheckerType.BLACK,column, row));					
					}
					else{
						board[row][column]= (new Checker(CheckerType.EMPTY,  column, row));
					}
				}
				else if(row==5 || row == 7){
					if(column==0 || column == 2 || column == 4 || column == 6){
						board[row][column]=(new Checker(CheckerType.BLACK, column, row));
					}
					else{
						board[row][column]= (new Checker(CheckerType.EMPTY,column, row));
					}
				}
				else{
					board[row][column]= (new Checker(CheckerType.EMPTY,  column, row));
				}
			}
		}
	}
	
	/**
	 * 
	 * @param state
	 * @return current player for that state
	 */
	public Player getCurrentPlayer(State state) {
		return state.getCurrentPlayer();
	}

	/**
	 * 
	 * @param action to be performed
	 * @return state after the action is performed
	 */
	public State getResult(Action action) {
		State result;
		if(getCurrentPlayer(currentState).getCheckerType() == CheckerType.BLACK){
			 result = new State(action.createAfterBoard(), playerRed);
		}
		else{
			result= new State(action.createAfterBoard(), playerBlack);
		}
		return result;
	}
	
	/**
	 * Heuristic function that returns how good a state is based on the 
	 * number of opponent pieces vs number of your pieces, the number of opponents kings,
	 * the number of your kings, and the possibilities for your pieces to be removed the next time.
	 * The higher the number, the better the state is
	 * @param state state after the action has been performed
	 * @param player player that made the action
	 * @return heuristic of the state (the higher the better)
	 */
	public double getUtility(State state, Player player) {
		double result = 0.0;
		//If it is a winner, it should be worth a lot if it is your win, or very bad if it is the computer's win
		if(state.isTerminal()) {
			if(state.getWinner() == player.getCheckerType()) {
				return Double.POSITIVE_INFINITY;
			}
			else {
				return Double.NEGATIVE_INFINITY;
			}
		}
		//count the number of pieces on the board
		int numberOfRed = 0;
		int numberOfBlack = 0;
		int numberOfRedKings =0;
		int numberOfBlackKings =0;
		for(int row=0; row<8; row++){
			for(int column =0; column<8; column++){
				if(state.getBoard()[row][column] != null){
					if(state.getBoard()[row][column].checkerType == CheckerType.RED ){
						numberOfRed++;
					}
					else if(state.getBoard()[row][column].checkerType == CheckerType.RED_KING){
						numberOfRed++;
						numberOfRedKings++;
					}
					else if(state.getBoard()[row][column].checkerType == CheckerType.BLACK){
						numberOfBlack++;
					}
					else if(state.getBoard()[row][column].checkerType == CheckerType.BLACK_KING){
						numberOfBlack++;
						numberOfBlackKings++;
					}
				}
			}
		}
		//Add and subtract based on the number of red pieces and kings
		if(player.getCheckerType() == CheckerType.RED) {
			result= numberOfRed - numberOfBlack;
			result= result + 0 - (numberOfBlackKings*5);
			result= result + (numberOfRedKings*5);
		}
		//Add and subtract based on the number of black pieces and kings
		else {
			result= numberOfBlack - numberOfRed;
			result= result + 0 - (numberOfRedKings*5);
			result= result + (numberOfBlackKings*5);
		}
		//Since you just played, you don't want the next move to be easy for the human to kill your pieces
		for(Action a: state.getActions()) {
			if(a.numberOfOpponentsRemoved() > 0) {
				result = result - (a.numberOfOpponentsRemoved() * 10);
			}
		}
		
		return result;
	}
	
	/**
	 * Heuristic that does better than the random heuristic, and is quicker 
	 * than the best heuristic, but still doesn't win games
	 * @param state
	 * @param player
	 * @return
	 */
	public double getMediumUtility(State state, Player player) {
		double result = 0.0;
		//If it is a winner, it should be worth a lot if it is your win, or very bad if it is the computer's win
		if(state.isTerminal()) {
			if(state.getWinner() == player.getCheckerType()) {
				return Double.POSITIVE_INFINITY;
			}
			else {
				return Double.NEGATIVE_INFINITY;
			}
		}
		//count the number of pieces on the board
		int numberOfRed = 0;
		int numberOfBlack = 0;
		int numberOfRedKings =0;
		int numberOfBlackKings =0;
		for(int row=0; row<8; row++){
			for(int column =0; column<8; column++){
				if(state.getBoard()[row][column] != null){
					if(state.getBoard()[row][column].checkerType == CheckerType.RED ){
						numberOfRed++;
					}
					else if(state.getBoard()[row][column].checkerType == CheckerType.RED_KING){
						numberOfRed++;
						numberOfRedKings++;
					}
					else if(state.getBoard()[row][column].checkerType == CheckerType.BLACK){
						numberOfBlack++;
					}
					else if(state.getBoard()[row][column].checkerType == CheckerType.BLACK_KING){
						numberOfBlack++;
						numberOfBlackKings++;
					}
				}
			}
		}
		//Add and subtract based on the number of red pieces and kings
		if(player.getCheckerType() == CheckerType.RED) {
			result= numberOfRed - numberOfBlack;
			result= result + 0 - (numberOfBlackKings*5);
			result= result + (numberOfRedKings*5);
		}
		//Add and subtract based on the number of black pieces and kings
		else {
			result= numberOfBlack - numberOfRed;
			result= result + 0 - (numberOfRedKings*5);
			result= result + (numberOfBlackKings*5);
		}
		return result;
	}
	
	/**
	 * Terrible utility function that randomly returns a heuristic,
	 * used to write a report on how good the computer is at making a decision
	 * @param s
	 * @param p
	 * @return
	 */
	public double getRandomUtility(State s, Player p){
		return Math.random();
	}

	/**
	 * Prints the entire checker board
	 */
	public void printBoard(){
			System.out.println("    0   1    2    3    4    5    6    7");
			for(int row=0; row<8; row++){
				System.out.print(row);
				
				for(int column =0; column<8; column++){
					if(board[row][column].getCheckerType() != CheckerType.EMPTY){
						if(board[row][column].checkerType == CheckerType.RED){
							System.out.print(" _R__");
						}
						else if(board[row][column].checkerType == CheckerType.RED_KING){
							System.out.print(" _RK_");
						}
						else if(board[row][column].checkerType == CheckerType.BLACK){
							System.out.print(" _B__");
						}
						else if(board[row][column].checkerType == CheckerType.BLACK_KING){
							System.out.print(" _BK_");
						}
						else{
							System.out.print(" ____");
						}
					}
					else{
						System.out.print(" ____");
					}
				}
				System.out.println("");
			}
		}
}
