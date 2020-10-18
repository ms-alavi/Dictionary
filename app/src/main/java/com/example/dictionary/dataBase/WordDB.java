package com.example.dictionary.dataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.dictionary.dataBase.WordDAO;
import com.example.dictionary.model.Word;

@Database(entities = Word.class, version = 1)
public abstract class WordDB extends RoomDatabase {
    public abstract WordDAO getWordDAO();
}
