package pgdp.carol;

import static pgdp.MiniJava.*;

import java.util.Arrays;

public class CarolPfadfinder {
	//Public variables to use in different methods
	public static int length = 0;
	public static char [] new_instructions = new char[100];
	public static char []instructions = {'s','s','l','r','l','t','l'};//example
	public static void main(String[] args) {

		/*
		 * You can change this main-Method as you want. This is not being tested.
		 */

		// Note that in this array initialization the rows are in reverse order and both
		// x- and y-axis are swapped.
		int[][] playground = { //
				{ 0, -1, -1, -1, -1 }, //
				{ -1, -1, -1, -1, -1 }, //
				{ -1, -1, 7, 8, 9 }, //
				{ -1, -1, 8, 3, 5 }, //
				{ -1, -1, 9, 5, 3 } //
		};
		int startX = 2;
		int startY = 1;
		int startDir = 0;
		int startBlocks = 1;

		printPlayground(playground, startX, startY, startDir, startBlocks);

		int findX = 4;
		int findY = 4;

		// this is expected to have an optimal solution with exactly 40 instructions

		//write(getMinimalStepsAndTurns(startX,startY,startDir,findX,findY));
		//if (findInstructions(playground,startX,startY,startDir,startBlocks,findX,findY,instructions) == true){
		//	write("True");
		//}else {
		//	write("False");}
		char []instructions2 = null;
		instructions2 = findOptimalSolution(playground, startX, startY, startDir, startBlocks, findX, findY, 40);
		boolean success = instructions2 != null;

		if (success) {
			write("SUCCESS");
			printPlayground(playground);
			write(Arrays.toString(instructions2));
		} else {
			write("FAILED");
		}
	}
	static boolean lastTurnsAreUseless(char[] instr, int filled) {
		boolean result = false;
		//Conditions to see if last commands are turns and if so to see if they are useless
		if (filled >= 2) {
			if (instr[filled - 2] == 'r' && instr[filled - 1] == 'l') {
				result = true;
			} else if (instr[filled - 2] == 'l' && instr[filled - 1] == 'r') {
				result = true;
			} else if (instr[filled - 2] == 'r' && instr[filled - 1] == 'r') {
				result = true;
			}

		}
		else if (filled >= 3) {
			if (instr[filled - 3] == 'l' && instr[filled - 2] == 'l' && instr[filled - 1] == 'l') {
				result = true;
			} else if (instr[filled - 3] == 'r' && instr[filled - 2] == 'r' && instr[filled - 1] == 'r') {
				result = true;
			}
		}
	return result;
	}

	static boolean wasThereBefore(char[] instr, int filled){
		 boolean result = false;
		 int s_2 = 0;//s_2 is the second 's' command before the last one
		 if (filled < 0){
			 result = true;
		 }//Conditions to see if last command changes the current place
		 if (instr[filled-1] == 'r'){
			 result = true;
		 }else if (instr[filled-1] == 'l'){
			 result = true;
		 }else if(instr[filled-1] == 'p'){
			 result = false;
		 }else if(instr[filled-1] == 'n'){
			 result = false;
		 } else if (instr[filled-1] == 's'){//Conditions to se if carol was in the same spot before se arrived again
			 A:for (int i = 0; i <filled-1 ; i++) {
				 if (instr[filled-(2+i)] == 's'){
					 s_2 = filled-(2+i);
					 for (int j = 1; j < (filled-1 - s_2) ; j++) {
					 	if (instr[filled-(s_2+j+1)] == 'p' || instr[filled-(s_2+j+1)] == 'n'){
							result = false;
							break A;
						}else if(j < (filled-1-1)){
							if (instr[filled-(s_2+j+1)] == 'r' && instr[filled-(s_2+j+2)] == 'r'){
								result = true;
								break A;
							}else if (instr[filled-(s_2+j+1)] == 'l' && instr[filled-(s_2+j+2)] == 'l' ){
								result = true;
								break A;}
					 	}}
				 }else
					 result = false;
			 }
		 }
	return result;}


