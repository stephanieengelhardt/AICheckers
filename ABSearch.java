
public class ABSearch {

	private Player computer = new Player(CheckerType.BLACK);
	private Player human = new Player(CheckerType.RED);
	private int depthLimit;
	private Checkers game;
	
	/**
	 * Instance of the alpha beta search
	 * @param game
	 * @param depthLimit how many moves to look ahead
	 */
	public ABSearch(Checkers game, int depthLimit){
		this.depthLimit= depthLimit;
		this.game= game;
		
	}
	
	/**
	 * Decides which action is best at the state
	 * @param s state before the action is performed
	 * @return
	 */
	public Action makeDecision(State s){
		Action next=null;
		for(Action a: s.getActions()){
			double maxValue = Double.NEGATIVE_INFINITY;
			double value = minimax(game.getResult(a), 1, computer);
			if(value > maxValue){
				next=a;
				maxValue=value;
			}
		}
		return next;
	}
	
	public double minimax(State s, int depth, Player p){
		if(depth == depthLimit || s.isTerminal()){
			return game.getUtility(s, p);
		}
		//max
		if(p.getCheckerType() == computer.getCheckerType()){
			double maxValue = Double.NEGATIVE_INFINITY;
			for(Action possible: s.getActions()){
				double value= minimax(game.getResult(possible), depth + 1, human);
				if(value > maxValue){
					maxValue=value;
				}
			}
			return maxValue;
		}
		else{
			double minValue = Double.POSITIVE_INFINITY;
			for(Action possible: s.getActions()){
				double value= minimax(game.getResult(possible), depth + 1, computer);
				if(value < minValue){
					minValue=value;
				}
			}
			return minValue;
		}
	}
	
}
