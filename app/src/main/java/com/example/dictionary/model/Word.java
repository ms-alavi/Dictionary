package com.example.dictionary.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wordTable")
public class Word {
    @ColumnInfo(name = "wordId")@PrimaryKey(autoGenerate = true)
    private Long mId;
    @ColumnInfo(name = "english")
    private String mEnglish;
    @ColumnInfo(name = "persian")
    private String mPersian;


    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getEnglish() {
        return mEnglish;
    }

    public void setEnglish(String english) {
        mEnglish = english;
    }

    public String getPersian() {
        return mPersian;
    }

    public void setPersian(String persian) {
        mPersian = persian;
    }

    public Word(String english, String persian) {
        mEnglish = english;
        mPersian = persian;
    }

    public Word() {
    }
}
