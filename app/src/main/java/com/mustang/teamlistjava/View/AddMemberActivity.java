package com.mustang.teamlistjava.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mustang.teamlistjava.R;

import java.util.Objects;

public class AddMemberActivity extends AppCompatActivity {

    public static final String EXTRA_NAME =
            "com.mustang.teamlistjava.View.EXTRA_NAME";

    public static final String EXTRA_NUMBER =
            "com.mustang.teamlistjava.View.EXTRA_NUMBER";

    public static final String EXTRA_EMAIL =
            "com.mustang.teamlistjava.View.EXTRA_EMAIL";

    public static final String EXTRA_ADDRESS =
            "com.mustang.teamlistjava.View.EXTRA_ADDRESS";


    EditText editTextName;
    EditText editTextNumber;
    EditText editTextEmail;
    EditText editTextAddress;
    Button buttonCancel;
    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Details");

        editTextName = findViewById(R.id.editTextName);
        editTextNumber = findViewById(R.id.editTextNumber);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAddress = findViewById(R.id.editTextAddress);
        buttonCancel = findViewById(R.id.buttonCancel);
        buttonSave = findViewById(R.id.buttonSave);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddMemberActivity.this, "Nothing submitted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDetails();
            }
        });

    }

    private void saveDetails() {

        String memberName  = editTextName.getText().toString();
        String memberNumber = editTextNumber.getText().toString();
        String memberEmail  = editTextEmail.getText().toString();
        String memberAddress  = editTextAddress.getText().toString();

        if (memberName.equals("") || memberNumber.equals("") || memberEmail.equals("") || memberAddress.equals("")) {

            Toast.makeText(AddMemberActivity.this, "Fill all details before submitting", Toast.LENGTH_SHORT).show();
        } else {
            Intent data = new Intent();
            data.putExtra(EXTRA_NAME, memberName);
            data.putExtra(EXTRA_NUMBER, memberNumber);
            data.putExtra(EXTRA_EMAIL, memberEmail);
            data.putExtra(EXTRA_ADDRESS, memberAddress);
            setResult(RESULT_OK, data);
            Toast.makeText(this, "Member added", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}