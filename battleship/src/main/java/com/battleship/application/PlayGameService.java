/**
 * 
 */
package com.battleship.application;

import com.battleship.domain.model.handling.NoGameAvailableException;


/**
 * 
 * @author amall3
 *
 */
public interface PlayGameService {
	
	/**
	 * System maintains the turn status of individual players for respective game.
	 * This service is to check turn status with system. UI should enable play based on this status.
	 * If Game is over due to opponent's hit then it will return Game Over status as well. 
	 *  
	 * 
	 * @param checkTurnStatusRequest
	 * @param response
	 * @return GAME_OVER_STATUS if Game is "over" else TURN_STATUS_FALSE "false" or TURN_STATUS_TRUE "true"
	 * @throws NoGameAvailableException 
	 * @throws NumberFormatException 
	 */
	String checkPlayersTurnOrGameOverStatus(int gameId, int playerId) throws NoGameAvailableException;
	
	
	/**
	 * 
	 * @param hitOpponentShipUpdateRequest
	 * @return
	 * @throws NoGameAvailableException
	 */

	//HitOpponentShipUpdateResponse hitOpponentShipUpdateEvent(HitOpponentShipUpdateRequest hitOpponentShipUpdateRequest) throws NoGameAvailableException;
}