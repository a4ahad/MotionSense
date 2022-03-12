package com.leadiing.isense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.leadiing.isense.R;

public class MainActivity extends AppCompatActivity {

    LinearLayout inputGesture, developerInfo, deleteGesture, how_it_workId;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //hide action bar
     //  getSupportActionBar().hide();


        //find all item
        inputGesture = findViewById(R.id.inputGestureId);
        developerInfo = findViewById(R.id.developerInfoId);
        deleteGesture = findViewById(R.id.deleteGestureId);
        how_it_workId = findViewById(R.id.how_it_workId);


        inputGesture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InputGesture.class);
                startActivity(intent);
            }
        });

        developerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Developer_info.class);
                startActivity(intent);
            }
        });
        deleteGesture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Delete_Activity.class);
                startActivity(intent);
            }
        });

        how_it_workId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, How_it_works.class);
                startActivity(intent);
            }
        });


    }

    //for log in in register

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.profile:
                Toast.makeText(MainActivity.this, "New Register", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                break;

            case R.id.logout:
                Logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, StartActivity.class));
        finish();
    }

}