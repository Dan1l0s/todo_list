package ru.dan1l0s.project;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ru.dan1l0s.project.task.Task;

public class ReadActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> nameArr;
    private List<Task> dataArr;
    private DatabaseReference database;
    private String TASK_KEY = "Tasks";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_layout);
        initialisation();
        getDataFromDB();
    }

    private void initialisation() {
        listView = findViewById(R.id.listView);
        nameArr = new ArrayList<>();
        dataArr = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, nameArr);
        listView.setAdapter(adapter);
        database = FirebaseDatabase.getInstance("https://to-do-list-project-data-ba" +
                "se-default-rtdb.europe-west1.firebasedatabase.app/").getReference(TASK_KEY);
    }

    private void getDataFromDB() {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (nameArr.size() > 0) nameArr.clear();
                if (dataArr.size() > 0) dataArr.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Task task = ds.getValue(Task.class);
                    if (task == null) {
                        Toast.makeText(ReadActivity.this, "Было " +
                                "получено пустое задание", Toast.LENGTH_SHORT).show();
                        continue;
                    }
                    nameArr.add(task.getName());
                    dataArr.add(task);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        database.addValueEventListener(vListener);
    }

    private void setOnClickItem()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
                Task task = dataArr.get(index);
            }
        });
    }
}
