package com.battleship.domain.model.ship;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	// This needs to be replaced with JPA/Database.
	private static int autoIncrementShipID = 1;
		
	private int shipID;
	
	private List<String> positionCoordinates;

	private boolean isDestroyed;
	
	private List<String> hitOrMissCoordinates;
	
	
	public Ship(){
		this.setShipID(autoIncrementShipID++);
		this.setPositionCoordinates(new ArrayList<String>());
		this.setDestroyed(false);
		this.setHitOrMissCoordinates(new ArrayList<String>());
	}

	public int getShipID() {
		return shipID;
	}

	public void setShipID(int shipID) {
		this.shipID = shipID;
	}

	public List<String> getPlayerShipCoordinates() {
		return positionCoordinates;
	}

	public void setPositionCoordinates(List<String> positionCoordinates) {
		this.positionCoordinates = positionCoordinates;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}
	
	public List<String> getHitOrMissCoordinates() {
		return hitOrMissCoordinates;
	}

	public void setHitOrMissCoordinates(List<String> hitOrMissCoordinates) {
		this.hitOrMissCoordinates = hitOrMissCoordinates;
	}
		
}
