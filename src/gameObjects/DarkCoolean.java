package gameObjects;

public class DarkCoolean extends Enemy{
	
	public DarkCoolean (Connect c) {
		super (c);
		this.pieceType = 6;
		this.difficulty = 7;
		this.playSound("darkCoolean.wav");
	}

}
