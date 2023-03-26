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
	Enemy e = new Enemy (new Connect());
	Background back = new Background ();
	
	int turn = 0;
	
	Piece toDrop = new Piece(0);
	
	int peicePos = 0;
	
	int timer = 0;
	
	int [][] boardState = new int [7][6];
	
	boolean [] unlockedMoves = new boolean [4];
	
	SpecialMenu m = new SpecialMenu ();
	
	public ConnectFourGame () {
		b.setX(170);
		b.setY(69);
		
		k.setX(10);
		k.setY(380);
		
		e.setX(750);
		e.setY(200);
		
		toDrop.setX(170 + 10);
		toDrop.declare();
		
		b.getAnimationHandler().setFrameTime(200);
		back.declare();
		
		back.setRenderPriority(-4);
		this.setRenderPriority(-1);
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
		
		if (turn == -1) {
			if (keyPressed (KeyEvent.VK_ENTER)) {
				e.onDefeat();
				ObjectHandler.getObjectsByName("YouWin").get(0).forget();
				
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
				
				this.forget();
			}
		}
		
		
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
					toDrop = new Piece (e.pieceType);
					boardState[peicePos][firstOpen] = 1;
					peicePos = 0;
					
					if (checkForWin (boardState) == 1) {
						turn = -1;
						YouWin you = new YouWin ();
						you.declare();
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
				int chosenMove = e.getMove(boardState);
				int columToChange = getFirstOpen(chosenMove);
				
				if (columToChange == -1 && (e instanceof LeftLarry)) {
					turn = 0;
					
					Random rand = new Random ();
					
					timer = rand.nextInt(10) + 20;
					
					toDrop.bounceLeft();
					toDrop.declare(170 + 10,0);
					
					if (!(e instanceof DarkCoolean)) {
						toDrop = new Piece (0);
					} else {
						toDrop = new Piece (6);
					}
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
						while (x < 7 && y < 6  && (boardState[x][y] == 0 || x == chosenMove)) {
							pos = r.nextInt(0, peices.size());
							p = (Piece) peices.get(pos);
							x = (int) ((-180 + p.getX())/82);
							y = (int) ((-69 + p.getY())/78);
						
						}
						peices.get(pos).setRenderPriority(1);
						p.dropTo(700);
						refreshBoard(x, y, peices);
					}
				}
				
				boardState[chosenMove][columToChange] = 2;
				toDrop.declare(170 + 10 + (82 * chosenMove), 0);
				toDrop.dropTo(69 + (78 * columToChange));
				
				if (!(e instanceof DarkCoolean)) {
					toDrop = new Piece (0);
				} else {
					toDrop = new Piece (6);
				}
				
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
			toDrop = new Piece(e.pieceType);	
		}
		
		if (e instanceof DarkCoolean) {
			toDrop.setColor(6);
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
	
	
	public class SpecialMenu extends GameObject {
		
		SpecialButton button = new SpecialButton ();
		
		public SpecialMenu () {
		
		}
		
		@Override
		public void draw () {
			button.setX(this.getX());
			button.setY(this.getY());
			
			button.draw();
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
		public CrownIcon () {
			this.setSprite(new Sprite ("resources/sprites/King Special.png"));
		}
		
		@Override
		public void onSelect () {
			
		}
	}
	
	public class MirrorIcon extends Icon {
		public MirrorIcon () {
			this.setSprite(new Sprite ("resources/sprites/Reflect special.png"));
		}
		
		@Override
		public void onSelect () {
			
		}
	}
	
	public class LeftIcon extends Icon {
		public LeftIcon () {
			this.setSprite(new Sprite ("resources/sprites/Left Icon.png"));
		}
		
		@Override
		public void onSelect () {
			
		}
	}
	
	public class RandomIcon extends Icon {
		public RandomIcon () {
			this.setSprite(new Sprite ("resources/sprites/Left Icon.png"));
		}
		
		@Override
		public void onSelect () {
			
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
		}
	}
	
	public class YouWin extends GameObject {
		
		int curScaleX = 80;
		int curScaleY = 45;
		
		double scaleSpeed = 2;
		
		static Sprite og;
		
		public YouWin () {
			this.setSprite(new Sprite ("resources/sprites/you win.png"));
			if (og == null) {
				og = new Sprite (this.getSprite());
			}
			Sprite.scale(getSprite(), 80, 45);
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
				Sprite.scale(scale, curScaleX, curScaleY);
				this.setSprite (scale);
			}
			
			Random rand = new Random ();
			
			if (rand.nextInt(20) == 10) {
				FireworksRocket r = new FireworksRocket (rand.nextInt(5));
				r.declare(rand.nextInt(540), 1000);
			}
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
