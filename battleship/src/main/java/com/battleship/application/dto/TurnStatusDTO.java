/**
 * 
 */
package com.battleship.application.dto;

import java.util.List;

/**
 * @author amall3
 *
 */
public class TurnStatusDTO {

	private List<String> coordinates;

	private boolean isGameOver;

	private boolean turnStatus;

	/**
	 * @return the coordinates
	 */
	public List<String> getCoordinates() {
		return coordinates;
	}

	/**
	 * @param coordinates
	 *            the coordinates to set
	 */
	public void setCoordinates(List<String> coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * @return the isGameOver
	 */
	public boolean isGameOver() {
		return isGameOver;
	}

	/**
	 * @param isGameOver
	 *            the isGameOver to set
	 */
	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	/**
	 * @return the turnStatus
	 */
	public boolean isTurnStatus() {
		return turnStatus;
	}

	/**
	 * @param turnStatus
	 *            the turnStatus to set
	 */
	public void setTurnStatus(boolean turnStatus) {
		this.turnStatus = turnStatus;
	}

}
