package engine;

import java.util.Random;

public class State {
	private int play, player, value;
	public int[][] board;
	public State[] children;
	
	public State(int p, int[][] board, int player) {
		this.play = p;
		this.board = board;
		children = new State[6];
		this.player = player;
	}
	
	public int getPlay() {
		return play;
	}
	public int getPlayer() {
		return player;
	}
	public void addChild(State child) {
		int pos = findPos();
		if (pos != -1) children[pos] = child;
	}
	public int findPos() {
		for (int i = 0; i < children.length; i++) {
			if (children[i] == null) return i;
		}
		return -1;
	}
	public int findChildPlay(int val) {
		int[] checkOrder;
		Random r = new Random();
		int order = r.nextInt(6);
		if (order == 0) checkOrder = new int[]{0, 1, 2, 3, 4, 5};
		else if (order == 1) checkOrder = new int[]{1, 2, 3, 4, 5, 0};
		else if (order == 2) checkOrder = new int[]{2, 3, 4, 5, 0, 1};
		else if (order == 3) checkOrder = new int[]{3, 4, 5, 0, 1, 2};
		else if (order == 4) checkOrder = new int[]{4, 5, 0, 1, 2, 3};
		else checkOrder = new int[]{5, 0, 1, 2, 3, 4};
		for (int i = 0; i < checkOrder.length; i++) {
			if (children[checkOrder[i]].value == val) return children[checkOrder[i]].play;
		}
		return -2;
	}
	public void setValue(int val) {
		this.value = val;
	}
	public int getValue() {
		return value;
	}
}
