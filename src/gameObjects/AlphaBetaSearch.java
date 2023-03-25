package gameObjects;

import engine.SerbianConnectFour;
import engine.State;

public class AlphaBetaSearch {
	SerbianConnectFour scf;
	private int[] xDir = {1, 0, 1, -1};
	private int[] yDir = {1, -1, 0, -1};
	public AlphaBetaSearch(SerbianConnectFour scf) {
		this.scf = scf;
	}
	public boolean checkForNextTurnWin(int[][] board, int player) {
		int[][] temp = new int[7][6];
		for (int x = 0; x < 6; x++) {
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 6; j++) {
					temp[i][j] = board[i][j];
				}
			}
			if (!isFullColumn(board, x)) {
				scf.add(temp, x, player);
				if (scf.checkForWin(temp) == player) return true;
			}
		}
		return false;
	}
	
	private boolean isFullColumn(int[][] board, int colX) {
	    return board[0][colX] != 0;
	  }
	public int getHeuristic(int[][] board) {
	    int score = 0;
	    if(checkForNextTurnWin(board, 1) == true) {
	    	score = 1000;
	        if(scf.checkForWin(board) == 1) {
	        	score = 1000000;
	        }

	    }
	    else if(checkForNextTurnWin(board, 2) == true) {
	    	score = -1000;
	    }
	    if(scf.checkForWin(board) == 2) {
	    	score = -1000000;
	    }

	    int yellowsFour = findNumWaysToWin(board, 2);
	    int redsFour = findNumWaysToWin(board, 1);
	    int yellowsThree = findNumThrees(board, 2);
	    int redsThree = findNumThrees(board, 1);

	    score += 40*(redsFour - yellowsFour) + 10*(redsThree - yellowsThree);
	    if (score > 0) {
	    	return score;
	    }
	    return score;
	}
	
	public int findNumWaysToWin(int[][] board, int player) {
		int numWays = 0;
	    for (int x = 0; x < 7; x++) {
	      for (int y = 0; y < 6; y++) {
	        if (board[x][y] == 0) {
	          numWays += numMakesFour(board, y, x, player);
	        }
	      }
	    }
	    return numWays;
	  }
	
	  public int numMakesFour(int[][] board, int y, int x, int player) {
		    int numWays = 0;
		    for (int i = 0; i < 4; i++) {
		      if (makesFour(board, y, x, player, i)) {
		        numWays++;
		      }
		    }
		    return numWays;
		  }

	  private boolean makesFour(int[][] board, int y, int x, int player, int dir) {
	    int count = 0;
	    int x1 = x;
	    int y1 = y;
	    while ((x1 > -1 && x1 < 7 && y1 > -1 && y1 < 6) && board[x1][y1] == player) {
	      x1 += xDir[dir];
	      y1 += yDir[dir];
	      count++;
	    }
	    x1 = x;
	    y1 = y;
	    while ((x1 > -1 && x1 < 7 && y1 > -1 && y1 < 6) && board[x1][y1] == player) {
	      x1 -= xDir[dir];
	      y1 -= xDir[dir];
	      count++;
	    }
	    return count - 1 >= 4;
	  }
	  public int findNumThrees(int[][] board, int player) {
		    int numWays = 0;
		    for (int x = 0; x < 7; x++) {
		      for (int y = 0; y < 6; y++) {
		        if (board[x][y] == player) {
		          numWays += numMakesThree(board, y, x, player);
		        }
		      }
		    }
		    return numWays;
		  }

		  private int numMakesThree(int[][] board, int y, int x, int player) {
		    int numWays = 0;
		    for (int i = 0; i < 4; i++) {
		      if (makesThree(board, y, x, player, i)) {
		        numWays++;
		      }
		    }
		    return numWays;
		  }

		  private boolean makesThree(int[][] board, int y, int x, int player, int dir) {
		    int count = 0;
		    int x1 = x;
		    int y1 = y;
		    while ((x1 > -1 && x1 < 7 && y1 > -1 && y1 < 6) && board[x1][y1] == player) {
		      x1 += xDir[dir];
		      y1 += yDir[dir];
		      count++;
		    }
		    x1 = x;
		    y1 = y;
		    while ((x1 > -1 && x1 < 7 && y1 > -1 && y1 < 6) && board[x1][y1] == player) {
		      x1 -= xDir[dir];
		      y1 -= yDir[dir];
		      count++;
		    }
		    return count - 1 >= 3;
		  }
//	public int getHeuristic(int[][] board) {
//		int check, count = 1, heuristic = 88;
//		for (int wx = 0; wx < 6; wx++) {
//			for (int wy = 0; wy < 7; wy++) {
//				if (board[wx][wy] != 0) {
//					check = board[wx][wy];
//					for (int q = 0; q < 4; q++) {
//						int xMove = wx + xDir[q];
//						int yMove = wy + yDir[q];
//						while (xMove > -1 && xMove < 6 && yMove > -1 && yMove < 7) {
//							if (board[xMove][yMove] == 0 || 
//									board[xMove][yMove] == 
//									((check == 1) ? 2 : 1)) break;
//							count++;
//							xMove += xDir[q];
//							yMove += yDir[q];
//						}
//						if (count >= 4) {
//							heuristic += (check == 1) ? -(Math.pow());
//						}
//						count = 1;
//					}
//				}
//			}
//		}
//		return heuristic;
//	}
	
	public int alphaBetaSearch(int[][] board, int difficulty) {
		int a = Integer.MIN_VALUE, b = Integer.MAX_VALUE;
		State s = new State(-1, board, 2);
		int val = maxValue(s, a, b, 0, difficulty);
		return s.findChildPlay(val);
	}
	public int maxValue(State parent, int a, int b, int depth, int difficulty) {
		if (scf.checkForWin(parent.board) == 2 || depth > difficulty) {
			parent.setValue(getHeuristic(parent.board));
			return parent.getValue();
		}
		else {
			int v = Integer.MIN_VALUE;
			for (int i = 0; i < 6; i++) {
				int[][] childBoard = new int[7][6];
				for (int wx = 0; wx < 7; wx++) {
					for (int wy = 0; wy < 6; wy++) {
						childBoard[wx][wy] = parent.board[wx][wy];
					}
				}
				scf.add(childBoard, i, parent.getPlayer());
				State s;
				if (parent.getPlayer() == 1) {
					s = new State(i, childBoard, 2);
				}
				else s = new State(i, childBoard, 1);
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
		if (scf.checkForWin(parent.board) == 2 || depth > difficulty) {
			parent.setValue(getHeuristic(parent.board));
			return parent.getValue();
		}
		else {
			int v = Integer.MAX_VALUE;
			for (int i = 0; i < 6; i++) {
				int[][] childBoard = new int[7][6];
				for (int wx = 0; wx < 7; wx++) {
					for (int wy = 0; wy < 6; wy++) {
						childBoard[wx][wy] = parent.board[wx][wy];
					}
				}
				scf.add(childBoard, i, parent.getPlayer());
				State s;
				if (parent.getPlayer() == 1) {
					s = new State(i, childBoard, 2);
				}
				else s = new State(i, childBoard, 1);
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
