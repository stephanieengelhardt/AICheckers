import java.util.ArrayList;
import java.util.List;

public class State {
	Player current;
	Checker[][] board;
	CheckerType winner;

	public State(Checker[][] board, Player current){
		this.current=current;
		this.board=board;
	}
	
	/**
	 * 
	 * @return checkers board in this state
	 */
	public Checker[][] getBoard(){
		return board;
	}
	
	/**
	 * 
	 * @return current player
	 */
	public Player getCurrentPlayer(){
		return current;
	}
	
	/**
	 * 
	 * @return possible moves to be made by the current player in this state
	 */
	public ArrayList<Action> getActions() {
		boolean jumped=false;
		ArrayList<Action> actions = new ArrayList<Action>();
		
		//use to see if it is their piece
		ArrayList<CheckerType> okayPieces = new ArrayList<CheckerType>();
		if(current.getCheckerType() == CheckerType.BLACK){
			okayPieces.add(CheckerType.BLACK);
			okayPieces.add(CheckerType.BLACK_KING);
		}
		else{
			okayPieces.add(CheckerType.RED);
			okayPieces.add(CheckerType.RED_KING);
		}
		for(int row = 0; row < 8; row ++){
			for(int column = 0; column < 8; column++){
				if(okayPieces.contains(board[row][column].getCheckerType())){
					
					if(jumpPossible(row,column,row+1, column+1, row+2, column+2)){
						ArrayList<Move> moves= new ArrayList<Move>();
						jumped=true;
						moves.add(new Move(row,column,row+2,column+2, board[row+1][column+1]));
						moves.addAll(performMoreJumps(row+2, column+2, board[row+1][column+1]));
						actions.add(new Action(moves,board,current));
						
					}
					if(jumpPossible(row,column,row-1,column+1,row-2,column+2)){
						ArrayList<Move> moves= new ArrayList<Move>();
						jumped=true;
						moves.add(new Move(row,column,row-2,column+2, board[row-1][column+1]));
						moves.addAll(performMoreJumps(row-2, column+2, board[row-1][column+1]));
						actions.add(new Action(moves,board,current));
						
					}
					if(jumpPossible(row,column,row+1,column-1,row+2, column-2)){
						ArrayList<Move> moves= new ArrayList<Move>();
						jumped=true;
						moves.add(new Move(row,column,row+2,column-2, board[row+1][column-1]));
						moves.addAll(performMoreJumps(row+2, column-2, board[row+1][column-1]));
						actions.add(new Action(moves,board,current));
						
					}
					if(jumpPossible(row,column,row-1,column-1,row-2,column-2)){
						ArrayList<Move> moves= new ArrayList<Move>();
						jumped=true;
						moves.add(new Move(row,column,row-2,column-2, board[row-1][column-1]));
						moves.addAll(performMoreJumps(row-2, column-2, board[row-1][column-1]));
						actions.add(new Action(moves,board,current));
						
					}
				}
			}
		}
		//Don't add any non jumps if there is a jump because you must take a jump if you can
		if(!jumped){
			for(int row = 0; row < 8; row ++){
				for(int column = 0; column < 8; column++){
					if(okayPieces.contains(board[row][column].getCheckerType())){
						
						if(movePossible(row,column,row+1, column+1)){
							ArrayList<Move> moves= new ArrayList<Move>();
							moves.add(new Move(row,column,row+1, column+1, null));
							actions.add(new Action(moves,board,current));
							moves= new ArrayList<Move>();
						}
						if(movePossible(row,column,row-1,column+1)){
							ArrayList<Move> moves= new ArrayList<Move>();
							moves.add(new Move(row,column,row-1, column+1, null));
							actions.add(new Action(moves,board,current));
							moves= new ArrayList<Move>();
						}
						if(movePossible(row,column,row+1,column-1)){
							ArrayList<Move> moves= new ArrayList<Move>();
							moves.add(new Move(row,column,row+1, column-1, null));
							actions.add(new Action(moves,board,current));
							moves= new ArrayList<Move>();
						}
						if(movePossible(row,column,row-1,column-1)){
							ArrayList<Move> moves= new ArrayList<Move>();
							moves.add(new Move(row,column,row-1, column-1,null));
							actions.add(new Action(moves,board,current));
							moves= new ArrayList<Move>();
						}
					}
				}
			}
		}
		return actions;
	}
	
