package com.bnta.word_guesser.components;

import com.bnta.word_guesser.models.Player;
import com.bnta.word_guesser.services.GameService;
import com.bnta.word_guesser.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    GameService gameService;

    @Autowired
    PlayerService playerService;





    @Override
    public void run(ApplicationArguments args) throws Exception {


        Player player1 = new Player("Colin");
        Player player2 = new Player ("Thibyaa");
        Player player3 = new Player("Zakaria");


        playerService.savePlayer(player1);
        playerService.savePlayer(player2);
        playerService.savePlayer(player3);




        gameService.startNewGame(player1.getId());
        gameService.startNewGame(player2.getId());
        gameService.startNewGame(player3.getId());
//        gameService.startNewGame();
//        gameService.startNewGame();
    }
}
