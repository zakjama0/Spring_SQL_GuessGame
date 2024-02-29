package com.bnta.word_guesser.repositories;

import com.bnta.word_guesser.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
