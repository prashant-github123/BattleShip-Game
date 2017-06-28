package com.battleship.application.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.battleship.application.RegistrationService;
import com.battleship.domain.model.game.BattleShipGameRepository;
import com.battleship.domain.model.game.Game;
import com.battleship.domain.model.handling.GameInitiationException;
import com.battleship.domain.model.handling.InvalidPlayerException;
import com.battleship.domain.model.handling.NoGameAvailableException;
import com.battleship.domain.model.player.Player;


/**
 * This class implements <code>RegistrationService</code>.
 * 
 * @author amall3
 *
 */

@Service
@ComponentScan("com.battleship.repository")
public class RegistrationServiceImpl implements RegistrationService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BattleShipGameRepository gameRepository;
	
	@Override
	public Game retrieveLatestAvailableGame() throws GameInitiationException, NoGameAvailableException {
		
		int lstestAvailableGameID = gameRepository.latestAvailableGame();
		
		logger.info("[RegistrationServiceImpl.retrieveLatestAvailableGame] : Latest Available Game ID > "+ lstestAvailableGameID);
		
		return gameRepository.getGameByID(lstestAvailableGameID);
	}
	
	
	
	@Override
	public Player registerNewPlayer(String lPlayerName) throws InvalidPlayerException, GameInitiationException, NoGameAvailableException {
		
		logger.debug("[RegistrationServiceImpl.registerNewPlayer] : New Player registration with name > "+lPlayerName);
		
		boolean playerAddedSuccessfully = false;
		Player newPlayer;
		
		synchronized (this) {
			Game latestAvailableGame = this.retrieveLatestAvailableGame();
			
			newPlayer = new Player(lPlayerName);
			newPlayer.setGameID(latestAvailableGame.getGameID());
			
			playerAddedSuccessfully = gameRepository.addNewPlayerByGameID(newPlayer, latestAvailableGame.getGameID());
		}
		
		if(playerAddedSuccessfully){
			logger.debug("[RegistrationServiceImpl.registerNewPlayer] : New Player registration done !! with name > "+lPlayerName);
			return newPlayer;
		}
		
		throw new NoGameAvailableException();
	}


	

}
