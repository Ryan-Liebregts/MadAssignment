package com.example.madassignment;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    // Databse for users
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "user_name")
    private String userName;

    @ColumnInfo(name = "user_icon")
    private int userIcon;

    @ColumnInfo(name = "user_wins")
    private int userWins;

    @ColumnInfo(name = "user_losses")
    private int userLosses;

    @ColumnInfo(name = "user_games")
    private int userGames;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }


    public int getUserWins() {return userWins;}

    public void setUserWins(int wins) { this.userWins = wins;}

    public int getUserLosses() {
        return userLosses;
    }
    public void setUserLosses(int losses) {
        this.userLosses = losses;
    }

    public int getUserGames() {
        return userGames;
    }
    public void setUserGames(int games) {
        this.userGames = games;
    }

    public int getUserIcon() { return  userIcon;}

    public void setUserIcon(int userIcon) {
        this.userIcon = userIcon;
    }


}
