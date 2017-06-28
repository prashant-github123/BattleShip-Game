package com.battleship;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.battleship.domain.model.game.BattleShipGameRepository;
import com.battleship.domain.model.game.Game;
import com.battleship.domain.model.handling.NoGameAvailableException;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BattleshipApplication.class})
public class GameRepositoryUnitTests {
		
	@InjectMocks
	private BattleShipGameRepository gameRepository;
	
	@Before
	public void setUp() throws Exception {
		
		
	}

	@Test
	public void testFirstGameCreation() {
		
		int latestGameID = gameRepository.latestAvailableGame();
	
		Game newGame = null;
		try {
			newGame = gameRepository.getGameByID(latestGameID);
		} catch (NoGameAvailableException e) {
			e.printStackTrace();
		}
		
		if(newGame != null){
			assertTrue(newGame.getGamePlayers().isEmpty());
		}
		else
			assertFalse(true);
		
	}

	
	@Test
	public void testSubsequentGameCreation() {
		
		int latestGameID = gameRepository.latestAvailableGame();
	
		Game newGame = null;
		try {
			newGame = gameRepository.getGameByID(latestGameID);
		} catch (NoGameAvailableException e) {
			e.printStackTrace();
		}
		
		if(newGame != null){
			assertTrue(newGame.getGamePlayers().isEmpty());
		}
		else
			assertFalse(true);
	}
	
	
	
	
	
}
