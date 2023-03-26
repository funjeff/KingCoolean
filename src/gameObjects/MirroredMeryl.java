package gameObjects;

import java.util.Arrays;
import java.util.Random;

import engine.GameCode;
import engine.GameObject;
import engine.Sprite;

public class MirroredMeryl extends Enemy{
		
		int [] curHeights = new int [7];
	
	//	boolean firstMove = true;
		
		public MirroredMeryl (Connect connect) {
			super(connect);
			this.setSprite(new Sprite ("resources/sprites/config/MirroredMeryl.txt"));
			this.getAnimationHandler().setFlipHorizontal(false);
			Arrays.fill(curHeights, 5);
			pieceType = 3;
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
			this.mapConnect.setAbove(mapConnect.getUpConnectByPosition());
			this.mapConnect.getAbove().setBelow(this.mapConnect);
			GameCode.map.declare();
		}
}
