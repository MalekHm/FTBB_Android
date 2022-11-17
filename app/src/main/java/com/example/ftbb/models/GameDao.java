package com.example.ftbb.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface GameDao {

    @Query("SELECT * FROM game")
    List<game> getAll();

    @Query("SELECT * FROM game WHERE NOT teamidhome == :teamidhome")
    List<game> getAllExept(String teamidhome);

    @Query("SELECT * FROM game WHERE id == :id")
    game findbyId(String id);

    @Query("SELECT * FROM game WHERE teamidhome == :team1 and teamidaway == :team2 ")
    game findbyteams(String team1,String team2);

    @Query("SELECT * FROM game WHERE round == :round ")
    game findbyround(String round);

    @Insert
    void insertOne(game tem);

    @Update
    void updateGame(game g);

    @Delete
    void delete(game team);

}
