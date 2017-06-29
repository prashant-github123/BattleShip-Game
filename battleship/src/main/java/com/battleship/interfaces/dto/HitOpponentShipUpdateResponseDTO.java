/**
 * 
 */
package com.battleship.interfaces.dto;

import java.util.List;

/**
 * @author amall3
 *
 */
public class HitOpponentShipUpdateResponseDTO extends ResponseDTO {

	private List<String> opponentHitCoordinates;

	private boolean gameOver;

	/**
	 * @return the gameOver
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * @param gameOver
	 *            the gameOver to set
	 */
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	/**
	 * @return the opponentHitCoordinates
	 */
	public List<String> getOpponentHitCoordinates() {
		return opponentHitCoordinates;
	}

	/**
	 * @param opponentHitCoordinates
	 *            the opponentHitCoordinates to set
	 */
	public void setOpponentHitCoordinates(List<String> opponentHitCoordinates) {
		this.opponentHitCoordinates = opponentHitCoordinates;
	}

}
