package com.battleship.application.impl;

import static com.battleship.application.util.ApplicationConstants.GAME_OVER_STATUS;
import static com.battleship.application.util.ApplicationConstants.TURN_STATUS_FALSE;
import static com.battleship.application.util.ApplicationConstants.TURN_STATUS_TRUE;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.battleship.application.PlayGameService;
import com.battleship.application.dto.HitOpponentShipUpdateDTO;
import com.battleship.domain.model.game.BattleShipGameRepository;
import com.battleship.domain.model.game.Game;
import com.battleship.domain.model.handling.NoGameAvailableException;
import com.battleship.domain.model.player.Player;
import com.battleship.interfaces.dto.HitOpponentShipUpdateRequestDTO;
import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * This class is an implementation of <code>PlayGameService</code> and has
 * method that checks the game and player's turn status.
 * 
 * @author amall3
 *
 */
@Service
@ComponentScan("com.battleship.repository")
public class PlayGameServiceImpl implements PlayGameService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BattleShipGameRepository gameRepository;

	
	/**
	 * This implements check turn status service. 
	 *  
	 * 
	 * @param checkTurnStatusRequest
	 * @param response
	 * @return GAME_OVER_STATUS if Game is Over else TURN_STATUS_FALSE (false) or TURN_STATUS_TRUE (true)
	 * @throws NoGameAvailableException 
	 * @throws NumberFormatException 
	 */
	public String checkPlayersTurnOrGameOverStatus(int gameId, int playerId) throws NoGameAvailableException {

		logger.info("Inside PlayGameServiceImpl.checkPlayersTurnOrGameOverStatus()");
		Game game = gameRepository.getGameByID(gameId);
		boolean isGameOver = Boolean.FALSE;
		
		for (Player player : game.getGamePlayers()) {
			if (player.getPlayerID() != playerId) {
				if(player.getShip().isDestroyed()){
					isGameOver = true;
				}
			}
		}
		
		if (isGameOver) {
			return GAME_OVER_STATUS;
		} else if(game.getWhoseTurnPlayerId() == playerId) {
			return TURN_STATUS_TRUE;	
		}		
		return TURN_STATUS_FALSE;
	}



	/**
	 * This implements hit ship event update service. 
	 * 
	 * @param <code>HitOpponentShipUpdateDTO</code> hit ship event update details.
	 * @param response
	 * @return List of hit coordinates   
	 * @throws NoGameAvailableException 
	 * @throws NumberFormatException 
	 */

	public List<String> hitOpponentShipUpdateEvent(HitOpponentShipUpdateDTO hitOpponentShipUpdateDTO) throws NoGameAvailableException{

		logger.info("Inside PlayGameServiceImpl.processGame()");
		List<String> coordinateList = null;

		Game game = gameRepository.getGameByID(Integer.valueOf(hitOpponentShipUpdateDTO.getGameId()));
		for (Player player : game.getGamePlayers()) {

			if (Integer.parseInt(hitOpponentShipUpdateDTO.getPlayerId()) != player.getPlayerID()) {
				game.setWhoseTurnPlayerId(player.getPlayerID());

				coordinateList = player.getShip().getHitOrMissCoordinates();
				if (null == coordinateList) {
					coordinateList = new ArrayList<>();
				}
				coordinateList.add(hitOpponentShipUpdateDTO.getHitCoordinate());
				player.getShip().setHitOrMissCoordinates(coordinateList);
			}
		}
		
		if (hitOpponentShipUpdateDTO.isGameOver()) {
			for (Player player : game.getGamePlayers()) {
				if (player.getPlayerID() != Integer.parseInt(hitOpponentShipUpdateDTO.getPlayerId())) {
					player.getShip().setDestroyed(Boolean.TRUE);
					return coordinateList;
				}
			}		 
		} 
		return coordinateList;
	}
}
