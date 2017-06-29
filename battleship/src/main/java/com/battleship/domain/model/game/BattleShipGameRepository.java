package com.battleship.domain.model.game;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.battleship.domain.model.handling.NoGameAvailableException;
import com.battleship.domain.model.player.Player;

/**
 * This repository manages <code>Game</code> instances.
 * This repository can be replaced with enterprise level solution with Database or Caching.
 * 
 * 
 * @author pmalsh
 *
 */
@Component
public class BattleShipGameRepository{

	/**
	 * This is LinkedList repository for easy retrieval latest available game and other functions.
	 * 
	 */
	private LinkedList<Game> allGames;
	
	
	//Changing this constructor from private to public as Mockito is unable to call this default constructor while creating mock object.
	
	private BattleShipGameRepository(){
		allGames = new LinkedList<>();	
	}
	
	private BattleShipGameRepository(int i){
		allGames = new LinkedList<>();	
	}
	
	/**
	 * This method retrieves latest Game ID if available for play.
	 * If there is no game available the it creates new game and provides new Game ID.
	 * 
	 * @return Game ID 
	 */
	
	public int latestAvailableGame(){
		
		if(allGames.isEmpty() || allGames.getFirst().getGamePlayers().size() == 2){
			allGames.push(new Game());
			return allGames.getFirst().getGameID();
		}
		else{	
			return allGames.getFirst().getGameID();
		}
	}
	
	/**
	 * This method returns Game for provided Game ID.
	 * If Game is not available, NULL will be returned.
	 * 
	 * @return <code>Game<code>
	 */
	public Game getGameByID(int gameID) throws NoGameAvailableException {
		
		for (Game game : allGames) {
			if(game.getGameID() == gameID){
				return game;
			}
		}
		throw new NoGameAvailableException();
	}
	
	/**
	 * This method adds player to the game if that game has less than 2 players.
	 *  
	 * @param player
	 * @param gameID
	 * @return true if player added successfully, false if fails to add player into game
	 * @throws NoGameAvailableException 
	 */
	public boolean addNewPlayerByGameID(Player player, int gameID) throws NoGameAvailableException{
		
		List<Player> playersList = this.getGameByID(gameID).getGamePlayers();
		
		if(playersList.size() < 2){
			playersList.add(player);
			return true;
		}
		return false;
	}
	
}
