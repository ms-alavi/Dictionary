package com.example.dictionary.dataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dictionary.model.Word;

import java.util.List;


@Dao
public interface WordDAO {
    @Insert
    void insertWord(Word word);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWords(Word... words);

    @Delete
    void deleteWord(Word word);

    @Update
    void updateWord(Word word);

    @Query("SELECT * FROM wordTable")
    List<Word> getWords();

    @Query("SELECT * FROM wordTable WHERE wordId=:id")
    Word getWord(Long id);
    @Query("SELECT * FROM wordTable WHERE english LIKE :string OR persian LIKE :string " )
    List<Word> searchWord(String string);

}
