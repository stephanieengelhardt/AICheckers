
public class Player {
	CheckerType checkertype; 
	
	
	public Player(CheckerType ct){
		checkertype= ct;
	}
	
	/**
	 * 
	 * @return checker type for the player, will always be a regular type (either RED or BLACK)
	 */
	public CheckerType getCheckerType(){
		return checkertype;
	}
}
