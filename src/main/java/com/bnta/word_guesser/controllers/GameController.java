package com.bnta.word_guesser.controllers;

import com.bnta.word_guesser.models.Game;
import com.bnta.word_guesser.models.Guess;
import com.bnta.word_guesser.models.Reply;
import com.bnta.word_guesser.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/games")
public class GameController {

    @Autowired
    GameService gameService;

    @PostMapping
    public ResponseEntity<Reply> newGame(@RequestParam long playerId){
        Reply reply = gameService.startNewGame(playerId);
        return new ResponseEntity<>(reply, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Reply> getGameStatus(@PathVariable long id){

        Optional<Game> game  = gameService.getGameById(id);

        if (game.isEmpty()){
            Reply reply = new Reply(
                    null,
                    "Game not found",
                    false
            );
            return new ResponseEntity<>(reply, HttpStatus.NOT_FOUND);
        } else {
            Reply reply = new Reply(
                    game.get().getCurrentState(),
                    "Game in progress",
                    false
            );
            return new ResponseEntity<>(reply, HttpStatus.OK);
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Reply> handleGuess(@RequestBody Guess guess,
                                             @PathVariable long id){
        Reply reply = gameService.processGuess(guess, id);
        return new ResponseEntity<>(reply, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<Game>> getAllGames(@RequestParam Optional<Boolean> isCompelete, @RequestParam Optional<String> playerName){
        List<Game> games;

        if (isCompelete.isPresent()){
            games = gameService.getCompletedGames(isCompelete.get());
        } else if (playerName.isPresent()){
            games = gameService.getGamesForPlayer(playerName.get());
        }
        else{
            games = gameService.getAllGames();

        }
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

}
