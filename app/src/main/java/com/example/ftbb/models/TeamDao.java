package com.example.ftbb.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface TeamDao {

    @Query("SELECT * FROM team")
    List<team> getAll();

    @Query("SELECT * FROM team WHERE NOT name == :name")
    List<team> getAllExept(String name);

    @Query("SELECT * FROM team WHERE id == :id")
    user findbyId(String id);

    @Query("SELECT * FROM team WHERE name == :name ")
    user findbyname(String name);

    @Insert
    void insertOne(team tem);

    @Delete
    void delete(team team);

}
