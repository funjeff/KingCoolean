package gameObjects;

import java.util.ArrayList;

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
	
	boolean forBoss = false;
	
	boolean accesable = true;
	
	public Connect () {
		if (!forBoss) {
			this.setSprite(new Sprite ("resources/sprites/linkedinmapIcon.png"));
		} else {
			this.setSprite(new Sprite ("resources/sprites/linkedinmapIconBoss.png"));
		}
	}
	
	public void onHover () {
		if (!forBoss) {
			this.setSprite(new Sprite ("resources/sprites/linkedinmapIconSelected.png"));
		} else {
			this.setSprite(new Sprite ("resources/sprites/linkedinmapIconSelectedBoss.png"));
		}
		
	}
	
	public void onNotHover() {
		if (!forBoss) {
			this.setSprite(new Sprite ("resources/sprites/linkedinmapIcon.png"));
		} else {
			this.setSprite(new Sprite ("resources/sprites/linkedinmapIconBoss.png"));
		}
		
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
	
	public void positionAbove (Connect below) {
		this.setY(below.getY() - 114);
		this.setX(below.getX());
	}
	
	public void positionBelow (Connect above) {
		this.setY(above.getY() + 114);
		this.setX(above.getX());
	}
	
	public void positionRight (Connect left) {
		this.setY(left.getY());
		this.setX(left.getX() + 114);
	}
	
	public void positionLeft (Connect right) {
		this.setY(right.getY());
		this.setX(right.getX() - 114);
	}
	
	public void makeForBoss () {
		forBoss = true;
		this.setSprite(new Sprite ("resources/sprites/linkedinmapIconBoss.png"));
	}
	
	public void makeinassesable () {
		accesable = false;
	}
	
	public void makeasseable () {
		accesable = true;
	}
	
	public boolean isAsseable () {
		return accesable;
	}
	
	public Resume getResume () {
		return resume;
	}
	
	public Connect getRightConnectByPosition () {
		ArrayList <Connect> connects = ConnectMap.allConnects;
		
		for (int i = 0; i < connects.size(); i++) {
			if (connects.get(i).getX() == this.getX() + 114 && connects.get(i).getY() == this.getY()) {
				return connects.get(i);
			}
		}
		
		return null;
	}
	
	public Connect getLeftConnectByPosition () {
		ArrayList <Connect> connects = ConnectMap.allConnects;
		
		for (int i = 0; i < connects.size(); i++) {
			if (connects.get(i).getX() == this.getX() - 114 && connects.get(i).getY() == this.getY()) {
				return connects.get(i);
			}
		}
		
		return null;
	}
	
	public Connect getUpConnectByPosition () {
		ArrayList <Connect> connects = ConnectMap.allConnects;
		
		for (int i = 0; i < connects.size(); i++) {
			if (connects.get(i).getX() == this.getX() && connects.get(i).getY() + 114 == this.getY()) {
				return connects.get(i);
			}
		}
		
		return null;
	}
	
	public Connect getDownConnectByPosition () {
		ArrayList <Connect> connects = ConnectMap.allConnects;
		
		for (int i = 0; i < connects.size(); i++) {
			if (connects.get(i).getX() == this.getX() && connects.get(i).getY() - 114 == this.getY()) {
				return connects.get(i);
			}
		}
		
		return null;
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


