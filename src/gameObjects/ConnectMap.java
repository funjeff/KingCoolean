package gameObjects;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import engine.GameObject;

public class ConnectMap extends GameObject {
	
	Connect hovering = null;
	
	public static ArrayList <Connect> allConnects = new ArrayList <Connect>();
	
	
	public ConnectMap (Connect startHover) {
		hovering = startHover;
		hovering.onHover();
		if (!allConnects.contains(startHover)) {
			allConnects.add(startHover);
		}
	}
	
	@Override
	public void draw () {
		for (int i = 0; i < allConnects.size(); i++) {
			allConnects.get(i).draw();
		}
		hovering.getResume().draw();
	}
	
	@Override
	public void frameEvent () {
		if (keyPressed ('A')) {
			moveLeft();
		}
		if (keyPressed ('D')) {
			moveRight();
		}
		if (keyPressed ('S')) {
			moveDown();
		}
		if (keyPressed ('W')) {
			moveUp();
		}
		
		if (keyPressed (KeyEvent.VK_ENTER)) {
			hovering.onSelect();
		}
		
	}
	
	private void moveLeft () {
		if (hovering.getLeft() != null && hovering.isAsseable()) {
			hovering.getLeft().onHover();
			hovering.onNotHover();
			hovering = hovering.getLeft();
		}
	}
	
	private void moveRight () {
		if (hovering.getRight() != null && hovering.isAsseable()) {
			hovering.getRight().onHover();
			hovering.onNotHover();
			hovering = hovering.getRight();
		}
	}
	
	private void moveDown () {
		if (hovering.getBelow() != null && hovering.isAsseable()) {
			hovering.getBelow().onHover();
			hovering.onNotHover();
			hovering = hovering.getBelow();
		}
	}
	
	private void moveUp () {
		if (hovering.getAbove() != null && hovering.isAsseable()) {
			hovering.getAbove().onHover();
			hovering.onNotHover();
			hovering = hovering.getAbove();
		}
	}
	
	public void addConnect (Connect c) {
		
	}
	
}
