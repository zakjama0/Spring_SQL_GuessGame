package com.bnta.word_guesser.services;


import com.bnta.word_guesser.models.Player;
import com.bnta.word_guesser.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {


    @Autowired
    PlayerRepository playerRepository;



    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }


    public Optional<Player> getPlayerByID (long id){
        return playerRepository.findById(id);
    }


    public Player savePlayer(Player player){
        playerRepository.save(player);
        return player;
    }
}
