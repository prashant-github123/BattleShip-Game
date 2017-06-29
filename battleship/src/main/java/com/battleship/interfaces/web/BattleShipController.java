/**
 * 
 */
package com.battleship.interfaces.web;

import static com.battleship.application.util.ApplicationConstants.GAME_OVER_STATUS;
import static com.battleship.application.util.ApplicationConstants.TURN_STATUS_FALSE;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.battleship.application.PlayGameService;
import com.battleship.application.PrepareGroundService;
import com.battleship.application.RegistrationService;
import com.battleship.application.dto.HitOpponentShipUpdateDTO;
import com.battleship.domain.model.handling.GameInitiationException;
import com.battleship.domain.model.handling.InvalidPlayerException;
import com.battleship.domain.model.handling.NoGameAvailableException;
import com.battleship.domain.model.handling.NoPlayerFoundException;
import com.battleship.domain.model.player.Player;
import com.battleship.interfaces.dto.CheckTurnStatusRequestDTO;
import com.battleship.interfaces.dto.CheckTurnStatusResponseDTO;
import com.battleship.interfaces.dto.HitOpponentShipUpdateRequestDTO;
import com.battleship.interfaces.dto.HitOpponentShipUpdateResponseDTO;
import com.battleship.interfaces.dto.NewPlayerRequestDTO;
import com.battleship.interfaces.dto.PlaceShipRequestDTO;
import com.battleship.interfaces.dto.RetrieveShipLocationRequestDTO;
import com.battleship.interfaces.util.InterfacesUtil;

/**
 * This is main controller for all the APIs used in Battleship Game.
 * There are 3 functional contexts as:
 * Registration, Prepare Ground and Play Game
 *  
 *  
 * @author amall3
 *
 */
