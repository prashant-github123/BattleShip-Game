/**
 * 
 */
package com.battleship.application.dto;


/**
 * @author amall3
 *
 */
public class HitOpponentShipUpdateDTO {

	private String playerId;

	private String gameId;

	private boolean hitOrMiss;

	private boolean isGameOver;

	private String hitCoordinate;

	/**
	 * @return the playerId
	 */
	public String getPlayerId() {
		return playerId;
	}

	/**
	 * @param playerId
	 *            the playerId to set
	 */
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	/**
	 * @return the gameId
	 */
	public String getGameId() {
		return gameId;
	}

	/**
	 * @param gameId
	 *            the gameId to set
	 */
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	/**
	 * @return the hitOrMiss
	 */
	public boolean isHitOrMiss() {
		return hitOrMiss;
	}

	/**
	 * @param hitOrMiss
	 *            the hitOrMiss to set
	 */
	public void setHitOrMiss(boolean hitOrMiss) {
		this.hitOrMiss = hitOrMiss;
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
	 * @return the hitCoordinate
	 */
	public String getHitCoordinate() {
		return hitCoordinate;
	}

	/**
	 * @param hitCoordinate
	 *            the hitCoordinate to set
	 */
	public void setHitCoordinate(String hitCoordinate) {
		this.hitCoordinate = hitCoordinate;
	}

}
