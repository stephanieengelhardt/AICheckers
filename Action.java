import java.util.ArrayList;

public class Action {
	Checker[][] boardBefore;
	Checker[][] boardAfter;
	boolean jumped;
	ArrayList<Move> moves;
	int numberOfOpponentsRemoved;
	Player current;
	
	/**
	 * Action to be performed
	 * @param moves a list of moves, will only contain multiple if there is a multi-jump process
	 * @param board board before the action is performed
	 * @param p current player
	 */
	public Action(ArrayList<Move> moves, Checker[][] board, Player p){
		this.boardBefore= board;
		this.moves= moves;
		boardAfter= new Checker[8][8];
		for(int i=0; i< 8; i++) {
			for(int j=0; j< 8; j++) {
				boardAfter[i][j]=boardBefore[i][j];
			}
		}
		numberOfOpponentsRemoved();
		if(numberOfOpponentsRemoved >0){
			jumped=true;
		}
		else{
			jumped=false;
		}
		current=p;
	}
	
	/**
	 * @return row of the checker piece before the action
	 */
	public int getOldRow(){
		return moves.get(0).getOldRow();
	}
	
	/**
	 * @return column of the checker piece before the action
	 */
	public int getOldColumn(){
		return moves.get(0).getOldColumn();
	}
	
	/**
	 * @return row of the checker piece after the action
	 */
	public int getNewRow(){
		return moves.get(moves.size()-1).getNewRow();
	}
	
	/**
	 * @return column of the checker piece after the action
	 */
	public int getNewColumn(){
		return moves.get(moves.size()-1).getNewColumn();
	}

	/**
	 * Perform the action by moving the piece from the old coordinates to the new,
	 * if any piece was removed from a jump, that is removed in the createAfterBoard() 
	 * method
	 */
	public void performAction(){
		if(jumped){
			if(getNewRow()==7 && current.getCheckerType()==CheckerType.RED) {
				boardAfter[getNewRow()][getNewColumn()]=new Checker(CheckerType.RED_KING,  getNewColumn(), getNewRow());
			}
			else if(getNewRow()==0 && current.getCheckerType()==CheckerType.BLACK){
				boardAfter[getNewRow()][getNewColumn()]=new Checker(CheckerType.BLACK_KING, getNewColumn(), getNewRow());
			}
			else {
				boardAfter[getNewRow()][getNewColumn()]=boardBefore[getOldRow()][getOldColumn()];
			}
			boardAfter[getOldRow()][getOldColumn()]=new Checker(CheckerType.EMPTY,  getOldColumn(), getOldRow());
			
		}
		else{
			if(getNewRow()==7 && current.getCheckerType()==CheckerType.RED) {
				boardAfter[getNewRow()][getNewColumn()]=new Checker(CheckerType.RED_KING,  getNewColumn(), getNewRow());
			}
			else if(getNewRow()==0 && current.getCheckerType()==CheckerType.BLACK){
				boardAfter[getNewRow()][getNewColumn()]=new Checker(CheckerType.BLACK_KING, getNewColumn(),  getNewRow());
			}
			else {
				boardAfter[getNewRow()][getNewColumn()]=boardBefore[getOldRow()][getOldColumn()];
			}
			boardAfter[getOldRow()][getOldColumn()]=new Checker(CheckerType.EMPTY,  getOldColumn(), getOldRow());
		}
	
}
	/**
	 * Deletes any checkers that were jumped over and moves the checkers piece from the old coordinates to new ones
	 * @return the board after the updates are made
	 */
	public Checker[][] createAfterBoard(){
		for(Move m: moves){
			if(m.jumped()){
				boardAfter[(m.getNewRow()+m.getOldRow())/2][(m.getOldColumn()+m.getNewColumn())/2]= new Checker(CheckerType.EMPTY,  m.getRemoved().getColumn(),  m.getRemoved().getRow());
			}
		}
		performAction();
		return boardAfter;
	}

	
	/**
	 * 
	 * @return number of opponents jumped over (and thus removed)
	 */
	public int numberOfOpponentsRemoved(){
		for(Move m: moves){
			if(m.jumped()){
				numberOfOpponentsRemoved++;
			}
		}
		return numberOfOpponentsRemoved;
	}
}
