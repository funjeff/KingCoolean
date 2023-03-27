package gameObjects;

import java.util.Arrays;
import java.util.Random;

import engine.GameCode;
import engine.GameObject;
import engine.Sprite;
import javafx.scene.media.AudioClip;

public class MirroredMeryl extends Enemy{
		
		int [] curHeights = new int [7];
	
	//	boolean firstMove = true;
		
		AudioClip clip = new AudioClip ("file:resources/music/idk2.wav");
		
		public MirroredMeryl (Connect connect) {
			super(connect);
			this.setSprite(new Sprite ("resources/sprites/config/MirroredMeryl.txt"));
			this.getAnimationHandler().setFlipHorizontal(false);
			Arrays.fill(curHeights, 5);
			pieceType = 3;
			clip.setCycleCount (100);
			clip.play ();
		}
		
		@Override
		public int getMove (int [][] boardState) {
//
//			if (firstMove) {
//				Random r = new Random ();
//				firstMove = false;
//				
//				
//				int choice = r.nextInt(7);
//				curHeights[choice] = curHeights[choice] - 1;
//				return choice;
//			}
			
			int playerMove = 0;
			for (int i = 0; i < curHeights.length; i++) {
				if (curHeights[i] < 0) {
					continue;
				}
				if (boardState[i][curHeights[i]] != 0) {
					playerMove = i;
					curHeights[i] = curHeights[i] -1;
					curHeights[6 -i] = curHeights[6 -i] -1;
					break;
				}
			}
			
			
			return 6-playerMove;
		}
		
		@Override
		public void onDefeat () {
			this.mapConnect.setLeft(mapConnect.getLeftConnectByPosition());
			this.mapConnect.getLeft().setRight(this.mapConnect);
			
			ConnectFourGame.unlockedMoves[1] = true;
			GameCode.map.declare();
			clip.stop ();
		}
		public void onVictory() {
			clip.stop();
			GameCode.map.declare();
		}
		
		@Override
		public void onDefeatLine () {
			this.playSound("MMPlayerWins.wav");
		}

		@Override
		public void onVictoryLine () {
			this.playSound("MMPlayerLoses.wav");
		}

}
