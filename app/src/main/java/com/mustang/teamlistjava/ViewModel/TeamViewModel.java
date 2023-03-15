package com.mustang.teamlistjava.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mustang.teamlistjava.Model.TeamMember;
import com.mustang.teamlistjava.Repository.TeamRepository;

import java.util.List;

public class TeamViewModel extends AndroidViewModel {

    private TeamRepository teamRepository;
    private LiveData<List<TeamMember>> allMembers;

    public TeamViewModel(@NonNull Application application) {
        super(application);
        teamRepository = new TeamRepository(application);
        allMembers = teamRepository.getAllMembers();
    }

    public void insert(TeamMember teamMember) {
        teamRepository.insert(teamMember);
    }

    public void update(TeamMember teamMember) {
        teamRepository.update(teamMember);
    }

    public void delete(TeamMember teamMember) {
        teamRepository.delete(teamMember);
    }

    public void deleteAllMembers() {
        teamRepository.deleteAllMembers();
    }

    public LiveData<List<TeamMember>> getAllMembers() {
        return allMembers;
    }

}
