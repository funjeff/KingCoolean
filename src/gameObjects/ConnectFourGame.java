package gameObjects;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.lwjgl.glfw.GLFW;

import engine.GameCode;
import engine.GameObject;
import engine.ObjectHandler;
import engine.Sprite;

public class ConnectFourGame extends GameObject {
	Board b = new Board ();
	KingCoolean k = new KingCoolean ();
	Enemy e = new Enemy (new Connect());
	Background back = new Background ();
	boolean blocked = false;
	int turn = 0;
	
	Piece toDrop = new Piece(0);
	
	int peicePos = 0;
	
	int timer = 0;
	
	int [][] boardState = new int [7][6];
	
	static boolean [] unlockedMoves = new boolean [4];
	
	SpecialMenu menu;
	
	boolean inSpecialMenu = false;
	
	boolean mirrorMove = false;
	
	Indicater indecator = new Indicater ();
	
	public ConnectFourGame () {
		

		b.setX(170);
		b.setY(69);
		
		k.setX(10);
		k.setY(380);
		
		e.setX(765);
		e.setY(380);
		
		indecator.setX(170 + 10);
		
		toDrop.setX(170 + 10);
		toDrop.declare();
		toDrop.hide();
		
		b.getAnimationHandler().setFrameTime(100);

		back.declare();
		
		back.setRenderPriority(-4);
		this.setRenderPriority(-1);
	
		
		for (int i = 0; i < unlockedMoves.length; i++) {
			if (unlockedMoves[i]) {
				menu = new SpecialMenu ();
				menu.setX(menu.getX() + 10);
				menu.setY(menu.getY() + 100);
				break;
			}
		}
	
	}
	
	@Override
	public void draw () {
		super.draw();
		
		
		b.draw();
		k.draw();
		e.draw();
		if (turn == 0) {
			indecator.draw();
		}
		
		if (menu != null) {
			menu.draw();
		}
	}
	
