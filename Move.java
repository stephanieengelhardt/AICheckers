
public class Move {
	int oldRow;
	int oldColumn;
	int newRow;
	int newColumn;
	Checker removed;
	
	/**
	 * Single move that is a part of an action
	 * @param oldRow of the checker moved
	 * @param oldColumn of the checker moved
	 * @param newRow of the checker moved
	 * @param newColumn of the checker moved
	 * @param removed checker removed (null if no jump occurred)
	 */
	public Move(int oldRow, int oldColumn, int newRow, int newColumn, Checker removed){
		this.oldRow= oldRow;
		this.oldColumn= oldColumn;
		this.newRow= newRow;
		this.newColumn= newColumn;
		this.removed=removed;
	}
	
	/**
	 * 
	 * @return checker removed by the action (null if no jump occurred)
	 */
	public Checker getRemoved(){
		return removed;
	}
	
	/**
	 * @return if this move was a jump
	 */
	public boolean jumped(){
		if (removed != null){
			return true;
		}
		else{
			return false;
		}
	}
	
	 /**
	  * 
	  * @return row of checker before the move
	  */
	public int getOldRow(){
		return oldRow;
	}
	
	/**
	  * 
	  * @return column of checker before the move
	  */
	public int getOldColumn(){
		return oldColumn;
	}
	
	/**
	  * 
	  * @return row of checker after the move
	  */
	public int getNewRow(){
		return newRow;
	}
	
	/**
	  * 
	  * @return column of checker after the move
	  */
	public int getNewColumn(){
		return newColumn;
	}
}
