
public class Checker {
	CheckerType checkerType;
	int column;
	int row;
	
	/**
	 * 
	 * @param checkerType checker type of the checker (red, red_king, black, black_king)
	 * @param column column the checker is located
	 * @param row row the checker is located
	 */
	 public Checker(CheckerType checkerType, int column, int row){
	      this.checkerType = checkerType;
	      this.column=column;
	      this.row=row;
	   }
	 
	 /**
	  * 
	  * @return checker type of the checker
	  */
	 public CheckerType getCheckerType(){
		   return checkerType;
	   }
	 
	 /**
	  * turns a regular checker into a king version of that checker color
	  */
	 public void kingMe(){
		 if(this.checkerType == CheckerType.BLACK){
			 this.checkerType= CheckerType.BLACK_KING;
		 }
		 else{
			 this.checkerType= CheckerType.RED_KING;
		 }
	 }
	 
	 /**
	  * 
	  * @return column of checker
	  */
	 public int getColumn(){
		 return column;
	 }
	 
	 /**
	  * 
	  * @return row of checker
	  */
	 public int getRow(){
		 return row;
	 }
	 
	 /**
	  * set the column of the checker
	  */
	 public void setColumn(int column){
		 this.column = column;
	 }
	 
	 /**
	  * set the row of the checker
	  */
	 public void setRow(int row){
		 this.row = row;
	 }
}