	/**
	 * 
	 * @param row
	 * @param column
	 * @param rowToBeJumped
	 * @param colToBeJumped
	 * @param finalRow
	 * @param finalCol
	 * @return if this jump can be made
	 */
	private boolean jumpPossible(int row, int column, int rowToBeJumped, int colToBeJumped, int finalRow, int finalCol){
		if(finalRow < 0 || finalRow >= 8 || finalCol < 0 || finalCol >= 8){
			return false;
		}
		if(board[finalRow][finalCol].getCheckerType() != CheckerType.EMPTY){
			return false;
		}
		if(current.getCheckerType() == CheckerType.RED){
			if(board[row][column].getCheckerType() == CheckerType.RED && finalRow < row){
				return false;
			}
		}
		if(current.getCheckerType() == CheckerType.BLACK){
			if(board[row][column].getCheckerType() == CheckerType.BLACK && finalRow > row){
				return false;
			}
		}
		if(board[rowToBeJumped][colToBeJumped].getCheckerType() == CheckerType.EMPTY){
			return false;
		}
		if(current.getCheckerType() == CheckerType.BLACK){
			if(board[rowToBeJumped][colToBeJumped].getCheckerType() == CheckerType.BLACK 
				|| board[rowToBeJumped][colToBeJumped].getCheckerType() == CheckerType.BLACK_KING){
				return false;
			}
			else{
				return true;
			}
		}
		else{
			if(board[rowToBeJumped][colToBeJumped].getCheckerType() == CheckerType.RED
					|| board[rowToBeJumped][colToBeJumped].getCheckerType() == CheckerType.RED_KING){
					return false;
				}
			else{
				return true;
			}
		}
	}
	
	/**
	 * 
	 * @param row
	 * @param column
	 * @param finalRow
	 * @param finalCol
	 * @return if this move can be made
	 */
	private boolean movePossible(int row, int column, int finalRow, int finalCol){
		if(finalRow < 0 || finalRow >= 8 || finalCol < 0 || finalCol >= 8){
			return false;
		}
		if(board[finalRow][finalCol].getCheckerType() != CheckerType.EMPTY){
			return false;
		}
		if(current.getCheckerType() == CheckerType.RED){
			if(board[row][column].getCheckerType() == CheckerType.RED && finalRow < row){
				return false;
			}
			return true;
		}
		if(current.getCheckerType() == CheckerType.BLACK){
			if(board[row][column].getCheckerType() == CheckerType.BLACK && finalRow > row){
				return false;
			}
			return true;
		}
		return true;
	}
	
	/**
	 * 
	 * @return if this state is a terminal state
	 */
	public boolean isTerminal() {
		int numberOfRed = 0;
		int numberOfBlack = 0;
		for(int row=0; row<8; row++){
			for(int column =0; column<8; column++){
				if(board[row][column] != null){
					if(board[row][column].checkerType == CheckerType.RED || board[row][column].checkerType == CheckerType.RED_KING){
						numberOfRed++;
					}
					else if(board[row][column].checkerType == CheckerType.BLACK || board[row][column].checkerType == CheckerType.BLACK_KING){
						numberOfBlack++;
					}
				}
			}
		}
		if(numberOfRed == 0){
			winner= CheckerType.BLACK;
			return true;
		}
		else if(numberOfBlack == 0){
			winner = CheckerType.RED;
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * 
	 * @return checker type of the winner (if it is terminal), otherwise returns null
	 */
	public CheckerType getWinner() {
		if(isTerminal()) {
			return winner;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Performs more jumps (if possible) in addition to the first jump
	 * @param row
	 * @param column
	 * @param removed
	 * @return array list of the moves possible for more jumps
	 */
	private ArrayList<Move> performMoreJumps(int row, int column, Checker removed){
		ArrayList<Move> moves= new ArrayList<Move>();
		if(jumpPossible(row,column,row+1, column+1, row+2, column+2)){
			moves.add(new Move(row,column,row+2,column+2, board[row+1][column+1]));
		}
		else if(jumpPossible(row,column,row-1,column+1,row-2,column+2)){
			moves.add(new Move(row,column,row-2,column+2, board[row-1][column+1]));
		}
		else if(jumpPossible(row,column,row+1,column-1,row+2, column-2)){
			moves.add(new Move(row,column,row+2,column-2, board[row+1][column-1]));
		}
		else if(jumpPossible(row,column,row-1,column-1,row-2,column-2)){
			moves.add(new Move(row,column,row-2,column-2, board[row-1][column-1]));
		}
		return moves;
	}
}
