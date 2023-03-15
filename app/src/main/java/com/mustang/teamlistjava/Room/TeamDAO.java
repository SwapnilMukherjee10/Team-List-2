package com.mustang.teamlistjava.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mustang.teamlistjava.Model.TeamMember;

import java.util.List;

@Dao
public interface TeamDAO {

    @Insert
    void insert(TeamMember teamMember);

    @Update
    void update(TeamMember teamMember);

    @Delete
    void delete(TeamMember teamMember);

    @Query("DELETE FROM team_table")
    void deleteAllMembers();

    @Query("SELECT * FROM team_table ORDER BY id ASC")
    LiveData<List<TeamMember>> getAllMembers();

}