	static int getMinimalStepsAndTurns(int x, int y, int direction, int findX, int findY){
		int result = 0;
		if (x == findX && y == findY){
			return result;
		}else{//Conditions to each possible relation of places between x - findX and y - findY
			while (x != findX || y != findY){
				if (x<findX && y == findY){
					while (x<findX && y == findY){
						if (direction == 1){
							result++;
							direction--;}
						else if (direction == 2){
							result++;
							direction--; }
						else if (direction == 3){
							result ++;
							direction =0; }
						else{
							result++;
							x++;
						}
					}
				}
				else if(x<findX && y < findY){
					while (x<findX || y < findY){
						if (direction == 1){
							for(y=y;y<findY;y++){
								result++;}
							if (x==findX && y == findY){
								break;
							}
							direction--;
							result++;
						}
						else if (direction == 2){
							result++;
							direction--; }
					else if (direction == 3){
							result ++;
							direction =0; }
						else{
							result++;
							x++;
							if (x == findX && y !=findY){
								direction=1;
								result++;}
						}
					}
				}
				else if (x<findX && y > findY){
					while (x<findX || y > findY){
						if (direction == 1){
							result++;
							direction--; }
						else if (direction == 2){
							result++;
							direction--; }
						else if (direction == 3){
							for(y=y;y>findY;y--){
								result++;}
							if (x==findX && y == findY){
								break;
							}
							direction = 0;
							result++;}
						else{
							result++;
							x++;
							if (x == findX && y !=findY){
								direction=3;
								result++;}
						}
					}
				}
				else if (x>findX && y == findY){
					while (x>findX && y == findY){
						if (direction == 1){
							result++;
							direction++;}
						else if (direction == 0){
							result++;
							direction++; }
						else if (direction == 3){
							result ++;
							direction--; }
						else{
							result++;
							x--;
						}
					}
				}
				else if(x>findX && y < findY){
					while (x>findX || y < findY){
						if (direction == 1){
							for(y=y;y<findY;y++){
								result++;}
							if (x==findX && y == findY){
								break;
							}
							direction++;
							result++;
						}
						else if (direction == 0){
							result++;
							direction++; }
						else if (direction == 3){
							result ++;
							direction--; }
						else{
							result++;
							x--;
							if (x == findX && y !=findY){
								direction=1;
								result++;}
						}
					}
				}
				else if (x>findX && y > findY){
					while (x>findX || y > findY){
						if (direction == 1){
							result++;
							direction++; }
						else if (direction == 0){
							result++;
							direction = 3; }
						else if (direction == 3){
							for(y=y;y>findY;y--){
								result++;}
							if (x==findX && y == findY){
								break;
							}
							direction = 2;
							result++;}
						else{
							result++;
							x--;
							if (x == findX && y !=findY){
								direction=3;
								result++;}
						}
					}
				}
				else if (x == findX && y < findY){
					while (x == findX && y < findY){
						if (direction == 2){
							result++;
							direction--;}
						else if (direction == 0){
							result++;
							direction++; }
						else if (direction == 3){
							result ++;
							direction--; }
						else{
							result++;
							y++;
						}
					}
				}
				else if (x == findX && y > findY){
					while (x == findX && y > findY){
						if (direction == 2){
							result++;
							direction++;}
						else if (direction == 0){
							result++;
							direction = 3; }
						else if (direction == 1){
							result ++;
							direction++; }
						else{
							result++;
							y--;
						}
					}
				}
			}
		}
		return result;
	}


