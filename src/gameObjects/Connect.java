package gameObjects;

import engine.GameCode;
import engine.GameObject;
import engine.Sprite;

public class Connect extends GameObject {
	
	Connect above = null;
	Connect below = null;
	Connect left = null;
	Connect right = null;
	
	ConnectStickUp up = new ConnectStickUp ();
	ConnectStickSide side = new ConnectStickSide ();
	
	Resume resume = new Resume ();
	
	public Connect () {
		this.setSprite(new Sprite ("resources/sprites/linkedinmapIcon.png"));
	}
	
	public void onHover () {
		this.setSprite(new Sprite ("resources/sprites/linkedinmapIconSelected.png"));
	}
	
	public void onNotHover() {
		this.setSprite(new Sprite ("resources/sprites/linkedinmapIcon.png"));
	}
	
	public void onSelect() {
		//override and load missions
	}
	
	
	@Override
	public void draw () {
		super.draw();
		up.setX(this.getX() + (62/2));
		up.setY(this.getY() - 50);
		if (getAbove() != null) {
			up.draw();
		}
		side.setX(this.getX() + 64);
		side.setY(this.getY() + (64/2));
		if (getRight() != null) {
			side.draw();
		}
	}
	
	public Connect getAbove () {
		return above;
	}
	
	public Connect getBelow () {
		return below;
	}
	
	public Connect getRight () {
		return right;
	}
	
	public Connect getLeft () {
		return left;
	}
	
	public void setAbove (Connect above) {
		this.above = above;
		if (this.getY() != 0) {
			above.setY(this.getY() - 114);
		}
		if (this.getX() != 0) {
			above.setX(this.getX());
		}
	}
	
	public void setBelow (Connect below) {
		this.below = below;
		if (this.getY() != 0) {
			below.setY(this.getY() + 114);
		}
		if (this.getX() != 0) {
			below.setX(this.getX());
		}
	}
	
	public void setRight (Connect right) {
		this.right = right;
		if (this.getX() != 0) {
			right.setX(this.getX() + 114);
		}
		if (this.getY() != 0) {
			right.setY(this.getY());
		}
	}
	
	public void setLeft (Connect left) {
		this.left = left;
		if (this.getX() != 0) {
			left.setX(this.getX() - 114);
		}
		if (this.getY() != 0) {
			left.setY(this.getY());
		}
	}
	
	public Resume getResume () {
		return resume;
	}
	
	public class ConnectStickUp extends GameObject{
		
		public ConnectStickUp() {
			this.setSprite(new Sprite ("resources/sprites/connectStickUp.png"));
		}
	}
	
	public class ConnectStickSide extends GameObject{
		
		public ConnectStickSide() {
			this.setSprite(new Sprite ("resources/sprites/connectStickSide.png"));
		}
	}
	
	public class Resume extends GameObject {
		public Resume () {
			this.setSprite(new Sprite ("resources/sprites/resumes/resume size paper.png"));
			this.setX(600);
		}
	}
}


