package engine;

public class State {
	private int play;
	public int[][] board;
	public State[] children;
	
	public State(int p, int[][] board) {
		this.play = p;
		this.board = board;
		children = new State[6];
	}
	
	public int getPlay() {
		return play;
	}
	
}
