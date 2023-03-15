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

public class UpdateActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.mustang.teamlistjava.View.EXTRA_ID";
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
        setContentView(R.layout.activity_update);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Update Details");

        editTextName = findViewById(R.id.editTextNameUpdate);
        editTextNumber = findViewById(R.id.editTextNumberUpdate);
        editTextEmail = findViewById(R.id.editTextEmailUpdate);
        editTextAddress = findViewById(R.id.editTextAddressUpdate);
        buttonCancel = findViewById(R.id.buttonCancelUpdate);
        buttonSave = findViewById(R.id.buttonSaveUpdate);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateActivity.this, "Nothing updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDetails();
            }
        });

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            editTextNumber.setText(intent.getStringExtra(EXTRA_NUMBER));
            editTextEmail.setText(intent.getStringExtra(EXTRA_EMAIL));
            editTextAddress.setText(intent.getStringExtra(EXTRA_ADDRESS));
        }

    }

    private void saveDetails() {

        String memberName  = editTextName.getText().toString();
        String memberNumber = editTextNumber.getText().toString();
        String memberEmail  = editTextEmail.getText().toString();
        String memberAddress  = editTextAddress.getText().toString();

        if (memberName.equals("") || memberNumber.equals("") || memberEmail.equals("") || memberAddress.equals("")) {

            Toast.makeText(UpdateActivity.this, "Fill all details before submitting", Toast.LENGTH_SHORT).show();
        } else {
            Intent data = new Intent();
            data.putExtra(EXTRA_NAME, memberName);
            data.putExtra(EXTRA_NUMBER, memberNumber);
            data.putExtra(EXTRA_EMAIL, memberEmail);
            data.putExtra(EXTRA_ADDRESS, memberAddress);
            int id = getIntent().getIntExtra(EXTRA_ID,-1);
            if (id != -1) {
                data.putExtra(EXTRA_ID, id);
            }
            setResult(RESULT_OK, data);
            Toast.makeText(this, "Details updated", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}