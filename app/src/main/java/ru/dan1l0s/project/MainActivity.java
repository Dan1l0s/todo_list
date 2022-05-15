package ru.dan1l0s.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ru.dan1l0s.project.recycler_view_adapter.Adapter;
import ru.dan1l0s.project.task.AddTask;
import ru.dan1l0s.project.task.Task;

public class  MainActivity extends AppCompatActivity implements Adapter.OnTaskListener{

    private RecyclerView ListRecyclerView;
    private Adapter adapter;
    private List<Task> list;

    private DatabaseReference database;
    String TASK_KEY = "Tasks";
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListRecyclerView = findViewById(R.id.listRecyclerView);
        floatingActionButton = findViewById(R.id.floating_action_button);

        getSupportActionBar().hide(); // same as in activity_loading
        database = FirebaseDatabase.getInstance("https://to-do-list-project-data-ba" +
                "se-default-rtdb.europe-west1.firebasedatabase.app/").getReference(TASK_KEY);

        getDataFromDB();
        initRecyclerView();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTask.class);
                startActivity(intent);
            }
        });



        /*

        // ListRecyclerView.setAdapter(adapter); //FIXME: all commented strings crash program, it's total gg.

        */


    }

    private void getDataFromDB() {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (list.size() > 0) list.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Task task = ds.getValue(Task.class);
                    if (task == null) {
                        Toast.makeText(MainActivity.this, "Было " +
                                "получено пустое задание", Toast.LENGTH_SHORT).show();
                        continue;
                    }
                    list.add(task);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        database.addValueEventListener(vListener);
    }

    private void initRecyclerView()
    {
        ListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new Adapter(this, list, this);
        ListRecyclerView.setAdapter(adapter);
    }


    @Override
    public void onTaskClick(int pos) {
        //DatabaseReference reference =
    }
}