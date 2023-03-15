package com.mustang.teamlistjava.Room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mustang.teamlistjava.Model.TeamMember;

@Database(entities = {TeamMember.class},exportSchema = false,version = 1)
public abstract class TeamDatabase extends RoomDatabase {

    private static TeamDatabase instance;

    public abstract TeamDAO getTeamDao();

    public static synchronized TeamDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), TeamDatabase.class, "team_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {
        private TeamDAO teamDAO;

        private PopulateDbAsyncTask(TeamDatabase db) {
            teamDAO = db.getTeamDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            teamDAO.insert(new TeamMember("Name","Email id","Address","Phone Number"));
            teamDAO.insert(new TeamMember("Name 2","Email id","Address","Phone Number"));
            return null;
        }
    }
}


