/**
 * 
 */
package com.battleship.interfaces.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author amall3
 *
 */
public class HitOpponentShipUpdateRequestDTO {

	@NotBlank(message = "Player Id is mandatory.")
	private String playerId;

	@NotBlank(message = "Game Id is mandatory.")
	private String gameId;

	private String hitOrMiss;

	private boolean gameOver;

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

	/**
	 * @return the hitOrMiss
	 */
	public String getHitOrMiss() {
		return hitOrMiss;
	}

	/**
	 * @param hitOrMiss
	 *            the hitOrMiss to set
	 */
	public void setHitOrMiss(String hitOrMiss) {
		this.hitOrMiss = hitOrMiss;
	}

}
