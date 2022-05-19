package ru.dan1l0s.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.dan1l0s.project.task.Task;

public class LoadingActivity extends AppCompatActivity {
    private DatabaseReference db;
    private List<Task> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Objects.requireNonNull(getSupportActionBar()).hide();
        list = new ArrayList<>();
        db = FirebaseDatabase.getInstance("https://to-do-list-project-data-ba" +
                "se-default-rtdb.europe-west1.firebasedatabase.app/").getReference(Constants.USERS_KEY).child(Constants.USER_UID);
        Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
        getFromDB();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
    private void getFromDB() {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (list.size() > 0) list.clear();
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    Task task = ds.getValue(Task.class);
                    list.add(task);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        db.addValueEventListener(vListener);
    }
}