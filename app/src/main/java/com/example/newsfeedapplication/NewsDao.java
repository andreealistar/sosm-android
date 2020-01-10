package com.example.newsfeedapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(News news);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(News... news);

    @Query("DELETE FROM news")
    void deleteAll();

    @Query("SELECT * FROM news")
    public List<News> loadAll();
}