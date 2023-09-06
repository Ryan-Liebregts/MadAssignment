package com.example.madassignment;

public class LeaderBoardData {
    private String userName;

    private int rank;
    private int userIcon;

    private int score;

    public LeaderBoardData(String userName, int rank, int userIcon, int score) {
        this.userIcon = userIcon;
        this.userName = userName;
        this.rank = rank;
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getUserIcon() {
        return userIcon;
    }
    public void setUserIcon(int userIcon) {
        this.userIcon = userIcon;
    }

    public int getUserRank() {
        return rank;
    }
    public void setUserRank(int rank) {
        this.rank = rank;
    }

    public int getUserScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
}
