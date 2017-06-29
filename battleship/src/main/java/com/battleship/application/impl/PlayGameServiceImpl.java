package com.battleship.application.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.battleship.application.PlayGameService;
import com.battleship.application.dto.HitOpponentShipUpdateDTO;
import com.battleship.application.dto.TurnStatusDTO;
import com.battleship.domain.model.game.Game;
import com.battleship.domain.model.handling.NoGameAvailableException;
import com.battleship.domain.model.player.Player;
import com.battleship.infrastructure.BattleShipGameRepository;

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
	public TurnStatusDTO checkPlayersTurnOrGameOverStatus(int gameId, int playerId) throws NoGameAvailableException {

		logger.info("Inside PlayGameServiceImpl.checkPlayersTurnOrGameOverStatus()");
		TurnStatusDTO turnStatusDTO = new TurnStatusDTO();
		Game game = gameRepository.getGameByID(gameId);
		boolean isGameOver = Boolean.FALSE;
		
		for (Player player : game.getGamePlayers()) {
			if (player.getPlayerID() != playerId) {
				turnStatusDTO.setCoordinates(player.getShip().getHitOrMissCoordinates());
			}
			
			if(player.getShip().isDestroyed()){
				isGameOver = true;
			}
		}
		
		if (isGameOver) {
			turnStatusDTO.setGameOver(isGameOver);
			turnStatusDTO.setTurnStatus(Boolean.FALSE);
			return turnStatusDTO;
			
		} else if(game.getWhoseTurnPlayerId() == playerId) {
			turnStatusDTO.setGameOver(isGameOver);
			turnStatusDTO.setTurnStatus(Boolean.TRUE);
			return turnStatusDTO;
		}		
		turnStatusDTO.setGameOver(isGameOver);
		turnStatusDTO.setTurnStatus(Boolean.FALSE);
		return turnStatusDTO;
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
