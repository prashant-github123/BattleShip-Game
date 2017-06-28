/**
 * 
 */
package com.battleship.interfaces.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.battleship.application.RegistrationService;
import com.battleship.domain.model.handling.GameInitiationException;
import com.battleship.domain.model.handling.InvalidPlayerException;
import com.battleship.domain.model.handling.NoGameAvailableException;
import com.battleship.domain.model.player.Player;
import com.battleship.interfaces.dto.NewPlayerRequestDTO;
import com.battleship.interfaces.util.GenericUtil;

/**
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
	private GenericUtil util;

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
	 * @ <code>GameInitiationException</code>,
	 *            <code>InvalidPlayerException</code>,
	 *            <code>NoGameAvailableException</code>
	 */
	@RequestMapping(value = "/addPlayer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
			
		} catch (GameInitiationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (InvalidPlayerException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (NoGameAvailableException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
}
