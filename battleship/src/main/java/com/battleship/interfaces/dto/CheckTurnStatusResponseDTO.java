/**
 * 
 */
package com.battleship.interfaces.dto;


/**
 * @author amall3
 *
 */
public class CheckTurnStatusResponseDTO extends ResponseDTO {

	private boolean turn;

	private boolean gameOver;

	/**
	 * @return the turn
	 */
	public boolean isTurn() {
		return turn;
	}

	/**
	 * @param turn
	 *            the turn to set
	 */
	public void setTurn(boolean turn) {
		this.turn = turn;
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

}
