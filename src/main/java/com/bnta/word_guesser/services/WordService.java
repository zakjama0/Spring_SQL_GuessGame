package com.bnta.word_guesser.services;

import com.bnta.word_guesser.repositories.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordService {

    @Autowired
    WordRepository wordRepository;

    public String getRandomWord(){
        return wordRepository.getRandomWord();
    }

}
