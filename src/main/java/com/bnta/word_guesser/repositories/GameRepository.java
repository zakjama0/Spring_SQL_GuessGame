package com.bnta.word_guesser.repositories;

import com.bnta.word_guesser.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {


    List<Game> findByComplete(boolean complete);
    // find = select complete = column value = true
    // SELECT * FROM games WHERE column = true
    List<Game> findByPlayerName(String playerName);


}