@RestController
@RequestMapping("/rest/api")
@CrossOrigin
public class BattleShipController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private PrepareGroundService prepareGroundService;
	
	@Autowired
	private PlayGameService playGameService;	
	
	@Autowired
	private InterfacesUtil util;

	/**
	 * This REST API will accept Player name to register in BattleShip Game.
	 * Registered player details will be returned.
	 * 
	 * 
	 * @param <code>NewPlayerRequest</code>
	 * @param <code>Errors</code>
	 * @return <code>Player</code> upon successfully else
	 *         <code>BadRequest</code> response will be returned
	 * 
	 * @throws GameInitiationException
	 * @throws InvalidPlayerException
	 * @throws NoGameAvailableException
	 */
	@RequestMapping(value = "/addPlayer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<?> registerPlayer(@Valid @RequestBody NewPlayerRequestDTO newPlayerRequest, Errors errors) {

		logger.debug("[BattleShipRegistrationController.registerPlayer()] : registerPlayer Service called");

		if (errors.hasErrors()) {
			logger.debug("[BattleShipRegistrationController.registerPlayer()] : There are validation errors > " + errors);
			return ResponseEntity.badRequest().body(util.getValidationErrors(errors));
		}

		Player newPlayer;
		try {
			
			newPlayer = registrationService.registerNewPlayer(newPlayerRequest.getPlayerName());
			return ResponseEntity.ok(newPlayer);
			
		} catch (GameInitiationException |InvalidPlayerException |NoGameAvailableException  e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	/**
	 * Once Player places his ship and prepare his battleground, this service can be called to record ship coordinates. 
	 * 
	 * 
	 * @param placeShipRequest
	 * @param errors
	 * @return <code>Player</code> object if successful.
	 *  
	 */
	@RequestMapping(value = "/placeShip", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<?> placeShip(@Valid @RequestBody PlaceShipRequestDTO placeShipRequest, Errors errors) {

		logger.debug("[BattleShipController.placeShip()] : Place Ship for a Player Service called");

		if (errors.hasErrors()) {
			logger.debug("[BattleShipController.placeShip()] : There are validation errors > " + errors);
			return ResponseEntity.badRequest().body(util.getValidationErrors(errors));
		}
	
		logger.debug("[BattleShipController.placeShip()] : The Game Id Passed Is {0}:", placeShipRequest.getGameId());
		logger.debug("[BattleShipController.placeShip()] : The Player Id Passed Is {0}:", placeShipRequest.getPlayerId());
		logger.debug("[BattleShipController.placeShip()] : The Coordinates Passed Is {0}:", placeShipRequest.getShipCoordinates());
		
		try {
			Player updatedPlayer = prepareGroundService.setShipCoodinatesForPlayer(placeShipRequest.getGameId(), placeShipRequest.getPlayerId(), placeShipRequest.getShipCoordinates());
			if (null != updatedPlayer) {
				return ResponseEntity.ok(updatedPlayer);
			} else {
				throw new NoGameAvailableException();
			}
		} catch (NoGameAvailableException | NoPlayerFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	/**
	 * Player's ship locations can be retrieved using this service for the requested Game.
	 * 	 * 
	 * @param gameId
	 * @return List of <code>Player</code>(s) with corresponding <code>Ship</code> details
	 * 
	 * 
	 */
	@RequestMapping(value = "/retrieveShipLocations", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<?> retrieveShipLocations(@Valid @RequestBody RetrieveShipLocationRequestDTO retrieveShipLocationRequest, Errors errors) {

		logger.debug("[BattleShipController.retrieveShipLocations()] : Retrieve the Player's ship coordinates .");
		
		if (errors.hasErrors()) {
			logger.debug("[BattleShipController.retrieveShipLocations()] : There are validation errors > " + errors);
			return ResponseEntity.badRequest().body(util.getValidationErrors(errors));
		}
		
		logger.debug("The Game Id Passed Is {0}:", retrieveShipLocationRequest.getGameId());
		
		try {
			List<Player> players = prepareGroundService.getPlayerDetails(retrieveShipLocationRequest.getGameId());
			if (null != players && !players.isEmpty()){
				return ResponseEntity.ok(players);
			}
		} catch (NoGameAvailableException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	/**
	 * Method responsible for returning the turn status for the passed in game
	 * Id.
	 * 
	 * @param gameId
	 * @return
	 */
	@RequestMapping(value = "/checkTurnStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<?> checkTurnStatus(@Valid @RequestBody CheckTurnStatusRequestDTO checkTurnStatusRequest, Errors errors) {

		logger.debug("[BattleShipController.checkTurnStatus()] : The Game Id Passed Is {0}:", checkTurnStatusRequest.getGameId());
		logger.debug("[BattleShipController.checkTurnStatus()] : The Player Id Passed Is {0}:", checkTurnStatusRequest.getPlayerId());

		if (errors.hasErrors()) {
			logger.debug("[BattleShipController.retrieveShipLocations()] : There are validation errors > " + errors);
			return ResponseEntity.badRequest().body(util.getValidationErrors(errors));
		}

		
		CheckTurnStatusResponseDTO checkTurnStatusResponseDTO = new CheckTurnStatusResponseDTO();
		
		try {
			String turnStatus = playGameService.checkPlayersTurnOrGameOverStatus(
					Integer.parseInt(checkTurnStatusRequest.getGameId()), Integer.parseInt(checkTurnStatusRequest.getPlayerId()));
			
			if (turnStatus.equals(GAME_OVER_STATUS)) {
				checkTurnStatusResponseDTO.setTurn(Boolean.FALSE);
				checkTurnStatusResponseDTO.setGameOver(Boolean.TRUE);
				
			} else if (turnStatus.equals(TURN_STATUS_FALSE)) {
				checkTurnStatusResponseDTO.setTurn(Boolean.FALSE);
				checkTurnStatusResponseDTO.setGameOver(Boolean.FALSE);
				
			} else {
				checkTurnStatusResponseDTO.setTurn(Boolean.TRUE);
				checkTurnStatusResponseDTO.setGameOver(Boolean.FALSE);
			}
			logger.debug("[BattleShipController.checkTurnStatus()] : Response is :", checkTurnStatusResponseDTO);
			
		} catch (NumberFormatException | NoGameAvailableException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok(checkTurnStatusResponseDTO);
	}
	
	
	/**
	 * Method responsible for returning the turn status for the passed in game
	 * Id.
	 * 
	 * @param gameId
	 * @return
	 */
	@RequestMapping(value = "/hitOpponentShip", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<?> hitOpponentShipEventUpdate(@Valid @RequestBody HitOpponentShipUpdateRequestDTO hitOpponentShipUpdateRequest, Errors errors) {

		logger.debug("[BattleShipController.hitOpponentShipUpdate()] : The Game Id Passed Is {0}:", hitOpponentShipUpdateRequest.getGameId());
		logger.debug("[BattleShipController.hitOpponentShipUpdate()] : The Player Id Passed Is {0}:", hitOpponentShipUpdateRequest.getPlayerId());
		
		HitOpponentShipUpdateResponseDTO hitOpponentShipUpdateResponseDTO = new HitOpponentShipUpdateResponseDTO();
		
		if (errors.hasErrors()) {
			logger.debug("[BattleShipController.retrieveShipLocations()] : There are validation errors > " + errors);
			return ResponseEntity.badRequest().body(util.getValidationErrors(errors));
		}
		
		try {
			HitOpponentShipUpdateDTO hitOpponentShipUpdateDTO = new HitOpponentShipUpdateDTO();
			BeanUtils.copyProperties(hitOpponentShipUpdateDTO, hitOpponentShipUpdateRequest);
			List<String> coordinateList = playGameService.hitOpponentShipUpdateEvent(hitOpponentShipUpdateDTO);
			if (null != coordinateList){
				hitOpponentShipUpdateResponseDTO.setOpponentHitCoordinates(coordinateList);
				return ResponseEntity.ok(hitOpponentShipUpdateResponseDTO);
			}
		} catch (NumberFormatException | NoGameAvailableException | IllegalAccessException | InvocationTargetException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return  ResponseEntity.badRequest().body(hitOpponentShipUpdateResponseDTO);
	}
	
}
