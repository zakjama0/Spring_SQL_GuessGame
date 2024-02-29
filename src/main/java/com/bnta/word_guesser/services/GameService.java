package com.bnta.word_guesser.services;

import com.bnta.word_guesser.models.Game;
import com.bnta.word_guesser.models.Guess;
import com.bnta.word_guesser.models.Player;
import com.bnta.word_guesser.models.Reply;
import com.bnta.word_guesser.repositories.GameRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    @Autowired
    WordService wordService;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlayerService playerService;

    public List<Game> getAllGames(){
        return gameRepository.findAll();
    }

    public List <Game> getCompletedGames(boolean complete){
//        List<Game> allGames = gameRepository.findAll();
//        List<Game> completedGames = new ArrayList<>();
//        for(Game game : allGames){
//            if (game.isComplete()){
//                completedGames.add(game);
//            }
//        }
//        return completedGames;
        return gameRepository.findByComplete(complete);
    }

    public List<Game> getGamesForPlayer(String playerName){
        return gameRepository.findByPlayerName(playerName);

    }


    public Reply startNewGame(long playerId){
        String targetWord = wordService.getRandomWord();
        String currentWordStatus = Strings.repeat("*", targetWord.length());
        Player player = playerService.getPlayerByID(playerId).get();
        Game game = new Game(targetWord, currentWordStatus, player);
        gameRepository.save(game);
        return new Reply(
                game.getCurrentState(),
                "Started new game",
                false
        );
    }

    public Reply processGuess(Guess guess, long id){
        // find the game we want to play
        Optional<Game> gameOptional = getGameById(id);

        // Check if game exists
        if (gameOptional.isEmpty()) {
            return new Reply(
                    null,
                    "Game not found",
                    false
            );
        }

        // extract game details from Optional
        Game game = gameOptional.get();

        // check if game already complete
        if(game.isComplete()){
            return new Reply(
                    game.getCurrentState(),
                    "Game already complete",
                    false
            );
        }

        // record guess
        incrementGuesses(game);

        // check if letter has already been guessed
        if (game.getCurrentState().contains(guess.getLetter())) {
            gameRepository.save(game);
            return new Reply(
                    game.getCurrentState(),
                    String.format("Already found %s", guess.getLetter()),
                    false
            );
        }

        // check for incorrect guess
        if (!game.getWord().contains(guess.getLetter())) {
            gameRepository.save(game);
            return new Reply(
                    game.getCurrentState(),
                    String.format("%s is not in the word", guess.getLetter()),
                    false
            );
        }


        // process correct guess
        String runningResult = game.getWord();

        // build list of previously guessed letters
        List<String> guessedLetters = new ArrayList<>(Arrays.asList(
                game.getCurrentState().split(""))
        );

        // remove * characters
        guessedLetters.removeAll(Collections.singleton("\\*"));

        // add current guess
        guessedLetters.add(guess.getLetter());

        // update current state of game
        for (Character letter : game.getWord().toCharArray()) {
            if (!guessedLetters.contains(letter.toString())) {
                runningResult = runningResult.replace(letter, '*');
            }
        }

        game.setCurrentState(runningResult);

        // check if game won
        if (checkWinCondition(game)){
            game.setComplete(true);
            gameRepository.save(game);
            return new Reply(
                    game.getCurrentState(),
                    "Congratulations, you win!",
                    true
            );
        }

        gameRepository.save(game);

        return new Reply(
                game.getCurrentState(),
                String.format("%s is in the word", guess.getLetter()),
                true
        );
    }

    private boolean checkWinCondition(Game game){
        String gameWord = game.getWord();
        String gameState = game.getCurrentState();
        return gameWord.equals(gameState);
    }

    private void incrementGuesses(Game game){
        game.setGuesses(game.getGuesses() + 1);
    }

    public Optional<Game> getGameById(long id){
        return gameRepository.findById(id);
    }

}
