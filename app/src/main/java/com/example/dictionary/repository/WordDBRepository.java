package com.example.dictionary.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.dictionary.dataBase.WordDAO;
import com.example.dictionary.dataBase.WordDB;
import com.example.dictionary.model.Word;

import java.util.List;

public class WordDBRepository implements IRepository{
    private  static WordDBRepository sInstance;
    private WordDAO mWordDAO;
    private Context mContext;
    public static WordDBRepository getInstance(Context context){
        if (sInstance==null)sInstance=new WordDBRepository(context);
        return sInstance;
    }
    private WordDBRepository(Context context) {
        mContext=context.getApplicationContext();
        mWordDAO=Room.databaseBuilder(mContext,WordDB.class,"dictionary.db")
                .allowMainThreadQueries()
                .build().getWordDAO();
    }

    @Override
    public Word getWord(Long id) {
        return mWordDAO.getWord(id);
    }

    @Override
    public List<Word> getWords() {
        return mWordDAO.getWords();
    }

    @Override
    public void deleteWord(Word word) {
        mWordDAO.deleteWord(word);

    }


    @Override
    public void updateWord(Word word) {
        mWordDAO.updateWord(word);
    }

    @Override
    public void insertWord(Word word) {
        mWordDAO.insertWord(word);
    }

    @Override
    public void insertWords(Word... words) {
        mWordDAO.insertWords(words);
    }

    @Override
    public List<Word> searchWords(String string) {
     return    mWordDAO.searchWord(string);
    }
}
