package gameObjects;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import engine.GameObject;
import engine.ObjectHandler;
import engine.Sprite;

public class ConnectFourGame extends GameObject {
	Board b = new Board ();
	KingCoolean k = new KingCoolean ();
	Enemy e = new Enemy ();
	
	int turn = 0;
	
	Piece toDrop = new Piece(true);
	
	int peicePos = 0;
	
	int timer = 0;
	
	int [][] boardState = new int [7][6];
	
	public ConnectFourGame () {
		b.setX(170);
		b.setY(69);
		
		k.setX(10);
		k.setY(200);
		
		e.setX(750);
		e.setY(200);
		
		toDrop.setX(170 + 10);
		toDrop.declare();
		
	}
	
	@Override
	public void draw () {
		super.draw();
		b.draw();
		k.draw();
		e.draw();
	}
	
	@Override
	public void frameEvent () {
		if (turn == 0) {
			if (timer == 0) {
				if (keyPressed('D') && peicePos != 6) {
					peicePos = peicePos + 1;
					toDrop.setX(toDrop.getX() + 82);
				}
				if (keyPressed('A') && peicePos != 0) {
					peicePos = peicePos -1;
					toDrop.setX(toDrop.getX() - 82);
				}
				if (keyPressed (KeyEvent.VK_ENTER)) {
					
					int firstOpen = getFirstOpen(peicePos);
					
					if (firstOpen == -1) {
						return;
					}
					
					toDrop.dropTo(69 + (78 * firstOpen));
					turn = 1;
					toDrop = new Piece (false);
					boardState[peicePos][firstOpen] = 1;
					peicePos = 0;
					
					Random rand = new Random ();
					
					timer = rand.nextInt(20) + 50;
				}
			} else {
				timer = timer - 1;
				if (timer == 0) {
					toDrop.declare(170 + 10, 0);
				}
			}
		} else if (turn == 1) {
			if (timer == 0) {
				int chosenMove = e.getMove(boardState);
				int columToChange = getFirstOpen(chosenMove);
				
				if (columToChange == -1 && (e instanceof LeftLarry)) {
					turn = 0;
					
					Random rand = new Random ();
					
					timer = rand.nextInt(10) + 20;
					
					toDrop.bounceLeft();
					toDrop.declare(170 + 10,0);
					
					toDrop = new Piece (true);
					return;
					
				}
				else if (e instanceof DeliriousDerek) {
					ArrayList<GameObject> peices = ObjectHandler.getObjectsByName("Piece");
					if (peices != null && getNumMoves(boardState) % 3 == 0) {
						Random r = new Random();
						int pos = r.nextInt(0, peices.size());
						Piece p = (Piece) peices.get(pos);
						int x = (int) ((-180 + p.getX())/82);
						int y = (int) ((-69 + p.getY())/78);
						if ((x > 6 || y > 6 || x <= 0 || y <= 0) && (boardState[x][y] != 0)) {
							peices.get(pos).setRenderPriority(1);
							p.dropTo(700);
							refreshBoard(x, y, peices);
						}
					}
				}
				
				boardState[chosenMove][columToChange] = 2;
				toDrop.declare(170 + 10 + (82 * chosenMove), 0);
				toDrop.dropTo(69 + (78 * columToChange));
				
				toDrop = new Piece (true);
				
				turn = 0;
				
				Random rand = new Random ();
				
				timer = rand.nextInt(10) + 20;
				
			} else {
				timer = timer - 1;
			}
		}
	}

	public int getNumMoves(int[][] boardState) {
		int numMoves = 0;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				if (boardState[i][j] != 0) numMoves++;
			}
		}
		return numMoves;
	}
	public ArrayList<int[]> findNumEnemyMoves(int[][] boardState) {
		ArrayList<int[]> moves = new ArrayList<int[]>();
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				if (boardState[i][j] == 1) moves.add(new int[]{i, j});
			}
		}
		return moves;
	}
	public void setEnemy (Enemy enemy) {
		e = enemy;
		if (e instanceof LeftLarry) {
			turn = 1;
			toDrop.forget();
			toDrop = new Piece(false);	
		}
		
		e.setX(750);
		e.setY(200);
	}
	private void refreshBoard(int x, int y, ArrayList<GameObject> peices) {
		int[][] temp = new int[7][6];
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				temp[i][j] = boardState[i][j];
			}
		}
		temp[x][y] = 0;
		for (int i = 0; i < peices.size(); i++) {
			Piece p = (Piece) peices.get(i);
			int peiceX = (int) ((-180 + p.getX())/82);
			int peiceY = (int) ((-69 + p.getY())/78);
			if (peiceX == x && peiceY < y) {
				p.dropTo((int) p.getY() + 82);
			}
		}
		int foundEmpty = -1;
		for (int i = 5; i >= 0; i--) {
			if (temp[x][i] == 0) {
				foundEmpty = i;
				break;
			}
		}
		if (foundEmpty != -1) {
			for (int i = foundEmpty-1; i > 0; i--) {
				if (temp[x][i] != 0) {
					temp[x][i+1] = temp[x][i];
					temp[x][i] = 0;
				}
			}
//			for (int j = foundEmpty; j < 6; j++) {
//				if (j >= 5) {
//					temp[x][5] = tempInt;
//					temp[x][i] = 0;
//					break;
//				}
//				else if (temp[x][j] != 0 && j != 0) {
//					temp[x][j-1] = tempInt;
//					temp[x][i] = 0;
//					break;
//				}
//			}
		}
		boardState = temp;
	}
	
	private int getFirstOpen (int row) {
		int firstOpen = 5;
		
		while (boardState[row][firstOpen] != 0) {
			firstOpen = firstOpen - 1;
			if (firstOpen == -1) {
				return -1;
			}
		}
		return firstOpen;
	}
	
	public class Board extends GameObject {
		
		public Board () {
			this.setSprite(new Sprite ("resources/sprites/board.png"));
		}
	}
}
