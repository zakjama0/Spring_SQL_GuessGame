package com.bnta.word_guesser.controllers;


import com.bnta.word_guesser.models.Player;
import com.bnta.word_guesser.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/players")
public class PlayerController {


    @Autowired
    PlayerService playerService;


    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers(){
        List<Player> players = playerService.getAllPlayers();
        return new ResponseEntity<>(players, HttpStatus.OK);
    }


    // @PathVariable means its getting from the url

    @GetMapping(value = "/{id}")
    public ResponseEntity<Player> getPlayerById (@PathVariable long id){
        Optional<Player> player = playerService.getPlayerByID(id);
        if (player.isPresent()){
                return new ResponseEntity<>(player.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Player> addNewPlayer (@RequestBody Player player){
        Player savedPlayer = playerService.savePlayer(player);
        return new ResponseEntity<>(savedPlayer, HttpStatus.CREATED);
    }



}
