package gameObjects;

import java.awt.event.KeyEvent;
import java.util.Arrays;

import engine.GameObject;
import engine.Sprite;

public class ConnectFourGame extends GameObject {
	Board b = new Board ();
	KingCoolean k = new KingCoolean ();
	Enemy e = new Enemy ();
	
	int turn = 0;
	
	Piece toDrop = new Piece(true);
	
	int peicePos = 0;
	
	int [][] boardState = new int [7][6];
	
	public ConnectFourGame () {
		b.setX(170);
		b.setY(69);
		
		k.setX(10);
		k.setY(200);
		
		e.setX(350);
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
			if (keyPressed('D') && peicePos != 6) {
				peicePos = peicePos + 1;
				toDrop.setX(toDrop.getX() + 82);
			}
			if (keyPressed('A') && peicePos != 0) {
				peicePos = peicePos -1;
				toDrop.setX(toDrop.getX() - 82);
			}
			if (keyPressed (KeyEvent.VK_ENTER)) {
				
				int firstOpen = 5;
				
				while (boardState[peicePos][firstOpen] != 0) {
					firstOpen = firstOpen - 1;
					if (firstOpen == -1) {
						return;
					}
				}
				
				toDrop.dropTo(69 + (78 * firstOpen));
				turn = 1;
				toDrop = new Piece (false);
				toDrop.declare(170 + 10, 0);
				boardState[peicePos][firstOpen] = 1;
				peicePos = 0;
			}
		}
	}
	
	public class Board extends GameObject {
		
		public Board () {
			this.setSprite(new Sprite ("resources/sprites/board.png"));
		}
	}
}
