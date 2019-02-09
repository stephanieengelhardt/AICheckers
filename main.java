import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
	
	public static void main(String[] args) throws IOException{
		//Start a new instance of the game
		Checkers gameOfCheckers= new Checkers();
		State current = gameOfCheckers.getCurrentState();
		ArrayList<Action> possibleActions= new ArrayList<Action>();
		//Search to be used throughout the game by the computer, at depth of 5
		ABSearch alphaBetaSearch = new ABSearch(gameOfCheckers,5);
		Action next;
		//First print the board of the starting state
		gameOfCheckers.printBoard();
		boolean toldWinner=false;
		while(!current.isTerminal()){
			//Computer's turn
			if(current.getCurrentPlayer().getCheckerType() == CheckerType.BLACK){
				System.out.println("Computer's turn");
				//Get all the possible actions to make sure that there are possible actions
				possibleActions= current.getActions();
				if(possibleActions.size() == 0) {
					System.out.println("GAME OVER, I lost :(");
					break;
				}
				//Make a decision using alpha beta search on which piece to move
				next = alphaBetaSearch.makeDecision(gameOfCheckers.getCurrentState());
				
				//Get the result of the game
				current = gameOfCheckers.getResult(next);
				gameOfCheckers.setCurrentState(current);
				//Print out the resulting board
				gameOfCheckers.printBoard();
			}
			//Human's turn
			else{
				System.out.println("Human's turn");
				possibleActions=(new State(current.getBoard(), current.getCurrentPlayer())).getActions();
				//Print out all of the moves for the user to choose from, this prevents any moves that are illegal from being made
				System.out.print("Please pick a move: ");
				if(possibleActions.size() == 0) {
					System.out.println("GAME OVER, You lost :)");
					toldWinner=true;
					break;
				}
				int i =0;
				for(Action action: possibleActions) {
					System.out.println("");
					System.out.print("Move "+ i + ": ");
					System.out.print("From (" + action.getOldColumn() + "," +  action.getOldRow() + ") ");
					System.out.print("to (" + action.getNewColumn() + "," +  action.getNewRow() + ")");
					i++;
				}
				//Read in the number of the move that the user wants
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("\nYour choice: ");
				String input = br.readLine();
				//If they just hit enter
				while(input.length() == 0) {
					 System.out.println("Please pick a move: ");
					 input = br.readLine();
				 }
				//If they picked a number that was not listed as a choice
				 int move= Integer.parseInt(input);
				 while(move > possibleActions.size() - 1) {
					 System.out.println("Please pick an option within the range possible: ");
					 input = br.readLine();
					 move= Integer.parseInt(input);
				 }
				 //Perform the move
				 next=possibleActions.get(move);
				 current = gameOfCheckers.getResult(next);
				 //Set the current state to after the move
				 gameOfCheckers.setCurrentState(current);
				 //Print the updated board
				 gameOfCheckers.printBoard();
			
				
			}
		}
		if(!toldWinner){
			if(current.getWinner() == CheckerType.BLACK) {
				System.out.println("GAME OVER, You lost :)");
			}
			else {
				System.out.println("GAME OVER, I lost :(");
			}
		}
		
	}
	
}
