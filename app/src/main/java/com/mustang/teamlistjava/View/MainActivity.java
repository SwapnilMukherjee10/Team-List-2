package com.mustang.teamlistjava.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mustang.teamlistjava.Adapter.TeamAdapter;
import com.mustang.teamlistjava.Model.TeamMember;
import com.mustang.teamlistjava.R;
import com.mustang.teamlistjava.Room.TeamDatabase;
import com.mustang.teamlistjava.ViewModel.TeamViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TeamViewModel teamViewModel;
    private List<TeamMember> teamMembers;
    public static final int ADD_MEMBER_REQUEST = 1;
    public static final int UPDATE_MEMBER_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TeamAdapter adapter = new TeamAdapter();
        recyclerView.setAdapter(adapter);

        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
        teamViewModel.getAllMembers().observe(this, teamMembers -> {
            // update recyclerview
            adapter.setMembers(teamMembers);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                teamViewModel.delete(adapter.getMemberAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Member details deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new TeamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TeamMember teamMember) {
                Intent intent = new Intent(MainActivity.this,UpdateActivity.class);
                intent.putExtra(UpdateActivity.EXTRA_ID,teamMember.getId());
                intent.putExtra(UpdateActivity.EXTRA_NAME,teamMember.getMemberName());
                intent.putExtra(UpdateActivity.EXTRA_NUMBER,teamMember.getContact());
                intent.putExtra(UpdateActivity.EXTRA_EMAIL,teamMember.getEmail());
                intent.putExtra(UpdateActivity.EXTRA_ADDRESS,teamMember.getAddress());
                startActivityForResult(intent,UPDATE_MEMBER_REQUEST);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_add_member :
                Intent intent = new Intent(MainActivity.this,AddMemberActivity.class);
                startActivityForResult(intent,ADD_MEMBER_REQUEST);
                break;
            case R.id.item_delete_all_members :
                showDialogMessage();
                break;
            case R.id.item_logout :

                FirebaseAuth.getInstance().signOut();
                Intent loginIntent = new Intent(this,LoginActivity.class);
                startActivity(loginIntent);
                Toast.makeText(this, "You have been logged out", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return true;
    }

    void showDialogMessage() {

        new AlertDialog.Builder(this)
        .setTitle("Delete every member info?")
        .setMessage(Html.fromHtml("If clicked YES every member info will be deleted, if you want to delete a specific member info," +
                " please swipe left or right."))
        .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .setPositiveButton("Yes", (dialog, which) -> {
                    teamViewModel.deleteAllMembers();
                    Toast.makeText(MainActivity.this, "All member details deleted", Toast.LENGTH_SHORT).show();
                })
                .create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_MEMBER_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            String memberName = data.getStringExtra(AddMemberActivity.EXTRA_NAME);
            String memberNumber = data.getStringExtra(AddMemberActivity.EXTRA_NUMBER);
            String memberEmail = data.getStringExtra(AddMemberActivity.EXTRA_EMAIL);
            String memberAddress = data.getStringExtra(AddMemberActivity.EXTRA_ADDRESS);

            TeamMember teamMember = new TeamMember(memberName, memberEmail,memberAddress,memberNumber);
            teamViewModel.insert(teamMember);
        } else if (requestCode == UPDATE_MEMBER_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(UpdateActivity.EXTRA_ID,-1);

            if(id == -1) {
                Toast.makeText(this, "Details cannot be updated", Toast.LENGTH_SHORT).show();
            }
            String memberName = data.getStringExtra(AddMemberActivity.EXTRA_NAME);
            String memberNumber = data.getStringExtra(AddMemberActivity.EXTRA_NUMBER);
            String memberEmail = data.getStringExtra(AddMemberActivity.EXTRA_EMAIL);
            String memberAddress = data.getStringExtra(AddMemberActivity.EXTRA_ADDRESS);

            TeamMember teamMember = new TeamMember(memberName, memberEmail,memberAddress,memberNumber);
            teamMember.setId(id);
            teamViewModel.update(teamMember);

        }
    }
}