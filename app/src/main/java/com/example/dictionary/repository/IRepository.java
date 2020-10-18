package com.example.dictionary.repository;

import com.example.dictionary.model.Word;

import java.util.List;

public interface IRepository {
    Word getWord(Long id);
    List<Word> getWords();
    void deleteWord(Word word);
    void updateWord(Word word);
    void insertWord(Word word);
    void insertWords(Word... words);
}
