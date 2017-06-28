package com.battleship.domain.model.ship;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	// This needs to be replaced with JPA/Database.
	private static int autoIncrementShipID = 1;
		
	private int shipID;
	
	private List<String> playerShipCoordinates;
	
	public Ship(){
		this.setShipID(autoIncrementShipID++);
		this.setPlayerShipCoordinates(new ArrayList<String>());
	}

	public int getShipID() {
		return shipID;
	}

	public void setShipID(int shipID) {
		this.shipID = shipID;
	}

	public List<String> getPlayerShipCoordinates() {
		return playerShipCoordinates;
	}

	public void setPlayerShipCoordinates(List<String> playerShipCoordinates) {
		this.playerShipCoordinates = playerShipCoordinates;
	}
		
}
