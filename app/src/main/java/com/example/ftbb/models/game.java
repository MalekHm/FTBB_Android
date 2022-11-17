package com.example.ftbb.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "game")
public class game {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "teamidhome")
    private String teamidhome;
    @ColumnInfo(name = "teamidaway")
    private String teamidaway;
    @ColumnInfo(name = "round")
    private String round;
    @ColumnInfo(name = "date")
    private String dateg;
    @ColumnInfo(name = "scoret1")
    private String scoret1;
    @ColumnInfo(name = "scoret2")
    private String scoret2;


    public game(String teamidhome, String teamidaway, String round, String dateg) {
        this.teamidhome = teamidhome;
        this.teamidaway = teamidaway;
        this.round = round;
        this.dateg = dateg;
        this.scoret1 = "0";
        this.scoret2 = "0";

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamidhome() {
        return teamidhome;
    }

    public void setTeamidhome(String teamidhome) {
        this.teamidhome = teamidhome;
    }

    public String getTeamidaway() {
        return teamidaway;
    }

    public void setTeamidaway(String teamidaway) {
        this.teamidaway = teamidaway;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getDateg() {
        return dateg;
    }

    public void setDateg(String dateg) {
        this.dateg = dateg;
    }

    public String getScoret1() {
        return scoret1;
    }

    public void setScoret1(String scoret1) {
        this.scoret1 = scoret1;
    }

    public String getScoret2() {
        return scoret2;
    }

    public void setScoret2(String scoret2) {
        this.scoret2 = scoret2;
    }

    @Override
    public String toString() {
        return "game{" +
                "id=" + id +
                ", teamidhome='" + teamidhome + '\'' +
                ", teamidaway='" + teamidaway + '\'' +
                ", round='" + round + '\'' +
                ", dateg='" + dateg + '\'' +
                ", scoret1='" + scoret1 + '\'' +
                ", scoret2='" + scoret2 + '\'' +
                '}';
    }
}
