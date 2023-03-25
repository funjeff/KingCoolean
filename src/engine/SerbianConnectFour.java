package engine;

import java.util.InputMismatchException;
import java.util.Scanner;
public class SerbianConnectFour {
	public static void main(String[] args) {
		SerbianConnectFour scf = new SerbianConnectFour();
		Scanner s = new Scanner(System.in);
		scf.print();
		int input = -1, check = 0, player = 1;
		while (true) {
//			System.out.println(input >= 0);
//			System.out.println(input <= 6);
			while (!(input >= 0) || !(input <= 6)) {
				try {
					input = (player == 1) ? s.nextInt() : scf.alphaBetaSearch(scf.board, 2);
//					System.out.println(input >= 0);
//					System.out.println(input <= 6);
				}
				catch(InputMismatchException e) {
					continue;
				}
			}
			if (scf.add(scf.board, input, player)) {
				input = -1;
				check = scf.checkForWin(scf.board);
				System.out.println();
				scf.print();
				player = (player == 1) ? 2 : 1;
				if (check == 1 || check == 2)
				{
					System.out.println("Player " + check + " Wins!");
					s.close();
					break;
				}
			}
			else System.out.println("Illegal Move."); //replace with something in game...
			input = -1;
		}
		
		

	}
	private int[][] board = new int[6][7];
	private int[] xDir = {1, 0, 1, -1};
	private int[] yDir = {1, 1, 0, 0};
	public SerbianConnectFour() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				board[i][j] = 0;
			}
		}
	}
	
	public boolean add(int[][] board, int x, int player) {
		for (int i = 5; i > -1; i--) {
			if (board[i][x] == 0) {
				board[i][x] = player;
				return true;
			}
		}
		return false;
	}
	
	public int checkForWin(int[][] board) {
		int check, count = 1;
		for (int wx = 0; wx < 6; wx++) {
			for (int wy = 0; wy < 7; wy++) {
				if (board[wx][wy] != 0) {
					check = board[wx][wy];
					for (int q = 0; q < 4; q++) {
						int xMove = wx + xDir[q];
						int yMove = wy + yDir[q];
						while (xMove > -1 && xMove < 6 && yMove > -1 && yMove < 7) {
							if (board[xMove][yMove] == 0) break;
							if (board[xMove][yMove] == check) {
								count++;
							}
							xMove += xDir[q];
							yMove += yDir[q];
						}
						if (count >= 4) {
							return check;
						}
						count = 1;
					}
				}
			}
		}
		return 0;
	}
	
	public void print() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public int getHeuristic(int[][] board) {
		int check, count = 0, heuristic = 0;
		boolean isSingle = false;
		for (int wx = 0; wx < 6; wx++) {
			for (int wy = 0; wy < 7; wy++) {
				if (board[wx][wy] != 0) {
					check = board[wx][wy];
					for (int q = 0; q < 4; q++) {
						int xMove = wx + xDir[q];
						int yMove = wy + yDir[q];
						while (xMove > -1 && xMove < 6 && yMove > -1 && yMove < 7) {
							if (board[xMove][yMove] == 0) break;
							if (board[xMove][yMove] == check) {
								count++;
							}
							xMove += xDir[q];
							yMove += yDir[q];
						}
						if (count >= 2) {
							heuristic += (check == 1) ? -Math.pow(2, count) : Math.pow(2, count);
							isSingle = true;
						}
						count = 1;
					}
					if (isSingle) heuristic++;
				}
			}
		}
		return heuristic;
	}
	
	public int alphaBetaSearch(int[][] board, int difficulty) {
		int a = Integer.MAX_VALUE, b = Integer.MIN_VALUE;
		State s = new State(-1, board, 2);
		int val = maxValue(s, a, b, 0, difficulty);
		return s.findChildPlay(val);
	}
	public int maxValue(State parent, int a, int b, int depth, int difficulty) {
		if (checkForWin(board) == 2 || depth >= difficulty) {
			parent.setValue(getHeuristic(parent.board));
			return parent.getValue();
		}
		else {
			int v = Integer.MIN_VALUE;
			for (int i = 0; i < 6; i++) {
				int[][] childBoard = new int[6][7];
				for (int wx = 0; wx < 6; wx++) {
					for (int wy = 0; wy < 7; wy++) {
						childBoard[wx][wy] = parent.board[wx][wy];
					}
				}
				State s = new State(i, childBoard, ((parent.getPlayer() == 1) ? 2 : 1));
				add(childBoard, i, 2);
				parent.addChild(s);
				v = Math.max(v, minValue(s, a, b, depth + 1, difficulty));
				if (v >= b) {
					parent.setValue(v);
					return v;
				}
				a = Math.max(a, v);
			}
			parent.setValue(v);
			return v;
		}
	}
	
	public int minValue(State parent, int a, int b, int depth, int difficulty) {
		if (checkForWin(board) == 2 || depth >= difficulty) {
			parent.setValue(getHeuristic(parent.board));
			return parent.getValue();
		}
		else {
			int v = Integer.MIN_VALUE;
			for (int i = 0; i < 6; i++) {
				int[][] childBoard = new int[6][7];
				for (int wx = 0; wx < 6; wx++) {
					for (int wy = 0; wy < 7; wy++) {
						childBoard[wx][wy] = parent.board[wx][wy];
					}
				}
				State s = new State(i, childBoard, ((parent.getPlayer() == 1) ? 2 : 1));
				parent.addChild(s);
				v = Math.min(v, maxValue(s, a, b, depth + 1, difficulty));
				if (v <= a) {
					parent.setValue(v);
					return v;
				}
				b = Math.min(v, b);
			}
			parent.setValue(v);
			return v;
		}
	}
}
