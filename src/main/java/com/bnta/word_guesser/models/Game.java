package com.bnta.word_guesser.models;

import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "word")
    private String word;

    @Column(name = "guesses")
    private int guesses;

    @Column(name = "complete")
    private boolean complete;

    @Column(name = "current_state")
    private String currentState;

    @ManyToOne
    @JoinColumn (name = "player_id") // foreign key
    private Player player;

    public Game(String word, String currentState, Player player) {
        this.word = word;
        this.currentState = currentState;
        this.guesses = 0;
        this.complete = false;
        this.player = player;
    }

    public Game() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getGuesses() {
        return guesses;
    }

    public void setGuesses(int guesses) {
        this.guesses = guesses;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