	public static boolean findInstructions(int[][] playground, int x, int y, int direction, int blocks, int findX, int findY, char[] instructions){
		//Variables for the recursive method
		int X = 0;
		int Y = 0;
		int dire = 0;
		int blo = 0;

		int min = getMinimalStepsAndTurns(x,y,direction,findX,findY);


		if (x == findX && y == findY) {//Method exit condition
			if (length < instructions.length) {//length is the length of the new_instructions array
				for (int i = 0; i <= length; i++) {
					instructions[i] = new_instructions[i];
				}
				for (int i = (instructions.length); length < i ; i--) {
					instructions[i-1] = 'e';
				}
				return true;}
			else if (length == instructions.length){
				for (int i = 0; i <length ; i++) {
					instructions[i] = new_instructions[i];
				}
				return true;}
			}
		else if (length >= instructions.length) {
			return false;
		}
		else if (min > instructions.length) {//If the length of the given array of commands is less than the min from the getMinimalStepsAndTurns
			return false;
		}

		//Conditions that compares the current position and the final one, followed by conditions that determine the current direction

		if (x < findX) {
			if (direction == 0) {
				if (playground[x][y] < (playground[x + 1][y] - 1)) {
					if (blocks < 10) {
						blo = +1;
						playground[x + 1][y]--;
						new_instructions[length] = 'n';
					}else{
						dire++;
						new_instructions[length] = 'l';}
				} else if (playground[x][y] > (playground[x + 1][y] + 1)) {
					if (blocks > 0) {
						blo = -1;
						playground[x + 1][y]++;
						new_instructions[length] = 'p';
					} else {
						dire++;
						new_instructions[length] = 'l';
					}
				} else {
					X++;
					new_instructions[length] = 's';
				}
			} else if (direction == 1) {
					if (blocks > 10) {
						blo = -1;
						playground[x][y-1]++;
						new_instructions[length] = 'p';
					}else{
						new_instructions[length] = 'r';
						dire--;}
			} else if (direction == 2) {
				new_instructions[length] = 'r';
				dire--;
			} else if (direction == 3) {
				new_instructions[length] = 'l';
				dire++;
			}

		} else if (x > findX) {
			if (direction == 2) {
				if (playground[x][y] < (playground[x - 1][y] - 1)) {
					if (blocks < 10) {
						blo = +1;
						playground[x + -1][y]--;
						new_instructions[length] = 'n';
					}else{
						dire++;
						new_instructions[length] = 'l';}
				} else if (playground[x][y] > (playground[x - 1][y] + 1)) {
					if (blocks > 0) {
						blo = -1;
						playground[x - 1][y]++;
						new_instructions[length] = 'p';
					} else {
						dire++;
						new_instructions[length] = 's';
					}
				} else {
					X--;
					new_instructions[length] = 's';
				}
			} else if (direction == 1) {
				new_instructions[length] = 'l';
				dire = +1;
			} else if (direction == 0) {
				new_instructions[length] = 'l';
				dire ++;
			} else if (direction == 3) {
					if (blocks > 10) {
						blo = -1;
						playground[x][y-1]++;
						new_instructions[length] = 'p';
					}
					else {
				new_instructions[length] = 'l';
				dire ++;}
			}

		} else if (y < findY) {
			if (direction == 1) {
				if (playground[x][y] < (playground[x][y + 1] - 1)) {
					if (blocks < 10) {
						blo ++;
						playground[x][y + 1]--;
						new_instructions[length] = 'n';
					}else{
						dire++;
						new_instructions[length] = 'l';
					}
				} else if (playground[x][y] > (playground[x][y + 1] + 1)) {
					if (blocks > 0) {
						blo --;
						playground[x][y + 1]++;
						new_instructions[length] = 'p';
					} else {
						dire++;
						new_instructions[length] = 'l';
					}
				} else {
					Y++;
					new_instructions[length] = 's';
				}
			} else if (direction == 0) {
				new_instructions[length] = 'l';
				dire++;
			} else if (direction == 2) {
				if (blocks > 10) {
					blo --;
					playground[x-1][y]++;
					new_instructions[length] = 'p';
				}else{
					new_instructions[length] = 'r';
					dire--;}
			} else if (direction == 3) {
				new_instructions[length] = 'l';
				dire++;
			}

		} else if (y > findY) {
			if (direction == 3) {
				if (playground[x][y] < (playground[x][y - 1] - 1)) {
					if (blocks < 10) {
						blo ++;
						playground[x][y - 1]--;
						new_instructions[length] = 'n';
					}else{
						dire++;
						new_instructions[length] = 'l';
					}
				} else if (playground[x][y] > (playground[x][y - 1] + 1)) {
					if (blocks > 0) {
						blo = -1;
						playground[x][y - 1]++;
						new_instructions[length] = 'p';
					} else {
						dire++;
						new_instructions[length] = 'l';
					}
				} else {
					Y--;
					new_instructions[length] = 's';
				}
			} else if (direction == 0) {
				if (blocks > 10) {
					blo --;
					playground[x+1][y]++;
					new_instructions[length] = 'p';
				}else{
					new_instructions[length] = 'l';
					dire++;}
			} else if (direction == 2) {
				new_instructions[length] = 'l';
				dire++;
			} else if (direction == 1) {
				new_instructions[length] = 'l';
				dire++;
			}
		}
		length++;

		//Recursion of the method findInstructions
		return findInstructions(playground,x+X,y+Y,((direction+dire)%4),blocks+blo,findX,findY,instructions);

}

public static char[] findOptimalSolution(int[][] playground, int x, int y, int direction, int blocks, int findX, int findY, int searchLimit){
		//I couldn't find a way to find the Optimal Solution but the method should be able to determine if a searchLimit is less than it should be
		char []result = null;
		char []optimalSolution = new char[getMinimalStepsAndTurns(x, y, direction, findX, findY)];
		if (searchLimit < getMinimalStepsAndTurns(x,y,direction,findX,findY)){
		return null;
		}else{
			if (findInstructions(playground,x,y,direction,blocks,findX,findY,instructions) == true){
				if (instructions.length == optimalSolution.length){
					result = instructions;
				}else if (instructions.length > optimalSolution.length){
					if (lastTurnsAreUseless(instructions, instructions.length) == true){
						for (int i = 0; i < optimalSolution.length ; i++) {
							optimalSolution[i] = instructions[i];
						}
						result = optimalSolution;
					}else if (wasThereBefore(instructions,instructions.length)== true){
						for (int i = 0; i < optimalSolution.length ; i++) {
							optimalSolution[i] = instructions[i];
						}
						result = optimalSolution;
					}
				}
			}
		}
	return result;}
}