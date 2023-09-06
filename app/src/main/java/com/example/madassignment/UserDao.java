package com.example.madassignment;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface UserDao
{
    @Insert
    void insert(User... user);

    @Update
    void update(User... user);

    @Delete
    void delete(User... user);

    @Query("SELECT * FROM users ORDER BY user_wins DESC")
    List<User> getAllUsers();

//    @Query("SELECT user_name AND user_icon FROM users")
//    List<User> getAllUsersNameAndIcon();

    @Query("SELECT * FROM users WHERE user_name = :userName")
    User getUsersByName(String userName);

    @Query("SELECT * FROM users WHERE id = :userId")
    User getStudentByID(int userId);


    @Query("UPDATE users SET user_wins = user_wins + 1 WHERE id = :userId")
    void updateUserWins(int userId);


    @Query("UPDATE users SET user_wins = user_losses + 1 WHERE id = :userId")
    void updateUserLosses(int userId);
}
