package ru.dan1l0s.project;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import ru.dan1l0s.project.recycler_view_adapter.Adapter;
import ru.dan1l0s.project.task.Task;

public class  MainActivity extends AppCompatActivity {

    private RecyclerView ListRecyclerView;
    private Adapter adapter;
    private List<Task> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide(); // same as in activity_loading

        list = new ArrayList<>();
        adapter = new Adapter(this);

        ListRecyclerView = findViewById(R.id.listRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        ListRecyclerView.setLayoutManager(manager);
        ListRecyclerView.setAdapter(adapter);

        Task task = new Task();
        task.setName("test name");
        task.setDesc("test desc");
//        task.setStatus(600);
//        task.setId(0);

        list.add(task);
        list.add(task);
        list.add(task);
        list.add(task);
        list.add(task);
        list.add(task);
        list.add(task);
        list.add(task);
        list.add(task);
        list.add(task);
        list.add(task);

        adapter.setList(list);
    }

}