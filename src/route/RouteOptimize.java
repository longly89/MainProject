package route;

import java.util.ArrayList;

public class RouteOptimize {

	private static int move[][] = { {-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	//real size 11x18
	private static int row = 10, col = 10;
	private static int mt[][] = {	{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
									{-1,0,0,0,0,0,-1,0,0,-1},
									{-1,0,0,-1,0,-1,0,0,0,-1},
									{-1,0,0,-1,0,-1,0,0,0,-1},
									{-1,0,0,-1,-1,-1,0,0,0,-1},
									{-1,0,0,0,0,0,0,0,0,-1},
									{-1,0,0,0,0,0,0,-1,-1,-1},
									{-1,0,0,0,0,0,0,-1,0,-1},
									{-1,0,0,0,-1,0,0,0,0,-1},
									{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}};

	private static void init() {
		//first row and last row fill with -1
		//each other row, first column & last column fill with -1
		for (int i = 0; i < col; i++) {
			mt[0][i] = -1;
			mt[row - 1][i] = -1;
		}
		
		for (int i = 1; i < row - 1; i++) { 
			mt[i][0] = -1;
			mt[i][col] = -1;
		}
	}

	private static void printmt (int mt[][]) {
		for (int i[]: mt) {
			for (int j: i)
				System.out.printf("%3d", j);
			System.out.println();
		}
	}
	
	private static void optimize() {
		//test matrix
		//0: accessible
		//-1: inaccessible
		//-2:goal
		int pos[] = {3,4}; //row, col
		int dest[] = {7,8};
		mt[dest[0]][dest[1]] = 1;
		mt[pos[0]][pos[1]] = -2;
		
		int step = 1;
		while (mt[pos[0]][pos[1]] == -2) {
			ArrayList<Integer> r = new ArrayList<Integer>();
			ArrayList<Integer> c = new ArrayList<Integer>();
			
			for (int i = 1; i < row; i++) 
				for (int j = 1; j < col; j++)
					if (mt[i][j] == step) {
						r.add(i);
						c.add(j);
					}
			
			for (int k = 0; k < r.size(); k++) {
				for (int m[] : move) {
					int rr = r.get(k) + m[0];
					int cc = c.get(k) + m[1];
					
					if (mt[rr][cc] == 0 || mt[rr][cc] == -2)
						mt[rr][cc] = step + 1;
					else
						if (mt[rr][cc] > 0 && mt[rr][cc] > step + 1)
							mt[rr][cc] = step + 1;
				}
			}
			
			step += 1;
			printmt(mt);
		}
		printmt(mt);
	}
	
	private static void backtracking() {
		int pos[] = {3,4}; //row, col
		//up, down, left, right
		//north, south, west, east
		//0,		1,		2,	3
		int orientation = 0;
		ArrayList<Character> command = new ArrayList<Character>();
		
		for (int i = mt[pos[0]][pos[1]] - 1; i > 0; i--) {
			int r = -1, c = -1, k = -1;
			
			//search around in 4 direction to find next step
			for (int m = 0; m < move.length; m++) {
				int rr = move[m][0] + pos[0];
				int cc = move[m][1] + pos[1];
				if (mt[rr][cc] == i) {
					r = rr;
					c = cc;
					k = m;
				}
			}
			
			pos[0] = r;
			pos[1] = c;
			
			switch (orientation) {
				case 0: //north
					switch (k) {
						case 1:	command.add('L'); command.add('L'); break;
						case 2: command.add('L'); break;
						case 3: command.add('R'); break;
					}
					command.add('S');
					break;
				case 1: //south
					switch (k) {
						case 0: command.add('L'); command.add('L'); break;
						case 2: command.add('R'); break;
						case 3: command.add('L'); break;
					}
					command.add('S');
					break;
				case 2: //west
					switch (k) {
						case 0: command.add('R'); break;
						case 1: command.add('L'); break;
						case 3: command.add('L'); command.add('L'); break;
					}
					command.add('S');
					break;
				case 3: //east
					switch (k) {
						case 0: command.add('L'); break;
						case 1: command.add('R'); break;
						case 2: command.add('L'); command.add('L'); break;
					}
					command.add('S');
					break;
			}
			
			orientation = k;
		}
		
		System.out.println(command);
	}
	
	public static void main(String arg[]) {
		optimize();
		backtracking();
	}
	
}
