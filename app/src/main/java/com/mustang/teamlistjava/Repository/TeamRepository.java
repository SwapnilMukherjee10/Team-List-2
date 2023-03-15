package com.mustang.teamlistjava.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.mustang.teamlistjava.Model.TeamMember;
import com.mustang.teamlistjava.Room.TeamDAO;
import com.mustang.teamlistjava.Room.TeamDatabase;

import java.util.List;

public class TeamRepository {

    private TeamDAO teamDAO;
    private LiveData<List<TeamMember>> allMembers;

    public TeamRepository(Application application) {

        TeamDatabase database = TeamDatabase.getInstance(application);
        teamDAO = database.getTeamDao();
        allMembers = teamDAO.getAllMembers();

    }

    public void insert(TeamMember teamMember) {
        new InsertMemberAsyncTask(teamDAO).execute(teamMember);
    }

    public void update(TeamMember teamMember) {
        new UpdateMemberAsyncTask(teamDAO).execute(teamMember);

    }

    public void delete(TeamMember teamMember) {
        new DeleteMemberAsyncTask(teamDAO).execute(teamMember);
    }

    public void deleteAllMembers() {
        new DeleteAllMembersAsyncTask(teamDAO).execute();
    }

    public LiveData<List<TeamMember>> getAllMembers() {
        return allMembers;
    }

    private static class InsertMemberAsyncTask extends AsyncTask<TeamMember,Void,Void> {
        private TeamDAO teamDAO;

        private InsertMemberAsyncTask(TeamDAO teamDAO) {
            this.teamDAO = teamDAO;
        }
        @Override
        protected Void doInBackground(TeamMember... teamMembers) {
            teamDAO.insert(teamMembers[0]);
            return null;
        }
    }

    private static class UpdateMemberAsyncTask extends AsyncTask<TeamMember,Void,Void> {
        private TeamDAO teamDAO;

        private UpdateMemberAsyncTask(TeamDAO teamDAO) {
            this.teamDAO = teamDAO;
        }
        @Override
        protected Void doInBackground(TeamMember... teamMembers) {
            teamDAO.update(teamMembers[0]);
            return null;
        }
    }

    private static class DeleteMemberAsyncTask extends AsyncTask<TeamMember,Void,Void> {
        private TeamDAO teamDAO;

        private DeleteMemberAsyncTask(TeamDAO teamDAO) {
            this.teamDAO = teamDAO;
        }
        @Override
        protected Void doInBackground(TeamMember... teamMembers) {
            teamDAO.delete(teamMembers[0]);
            return null;
        }
    }

    private static class DeleteAllMembersAsyncTask extends AsyncTask<Void,Void,Void> {
        private TeamDAO teamDAO;

        private DeleteAllMembersAsyncTask(TeamDAO teamDAO) {
            this.teamDAO = teamDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            teamDAO.deleteAllMembers();
            return null;
        }
    }

}
