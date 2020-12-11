package pgdp.carol;

import static pgdp.MiniJava.*;

import java.util.Arrays;

public class CarolPfadfinder {
	public static int length = 0;
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
		char []instructions = {'l','l','s','l','l','r','s','l','l','s','l','l','r','s','l','l','s','l','l','r','s','l','l','s','l','l','r','s','l','l','s','l','l','r','s'};
		//char []instr ={'l','l','s','l','l','r','s'};
		//int filled = 7;

		//write(getMinimalStepsAndTurns(startX,startY,startDir,findX,findY));
		if (findInstructions(playground, startX, startY, startDir,startBlocks,findX, findY,instructions) == true){
			write("True");
		}else {
			write("False");}

//		instructions = findOptimalSolution(playground, startX, startY, startDir, startBlocks, findX, findY, 40); // TODO implement
		boolean success = instructions != null;

		if (success) {
			write("SUCCESS");
			printPlayground(playground);
			write(Arrays.toString(instructions));
		} else {
			write("FAILED");
		}
	}
	static boolean lastTurnsAreUseless(char[] instr, int filled) {
		boolean result = false;
		if (filled >= 2) {
			if (instr[filled - 2] == 'r' && instr[filled - 1] == 'l') {
				result = true;
			} else if (instr[filled - 2] == 'l' && instr[filled - 1] == 'r') {
				result = true;
			} else if (instr[filled - 2] == 'r' && instr[filled - 1] == 'r') {
				result = true;
			} //else if (instr[filled - 2] == 'l' && instr[filled - 1] == 'l') {
				//result = true;
			//}
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


	//!static boolean wasThereBefore(char[] instr, int filled){
	//	int q = 0;
	//	while(filled >=4 && q < (filled-3)) {
	//		if (instr[filled - (4+q)] == 's' && instr[filled - (3+q)] == 'r' && instr[filled - (2+q)] == 'r' && instr[filled - 1] == 's' ) {
	//			return true;
	//		} else if (instr[filled - (4+q)] == 's' && instr[filled - (3+q)] == 'l' && instr[filled - (2+q)] == 'l' && instr[filled - 1] == 's'){
	//			return true;
	//		} else {
	//			return false;
	//		}
	//	}
	////		return false;
	//}

	static boolean wasThereBefore(char[] instr, int filled){
		 //int [][]place = new int[40][40];
		 boolean result = false;
		 int s_2 = 0;
		 if (filled < 0){
			 result = true;
		 }
		 if (instr[filled-1] == 'r'){
			 result = true;
		 }else if (instr[filled-1] == 'l'){
			 result = true;
		 }else if(instr[filled-1] == 'p'){
			 result = false;
		 }else if(instr[filled-1] == 'n'){
			 result = false;
		 } else if (instr[filled-1] == 's'){
			 A:for (int i = 0; i <filled-1 ; i++) {
				 if (instr[filled-(2+i)] == 's'){
					 s_2 = filled-(2+i);
					 for (int j = 1; j < (filled-1 - s_2) ; j++) {
					 	if (instr[filled-(s_2+j)] == 'p' || instr[filled-(s_2+j)] == 'n'){
							result = false;
							break A;
						}else if(j < (filled-1-1)){
							if (instr[filled-(s_2+j)] == 'r' && instr[filled-(s_2+j+1)] == 'r'){
								result = true;
								break A;
							}else if (instr[filled-(s_2+j)] == 'l' && instr[filled-(s_2+j+1)] == 'l' ){
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
		}else{
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
		int X = 0;
		int Y = 0;
		int dire = 0;
		int blo = 0;
		int min = getMinimalStepsAndTurns(x,y,direction,findX,findY);

		char [] new_instructions = new char[100];

		if (playground[x][y] == playground[findX][findY]) {
			if (length < instructions.length) {
				for (int i = 0; i <= length; i++) {
					instructions[i] = new_instructions[i];
				}
				for (int i = 0; length <= i && i < instructions.length; i++) {
					instructions[i] = 'e';
				}
				return true;}
			}
		else if (length >= instructions.length) {
			return false;
		}
		else if (min > instructions.length) {
			return false;
		}


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
				new_instructions[length] = 'r';
				dire = 1;
			} else if (direction == 3) {
					if (blocks > 10) {
						blo = -1;
						playground[x][y-1]++;
						new_instructions[length] = 'p';
					}
					else {
				new_instructions[length] = 'l';
				dire = (dire + 1) % 4;}
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
					new_instructions[length] = 'r';
					dire--;}
			} else if (direction == 2) {
				new_instructions[length] = 'l';
				dire++;
			} else if (direction == 1) {
				new_instructions[length] = 'l';
				dire++;
			}
		}
		length++;


		return findInstructions(playground,x+X,y+Y,((direction+dire)%4),blocks+blo,findX,findY,instructions);

}
public static char[] findOptimalSolution(int[][] playground, int x, int y, int direction, int blocks, int findX, int findY, int searchLimit){
	char []test = new char [2];
return test;}
}