	@Override
	public void frameEvent () {
		e.frameEvent();
		if (!inSpecialMenu) {
			if (turn == -1) {
				if (keyPressed (GLFW.GLFW_KEY_ENTER)) {
					ArrayList <GameObject> peices = ObjectHandler.getObjectsByName("Piece");
					
					while (peices != null && !peices.isEmpty()) {
						peices.remove(0);
					}
					
					ArrayList <GameObject> rockets = ObjectHandler.getObjectsByName("FireworksRocket");
					
					while (rockets != null && !rockets.isEmpty()) {
						rockets.remove(0);
					}
					
					ArrayList <GameObject> blasts = ObjectHandler.getObjectsByName("FireworksBlast");
					
					while (blasts != null && !blasts.isEmpty()) {
						blasts.remove(0);
					}
					back.forget();
					
					this.forget();
					

					if (ObjectHandler.getObjectsByName("YouWin") != null && ObjectHandler.getObjectsByName("YouWin").size() != 0 ) {
						ObjectHandler.getObjectsByName("YouWin").get(0).forget();
						e.onDefeat();
					}
					
					if (ObjectHandler.getObjectsByName("YouLose") != null && ObjectHandler.getObjectsByName("YouLose").size() != 0) {
						ObjectHandler.getObjectsByName("YouLose").get(0).forget();
						e.onVictory();
					}
				}
			}
			
			
			if (turn == 0) {
				if (timer == 0) {
					if (keyPressed('D') && peicePos != 6) {
						peicePos = peicePos + 1;
						indecator.setX(indecator.getX() + 82);
						toDrop.setX(toDrop.getX() + 82);
						
					}
					
					if (keyPressed('A') && peicePos == 0 && menu != null && !menu.isUsedUp()) {
						inSpecialMenu = true;
					}
					
					if (keyPressed('A') && peicePos != 0) {
						peicePos = peicePos -1;
						indecator.setX(indecator.getX() - 82);
						toDrop.setX(toDrop.getX() - 82);
					}
					
					
					
					if (keyPressed (GLFW.GLFW_KEY_ENTER)) {
						
						int firstOpen = getFirstOpen(peicePos);
						
						if (firstOpen == -1) {
							return;
						}
						
						indecator.setX(180);
						
						toDrop.show();
						
						
						toDrop.setCurPosX(peicePos);
						toDrop.setCurPosY(firstOpen);
						
						toDrop.dropTo(69 + (78 * firstOpen));
						
						if (mirrorMove) {
							int secondOpen = getFirstOpen(6 - peicePos);
							if (secondOpen != -1) {
								toDrop = new Piece (0);
								toDrop.declare(180 + (82 * (6-peicePos)),0);
								toDrop.setCurPosX(peicePos);
								toDrop.setCurPosY(secondOpen);
								toDrop.dropTo(69 + (78 * secondOpen));
								boardState[6-peicePos][secondOpen] = 1;
							} else {
								toDrop.setX(180);
								toDrop.bounceLeft();
							}
							mirrorMove = false;
						}
						
						turn = 1;
						toDrop = new Piece (e.pieceType);
						boardState[peicePos][firstOpen] = 1;
						peicePos = 0;
						
					
						
						if (e instanceof WeffJiener) {
							if (checkForFive (boardState) == 1) {
								turn = -1;
								YouWin you = new YouWin ();
								e.onDefeatLine();
								you.declare();
							}
						} else {
							if (checkForWin (boardState) == 1) {
								if (!(e instanceof MirroredMeryl)) {
									turn = -1;
									YouWin you = new YouWin ();
									e.onDefeatLine();
									you.declare();
								} else {
									turn = -1;
									YouLose you = new YouLose ();
									e.onVictoryLine();
									you.declare();
								}
							}
						}
						
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
					
					if (e instanceof CheatingCharlie) {
						if (checkForThree(boardState) == 0) {
							turn = 0;
							Random rand = new Random ();
							timer = rand.nextInt(10) + 20;
							toDrop.hide();
							toDrop.setColor(0);
							return;
						}
					}
					
					else if (e instanceof DeliriousDerek) {
						ArrayList<GameObject> peices = ObjectHandler.getObjectsByName("Piece");
						if (peices != null && getNumMoves(boardState) % 3 == 0) {
							Random r = new Random();
							int pos = r.nextInt(peices.size());
							Piece p = (Piece) peices.get(pos);
							while (p.curPosX > 7 || p.curPosY > 6  || (boardState[p.curPosX][p.curPosY] == 0 )) {
								pos = r.nextInt(peices.size());
								p = (Piece) peices.get(pos);	
							}
							peices.get(pos).setRenderPriority(1);
							p.dropTo(700);
							refreshBoard(p.curPosX, p.curPosY, peices);
						}
					} else if (e instanceof Jerry) {
						ArrayList<GameObject> peices = ObjectHandler.getObjectsByName("Piece");
						if (peices != null && getNumMoves(boardState) % 9 == 0) {
							DragonCoffee coffee = new DragonCoffee ();
							coffee.declare(180, 470);
							for (int i = 0; i < boardState.length; i++) {
								if (boardState[i][5] != 0) {
									Piece toDrop = getPieceAt (i,5);
									toDrop.setCurPosY(-1);
									toDrop.setCurPosX(-1);
									
									toDrop.setRenderPriority(5);
									
									toDrop.dropTo(700);
									refreshBoard(i, 5, peices);
								}
							}
						}
					} else if (e instanceof Imagamer) {
						if (getNumMoves(boardState) % 3 == 0) {
							for (int i = 0; i < boardState.length; i++) {
								for (int j = 0; j < boardState[i].length; j++) {
									if (boardState[i][j] == 1) {
										boardState[i][j] = 2;
										getPieceAt(i,j).setColor(e.pieceType);
									} else if (boardState[i][j] == 2) {
										boardState[i][j] = 1;
										getPieceAt(i,j).setColor(0);
									}
								}
							}
						}
					}
					
					
					
					int chosenMove = e.getMove(boardState);
					int columToChange = getFirstOpen(chosenMove);
					
					
					if (columToChange == -1 && (e instanceof LeftLarry)) {
						turn = 0;
						
						Random rand = new Random ();
						int choice = rand.nextInt(4);
						if (peicePos == 0 && !blocked) {
							if (choice == 2) {
								e.playSound("LLOverflow1.wav");
							}
							if (choice == 3) {
								e.playSound("LLOverflow2.wav");
							}
						}
						else if (!blocked) {
							if (choice == 2) {
								e.playSound("LLDialog1.wav");
							}
							if (choice == 3) {
								e.playSound("LLDialog2.wav");
							}
						}
						else if (blocked) { 
							blocked = false;
						}
						timer = rand.nextInt(10) + 20;
						
						toDrop.bounceLeft();
						toDrop.declare(170 + 10,0);
						
					
						toDrop = new Piece (0);
						
						toDrop.hide();
						return;
						
					}
					
					else if (e instanceof DeliriousDerek) {
						boolean removePiece = false;
						ArrayList<GameObject> peices = ObjectHandler.getObjectsByName("Piece");
						if (peices != null && getNumMoves(boardState) % 3 == 0) {
							Random r = new Random();
							int pos = r.nextInt(peices.size());
							Piece p = (Piece) peices.get(pos);
							int x = (int) ((-180 + p.getX())/82);
							int y = (int) ((-69 + p.getY())/78);
							while (x > 7 || y > 6  || (boardState[x][y] == 0 || x == chosenMove)) {
								pos = r.nextInt(peices.size());
								p = (Piece) peices.get(pos);
								x = (int) ((-180 + p.getX())/82);
								y = (int) ((-69 + p.getY())/78);
							
							}
							peices.get(pos).setRenderPriority(1);
							p.dropTo(700);
							refreshBoard(x, y, peices);
							removePiece = true;
						}
						Random dr = new Random();
						if (removePiece) {
							int lol = dr.nextInt(2);
							if (lol == 0) e.playSound("DeliriousDerekSpecialMove1.wav");
							else e.playSound("DeliriousDerekSpecialMove2.wav");
						}
						else {
							int lol = dr.nextInt(4);
							if (lol == 0) e.playSound("DeliriousDerekDialog1.wav");
						}
					}
					else if (e instanceof MirroredMeryl) {
						Random r = new Random();
						int pos = r.nextInt(8);
						if (pos >= 6) {
							if (pos == 6) e.playSound("MMDialog1.wav");
							else e.playSound("MMDialog2.wav");
						}
					}
					else if (e instanceof RandomRandy) {
						Random r = new Random();
						int pos = r.nextInt(4);
						if (pos == 0) e.playSound("RRDialog1.wav");
						else if (pos == 1) e.playSound("RRDialog2.wav");
						else if (pos == 2) e.playSound("RRDialog3.wav");
					}
					else if (e instanceof Imagamer) {
						Random r = new Random();
						int pos = r.nextInt(8);
						if (pos == 0)  e.playSound("ImagamerDialog1.wav");
						if (pos == 1)  e.playSound("ImagamerDialog2.wav");
						if (pos == 2)  e.playSound("ImagamerDialog3.wav");
						if (pos == 3)  e.playSound("ImagamerDialog4.wav");
						if (pos == 4)  e.playSound("ImagamerDialog5.wav");
						if (pos == 5)  e.playSound("ImagamerDialog6.wav");
						if (pos == 6)  e.playSound("ImagamerDialog7.wav");
					}
					else if (e instanceof JeffWeiner || e instanceof WeffJiener) {
						Random r = new Random();
						int pos = r.nextInt(4);
						if (pos == 0)	e.playSound("JeffWeinerDialog1.wav");
						if (pos == 1)	e.playSound("JeffWeinerDialog2.wav");
					}
					while (columToChange == -1) {
						chosenMove = e.getMove(boardState);
						columToChange = getFirstOpen(chosenMove);
					}

					boardState[chosenMove][columToChange] = 2;
					toDrop.declare(170 + 10 + (82 * chosenMove), 0);
					toDrop.setCurPosX(chosenMove);
					toDrop.setCurPosY(columToChange);
					toDrop.dropTo(69 + (78 * columToChange));
					
					if (!(e instanceof DarkCoolean)) {
						toDrop = new Piece (0);
					} else {
						toDrop = new Piece (6);
					}
					
					if (checkForWin (boardState) == 2) {
						if (!(e instanceof MirroredMeryl)) {
							turn = -1;
							YouLose you = new YouLose ();
							e.onVictoryLine();
							you.declare();
						} else {
							turn = -1;
							YouWin you = new YouWin ();
							e.onDefeatLine();
							you.declare();
						}
						return;
					}
					
					toDrop.hide();
					
					
					turn = 0;
					
					
					if (e instanceof CheatingCharlie && checkForThree(boardState) != 0) {
						turn = 1;
						toDrop.setColor(e.pieceType);
						toDrop.show();
					}
					
					Random rand = new Random ();
					
					timer = rand.nextInt(10) + 20;
					
				} else {
					timer = timer - 1;
				}
			}
		} else {
			menu.frameEvent();
		}
	}

	private Piece getPieceAt (int x, int y) {
		ArrayList <GameObject> pieces = ObjectHandler.getObjectsByName("Piece");
		
		for (int i = 0; i < pieces.size(); i++) {
			Piece cur = (Piece) pieces.get(i);
			if (cur.getCurPosX() == x && cur.getCurPosY() == y ) {
				return (Piece)pieces.get(i);
			}
		}
		return null;
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
		if (e instanceof LeftLarry /*|| e instanceof HorizontalHenry*/) {
			turn = 1;
			toDrop.forget();
			toDrop = new Piece(e.pieceType);	
		}
		
		back.setSprite(e.background);
		
		if (e instanceof MirroredMeryl) {
			menu = null;	
		}
		
		if (e instanceof DarkCoolean) {
			toDrop.setColor(6);
		}
		
		
		e.setX(770);
		e.setY(20);
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
			if (p.getCurPosX() == x && p.getCurPosY() < y) {
				p.setCurPosY(p.getCurPosY() + 1);
				p.dropTo(69 + (p.getCurPosY() * 78));
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
	
	private int[] xDir = {1, 0, 1, -1};
	private int[] yDir = {1, -1, 0, -1};
	public int checkForWin(int[][] board) {
		int check, count = 1;
		for (int wx = 0; wx < 7; wx++) {
			for (int wy = 0; wy < 6; wy++) {
				if (board[wx][wy] != 0) {
					check = board[wx][wy];
					for (int q = 0; q < 4; q++) {
						int xMove = wx + xDir[q];
						int yMove = wy + yDir[q];
						while (xMove > -1 && xMove < 7 && yMove > -1 && yMove < 6) {
							if (board[xMove][yMove] == 0 || 
									board[xMove][yMove] == 
									((check == 1) ? 2 : 1)) break;
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
	
	
	public int checkForFive(int[][] board) {
		int check, count = 1;
		for (int wx = 0; wx < 7; wx++) {
			for (int wy = 0; wy < 6; wy++) {
				if (board[wx][wy] != 0) {
					check = board[wx][wy];
					for (int q = 0; q < 4; q++) {
						int xMove = wx + xDir[q];
						int yMove = wy + yDir[q];
						while (xMove > -1 && xMove < 7 && yMove > -1 && yMove < 6) {
							if (board[xMove][yMove] == 0 || 
									board[xMove][yMove] == 
									((check == 1) ? 2 : 1)) break;
							if (board[xMove][yMove] == check) {
								count++;
							}
							xMove += xDir[q];
							yMove += yDir[q];
						}
						if (count >= 5) {
							return check;
						}
						count = 1;
					}
				}
			}
		}
		return 0;
	}
	
	public int checkForThree(int[][] board) {
		int check, count = 1;
		for (int wx = 0; wx < 7; wx++) {
			for (int wy = 0; wy < 6; wy++) {
				if (board[wx][wy] != 0) {
					check = board[wx][wy];
					for (int q = 0; q <4; q++) {
						int xMove = wx + xDir[q];
						int yMove = wy + yDir[q];
						while (xMove > -1 && xMove < 7 && yMove > -1 && yMove < 6) {
							if (board[xMove][yMove] == 0 || 
									board[xMove][yMove] == 
									((check == 1) ? 2 : 1)) break;
							if (board[xMove][yMove] == check) {
								count++;
							}
							xMove += xDir[q];
							yMove += yDir[q];
						}
						if (count >= 3) {
							return check;
						}
						count = 1;
					}
				}
			}
		}
		return 0;
	}
	
	public class Indicater extends GameObject {
		public Indicater () {
			this.setSprite(new Sprite ("resources/sprites/indicator.png"));
		}
	}
	
	public class DragonCoffee extends GameObject {
		int curFrame = -10000;
		
		public DragonCoffee () {
			this.setSprite(new Sprite ("resources/sprites/config/coffeeSpit.txt"));
			this.getAnimationHandler().setFrameTime(20);
			this.setRenderPriority(4);
			this.getAnimationHandler().setFlipHorizontal(true);
		}
		
		@Override
		public void frameEvent () {
			if (this.getAnimationHandler().getFrame() < curFrame) {
				this.forget();
			}
			curFrame = this.getAnimationHandler().getFrame();
		}
	}
	
	public class SpecialMenu extends GameObject {
		
		SpecialButton button = new SpecialButton ();
		
		CrownIcon crown = null;
		MirrorIcon mirror = null;
		LeftIcon left = null;
		RandomIcon rand = null;
		
		int hovering = 3;
		
		SpecialSelector select = new SpecialSelector ();
		
		boolean isUsed = false;
		
		public SpecialMenu () {
		
			if (unlockedMoves[0]) {
				crown = new CrownIcon();
			}
			
			if (unlockedMoves[1]) {
				mirror = new MirrorIcon();
			}
			
			if (unlockedMoves[2]) {
				left = new LeftIcon();
			}
			
			if (unlockedMoves[3]) {
				rand = new RandomIcon();
			}
			
			if (unlockedMoves[3]) {
				hovering = 3;
			} else if (unlockedMoves[0]) {
				hovering = 0;
			} else if (unlockedMoves [2]) {
				hovering = 2;
			} else {
				hovering = 1;
			}
			
		}
		
		@Override
		public void frameEvent () {
			if (keyPressed ('D')) {
				switch (hovering) {
				case 0: 
					leaveSpecialMenu();
					break;
				case 1:
					if (!unlockedMoves[0]) {
						leaveSpecialMenu();
					} else {
						hovering = 0;
					}
					break;
				case 2:
					if (!unlockedMoves[3]) {
						leaveSpecialMenu();
					} else {
						hovering = 3;
					}
					break;
				case 3:
					leaveSpecialMenu();
					break;
				}
			}
			
			if (keyPressed ('A')) {
				switch (hovering) {
				case 0:
					if (unlockedMoves[1]) {
						hovering = 1;
					}
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					if (unlockedMoves[2]) {
						hovering = 2;
					}
					break;
				}
			}
			
			if (keyPressed ('W')) {
				switch (hovering) {
				case 0:
					if (unlockedMoves[3]) {
						hovering = 3;
					}
					break;
				case 1:
					if (unlockedMoves[2]) {
						hovering = 2;
					}
					break;
				case 2:
					break;
				case 3:
					break;
				}
			}
			
			if (keyPressed ('S')) {
				switch (hovering) {
				case 0:
					break;
				case 1:
					break;
				case 2:
					if (unlockedMoves[1]) {
						hovering = 1;
					}
					break;
				case 3:
					if (unlockedMoves[0]) {
						hovering = 0;
					}
					break;
				}
			}
			
			if (keyPressed (GLFW.GLFW_KEY_ENTER)) {
				switch (hovering) {
				case 0:
					crown.onSelect();
					break;
				case 1:
					mirror.onSelect();
					break;
				case 2:
					left.onSelect();
					break;
				case 3:
					rand.onSelect();
					break;
				}
			}
			
		}
		
		@Override
		public void draw () {
			button.setX(this.getX());
			button.setY(this.getY());
			
			button.draw();
			
			if (unlockedMoves[0]) {
				crown.setX(this.getX() + 75);
				crown.setY(this.getY() + 160);
				crown.draw();
			}
			
			if (unlockedMoves[1]) {
				mirror.setX(this.getX());
				mirror.setY(this.getY() + 160);
				mirror.draw();
			}
			
			if (unlockedMoves[2]) {
				left.setX(this.getX());
				left.setY(this.getY() + 80);
				left.draw();
			}
			
			if (unlockedMoves[3]) {
				rand.setX(this.getX() + 75);
				rand.setY(this.getY() + 80);
				rand.draw();
			}
			
			if (inSpecialMenu) {
				switch (hovering) {
				case 0:
					select.setX(this.getX() + 75);
					select.setY(this.getY() + 160);
					select.draw();
					break;
				case 1:
					select.setX(this.getX());
					select.setY(this.getY() + 160);
					select.draw();
					break;
				case 2:
					select.setX(this.getX());
					select.setY(this.getY() + 80);
					select.draw();
					break;
				case 3:
					select.setX(this.getX() + 75);
					select.setY(this.getY() + 80);
					select.draw();
					break;
					
				}
			}
			
		}
		
		public void useUpMove () {
			isUsed = true;
			if (crown != null) {
				crown.setSprite(new Sprite ("resources/sprites/King Special used.png"));
			}
			if (left != null) {
				left.setSprite(new Sprite ("resources/sprites/Left Icon used.png"));
			}
			if (mirror != null) {
				mirror.setSprite(new Sprite ("resources/sprites/Reflect special used.png"));
			}
			if (rand != null) {
				rand.setSprite(new Sprite ("resources/sprites/Random Special used.png"));
			}
			
			button.setSprite(new Sprite ("resources/sprites/Special Sprite used.png"));
		}
		
		public boolean isUsedUp () {
			return isUsed;
		}
		
		private void leaveSpecialMenu() {
			inSpecialMenu = false;
		}
		
	}
	
	public class SpecialSelector extends GameObject {
		public SpecialSelector ( ) {
			this.setSprite(new Sprite ("resources/sprites/selecter.png"));
		}
	}
	
	public class SpecialButton extends GameObject {
		public SpecialButton () {
			this.setSprite(new Sprite ("resources/sprites/Special Sprite.png"));
		}
	}
	
	public class Icon extends GameObject {
		
		String name = "";
		
		public Icon () {
			
		}
		
		public void onSelect () {
			
		}
	}
	
	public class CrownIcon extends Icon {
		int uses = 3;
		
		public CrownIcon () {
			this.setSprite(new Sprite ("resources/sprites/King Special.png"));
		}
		
		@Override
		public void onSelect () {
			if (uses > 0) {
				e.difficulty = e.difficulty + 1;
			} 
			uses = uses - 1;
			
			if (uses == 0) {
				this.setSprite(new Sprite ("resources/sprites/King Special used.png"));
				
			}
			
		}
	}
	
	public class MirrorIcon extends Icon {
		public MirrorIcon () {
			this.setSprite(new Sprite ("resources/sprites/Reflect special.png"));
		}
		
		@Override
		public void onSelect () {
			mirrorMove = true;
			menu.useUpMove();
			inSpecialMenu = false;
		}
	}
	
	public class LeftIcon extends Icon {
		public LeftIcon () {
			this.setSprite(new Sprite ("resources/sprites/Left Icon.png"));
		}
		
		@Override
		public void onSelect () {
			int firstOpen = getFirstOpen(0);
			
			if (firstOpen == -1) {
				return;
			}
			
			toDrop.setX(180);
			
			toDrop.setCurPosX(0);
			toDrop.setCurPosY(firstOpen);
			
			toDrop.dropTo(69 + (78 * firstOpen));
			boardState[0][firstOpen] = 1;
			
			int secondOpen = getFirstOpen(0);
			
			if (secondOpen != -1) {
				toDrop = new Piece (0);
				toDrop.declare(180,0);
				toDrop.setCurPosX(0);
				toDrop.setCurPosY(secondOpen);
				toDrop.dropTo(69 + (78 * secondOpen));
				boardState[0][secondOpen] = 1;
			} else {
				toDrop.setX(180);
				toDrop.bounceLeft();
			}
			
			
			toDrop = new Piece (e.pieceType);
			turn = 1;
			
			peicePos = 0;
			
			if (e instanceof WeffJiener) {
				if (checkForFive (boardState) == 1) {
					turn = -1;
					YouWin you = new YouWin ();
					e.onDefeatLine();
					you.declare();
				}
			} else {
				if (checkForWin (boardState) == 1) {
					
					if (checkForWin (boardState) == 1) {
						if (!(e instanceof MirroredMeryl)) {
							turn = -1;
							YouWin you = new YouWin ();
							e.onDefeatLine();
							you.declare();
						} else {
							turn = -1;
							YouLose you = new YouLose ();
							e.onVictoryLine();
							you.declare();
						}
					}
				}
			}
			
			
			Random rand = new Random ();
			
			timer = rand.nextInt(20) + 50;
			menu.useUpMove();
			inSpecialMenu = false;
		}
	}
	
	public class RandomIcon extends Icon {
		public RandomIcon () {
			this.setSprite(new Sprite ("resources/sprites/Random Special.png"));
		}
		
		@Override
		public void onSelect () {
			Random rand = new Random ();
			
			int dropPos = rand.nextInt(7);
			
			int firstOpen = getFirstOpen(dropPos);
			
			
			if (firstOpen != -1) {
				toDrop.setX(180 + (82 * dropPos));
				toDrop.setCurPosX(dropPos);
				toDrop.setCurPosY(firstOpen);
				toDrop.dropTo(69 + (78 * firstOpen));
				boardState[dropPos][firstOpen] = 1;
			} else {
				toDrop.setX(180 + (82 * dropPos));
				toDrop.bounceLeft();
			}
			
			int dropPos2 = rand.nextInt(7);
			int secondOpen = getFirstOpen(dropPos2);
			
			if (secondOpen != -1) {
				toDrop = new Piece (0);
				toDrop.declare(180 + (82 * dropPos2),0);
				toDrop.setCurPosX(dropPos2);
				toDrop.setCurPosY(secondOpen);
				toDrop.dropTo(69 + (78 * secondOpen));
				boardState[dropPos2][secondOpen] = 1;
			} else {
				toDrop = new Piece (0);
				toDrop.declare(180 + (82 * dropPos2),0);
				toDrop.bounceLeft();
			}
			
			int dropPos3 = rand.nextInt(7);
			int thirdOpen = getFirstOpen(dropPos3);
			
			if (thirdOpen != -1) {
				toDrop = new Piece (0);
				toDrop.declare(180 + (82 * dropPos3),0);
				toDrop.setCurPosX(dropPos3);
				toDrop.setCurPosY(thirdOpen);
				toDrop.dropTo(69 + (78 * thirdOpen));
				boardState[dropPos3][thirdOpen] = 1;
			} else {
				toDrop = new Piece (0);
				toDrop.declare(180 + (82 * dropPos3),0);
				toDrop.bounceLeft();
			}
			
			
			int dropPos4 = rand.nextInt(7);
			int fourOpen = getFirstOpen(dropPos4);
			
			if (fourOpen != -1) {
				toDrop = new Piece (e.pieceType);
				toDrop.declare(180 + (82 * dropPos4),0);
				toDrop.setCurPosX(dropPos4);
				toDrop.setCurPosY(fourOpen);
				toDrop.dropTo(69 + (78 * fourOpen));
				boardState[dropPos4][fourOpen] = 2;
			} else {
				toDrop = new Piece (e.pieceType);
				toDrop.declare(180 + (82 * dropPos4),0);
				toDrop.bounceLeft();
			}
			
			toDrop = new Piece (e.pieceType);
			turn = 1;
			
			peicePos = 0;
			
			if (e instanceof WeffJiener) {
				if (checkForFive (boardState) == 1) {
					turn = -1;
					YouWin you = new YouWin ();
					e.onDefeatLine();
					you.declare();
				}
			} else {
				if (checkForWin (boardState) == 1) {
					if (checkForWin (boardState) == 1) {
						if (!(e instanceof MirroredMeryl)) {
							turn = -1;
							YouWin you = new YouWin ();
							e.onDefeatLine();
							you.declare();
						} else {
							turn = -1;
							YouLose you = new YouLose ();
							e.onVictoryLine();
							you.declare();
						}
					}
				}
			}
			if (checkForWin (boardState) == 2) {
				if (!(e instanceof MirroredMeryl)) {
					turn = -1;
					YouLose you = new YouLose ();
					e.onVictoryLine();
					you.declare();
				} else {
					turn = -1;
					YouWin you = new YouWin ();
					e.onDefeatLine();
					you.declare();
				}
			}
			
			
			timer = rand.nextInt(20) + 50;
			menu.useUpMove();
			inSpecialMenu = false;
		
		}
	}
	
	public class Background extends GameObject {
		
		public Background () {
			this.setSprite(new Sprite ("resources/sprites/Background1.png"));
		}
	}
	
	public class Board extends GameObject {
		
		public Board () {
			this.setSprite(new Sprite ("resources/sprites/config/RGB board.txt"));
			this.getAnimationHandler ().setFrameTime (0.1);
		}
	}
	
	public class YouWin extends YouSomething {
		
		int curScaleX = 80;
		int curScaleY = 45;
		
		double scaleSpeed = 2;
		
		Sprite og;
		
		public YouWin () {
			this.setSprite(new Sprite ("resources/sprites/you win.png"));
			if (og == null) {
				og = new Sprite (this.getSprite());
			}
			//Sprite.scale(getSprite(), 80, 45);
			this.setX(480);
			this.setY(270);
		}
		
		@Override
		public void frameEvent () {
			if (curScaleX != 960 || curScaleY != 540) {
				if (scaleSpeed < 10.1) {
					scaleSpeed = scaleSpeed + .3;
				}
				
				double scaleVelX = 960/(11-scaleSpeed);
				double scaleVelY = 540/(11-scaleSpeed);
				
				if (curScaleX != 960) {
					curScaleX = (int) Math.ceil(curScaleX + scaleVelX);
					if (curScaleX > 960) {
						curScaleX = 960;
					}
					
					if (this.getX() > 0) {
						this.setX(this.getX() - (scaleVelX/2));
						if (this.getX() < 0) {
							this.setX(0);
						}
					}
				}
				
				if (curScaleY != 540) {
					curScaleY = (int) Math.ceil(curScaleY + scaleVelY);
					if (curScaleY > 540) {
						curScaleY = 540;
					}
					if (this.getY() > 0) {
						this.setY(this.getY() - (scaleVelY/2));
						if (this.getY() < 0) {
							this.setY(0);
						}
					}
				}
				Sprite scale = new Sprite (og);
				//Sprite.draw(scale, curScaleX, curScaleY);
				this.setSprite (scale);
			}
			
			Random rand = new Random ();
			
			if (rand.nextInt(20) == 10) {
				FireworksRocket r = new FireworksRocket (rand.nextInt(5));
				r.declare(rand.nextInt(540), 1000);
			}
		}
		
	}
	public class YouLose extends YouSomething{
		
		int curScaleX = 80;
		int curScaleY = 45;
		
		double scaleSpeed = 2;
		
		Sprite og;
		
		public YouLose () {
			this.setSprite(new Sprite ("resources/sprites/you_lose.png"));
			if (og == null) {
				og = new Sprite (this.getSprite());
			}
			//Sprite.scale(getSprite(), 80, 45);
			this.setX(480);
			this.setY(270);
		}
		
		@Override
		public void frameEvent () {
			if (curScaleX != 960 || curScaleY != 540) {
				if (scaleSpeed < 10.1) {
					scaleSpeed = scaleSpeed + .3;
				}
				
				double scaleVelX = 960/(11-scaleSpeed);
				double scaleVelY = 540/(11-scaleSpeed);
				
				if (curScaleX != 960) {
					curScaleX = (int) Math.ceil(curScaleX + scaleVelX);
					if (curScaleX > 960) {
						curScaleX = 960;
					}
					
					if (this.getX() > 0) {
						this.setX(this.getX() - (scaleVelX/2));
						if (this.getX() < 0) {
							this.setX(0);
						}
					}
				}
				
				if (curScaleY != 540) {
					curScaleY = (int) Math.ceil(curScaleY + scaleVelY);
					if (curScaleY > 540) {
						curScaleY = 540;
					}
					if (this.getY() > 0) {
						this.setY(this.getY() - (scaleVelY/2));
						if (this.getY() < 0) {
							this.setY(0);
						}
					}
				}
				Sprite scale = new Sprite (og);
				//Sprite.draw(scale, curScaleX, curScaleY);
				this.setSprite (scale);
			}
			
			Random rand = new Random ();
			
			if (rand.nextInt(20) == 10) {
				FireworksRocket r = new FireworksRocket (rand.nextInt(5));
				r.declare(rand.nextInt(540), 1000);
			}
		}
		
	}
	public class YouSomething extends GameObject{
		public void frameEvent() {
			
		}
	}
	
	public class FireworksRocket extends GameObject{
		
		double vy = 30;
		
		int color = 0;
		
		public FireworksRocket (int color) {
			this.setSprite(new Sprite ("resources/sprites/fireworks rocket.png"));
		
			Random rand = new Random ();
			
			vy = (rand.nextDouble() * 5)+25;
			
			this.color = color;
			
			this.setRenderPriority(-1);
		}
		
		@Override
		public void frameEvent () {
			vy = vy - .4;		
			if (vy < 0) {
				detonate();
				return;
			}
			this.setY(this.getY() - vy);
			
			Random rand = new Random ();
			
			FireworksParticle p = new FireworksParticle ();
			p.declare(this.getX() + rand.nextInt(20) - 10, this.getY() + 40);
		}
		
		private void detonate () {
			this.forget();
			
			FireworksBlast b1 = new FireworksBlast(color);
			FireworksBlast b2 = new FireworksBlast(color);
			FireworksBlast b3 = new FireworksBlast(color);
			FireworksBlast b4 = new FireworksBlast(color);
			FireworksBlast b5 = new FireworksBlast(color);
			FireworksBlast b6 = new FireworksBlast(color);
			FireworksBlast b7 = new FireworksBlast(color);
			FireworksBlast b8 = new FireworksBlast(color);
			FireworksBlast b9 = new FireworksBlast(color);
			
			
			FireworksBlast b1s = new FireworksBlast(color);
			FireworksBlast b2s = new FireworksBlast(color);
			FireworksBlast b3s = new FireworksBlast(color);
			FireworksBlast b4s = new FireworksBlast(color);
			FireworksBlast b5s = new FireworksBlast(color);
			FireworksBlast b6s= new FireworksBlast(color);
			FireworksBlast b7s = new FireworksBlast(color);
			FireworksBlast b8s = new FireworksBlast(color);
			FireworksBlast b9s = new FireworksBlast(color);
			
			
			
			double bx = this.getX() + 10/2;
			double by = this.getY() + 40/2;
			
			
			b1.declare(bx,by);
			b2.declare(bx,by);
			b3.declare(bx,by);
			b4.declare(bx,by);
			b5.declare(bx,by);
			b6.declare(bx,by);
			b7.declare(bx,by);
			b8.declare(bx,by);
			b9.declare(bx,by);
			
			b1s.declare(bx,by);
			b2s.declare(bx,by);
			b3s.declare(bx,by);
			b4s.declare(bx,by);
			b5s.declare(bx,by);
			b6s.declare(bx,by);
			b7s.declare(bx,by);
			b8s.declare(bx,by);
			b9s.declare(bx,by);
			
			int bombSpeed = 4; //im probably gonna wanna tweek this without having to change every throw
			
			b1.throwObj(Math.PI/18,bombSpeed);
		
			b2.throwObj(5*Math.PI/18,bombSpeed);
			
			b3.throwObj(9*Math.PI/18,bombSpeed);
			
			b4.throwObj(13*Math.PI/18,bombSpeed);
			
			b5.throwObj(17*Math.PI/18,bombSpeed);
			
			b6.throwObj(21*Math.PI/18,bombSpeed);
			
			b7.throwObj(25*Math.PI/18,bombSpeed);
			
			b8.throwObj(29*Math.PI/18,bombSpeed);
			
			b9.throwObj(33*Math.PI/18,bombSpeed);
			
			
			b1s.throwObj(Math.PI/18,bombSpeed - 2);
			
			b2s.throwObj(5*Math.PI/18,bombSpeed - 2);
			
			b3s.throwObj(9*Math.PI/18,bombSpeed - 2);
			
			b4s.throwObj(13*Math.PI/18,bombSpeed - 2);
			
			b5s.throwObj(17*Math.PI/18,bombSpeed - 2);
			
			b6s.throwObj(21*Math.PI/18,bombSpeed - 2);
			
			b7s.throwObj(25*Math.PI/18,bombSpeed - 2);
			
			b8s.throwObj(29*Math.PI/18,bombSpeed - 2);
			
			b9s.throwObj(33*Math.PI/18,bombSpeed - 2);
			
			
		}
		
	}
	
	public class FireworksBlast extends GameObject {
		
		int timer = 30;
		
		public FireworksBlast (int color) {
			
			this.setRenderPriority(-1);
			switch (color) {
			case 0:
				this.setSprite(new Sprite ("resources/sprites/firework blue.png"));
				break;
			case 1:
				this.setSprite(new Sprite ("resources/sprites/firework yellow.png"));
				break;
			case 2:
				this.setSprite(new Sprite ("resources/sprites/firework red.png"));
				break;
			case 3:
				this.setSprite(new Sprite ("resources/sprites/firework purple.png"));
				break;
			case 4:
				this.setSprite(new Sprite ("resources/sprites/firework green.png"));
				break;
				
			}
		}
		
		@Override
		public void frameEvent () {
			super.frameEvent();
			timer = timer - 1;
			if (timer == 0) {
				this.forget();
			}
		}
	}
	
	public class FireworksParticle extends GameObject {
		
		int timer = 6;
		
		public FireworksParticle () {
			this.setSprite(new Sprite ("resources/sprites/fireworks partical.png"));
			
			
			this.setRenderPriority(-1);
		}
		
		@Override
		public void frameEvent () {
			if (timer == 0) {
				this.forget();
			}
			timer = timer - 1;
		}
	}
}
