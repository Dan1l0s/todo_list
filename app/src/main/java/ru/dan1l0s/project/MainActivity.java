package ru.dan1l0s.project;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.dan1l0s.project.recycler_view_adapter.Adapter;
import ru.dan1l0s.project.task.AddTask;
import ru.dan1l0s.project.task.DialogCloseListener;
import ru.dan1l0s.project.task.Task;

public class  MainActivity extends AppCompatActivity implements DialogCloseListener{

    private RecyclerView ListRecyclerView;
    private Adapter adapter;
    private List<Task> list;
//    private DBClient database;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); // same as in activity_loading

//        database = new DBClient(this);
//        database.openDataBase();

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
        floatingActionButton = findViewById(R.id.floating_action_button);
        ListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new Adapter(database, this);

        // ListRecyclerView.setAdapter(adapter); //FIXME: all commented strings crash program, it's total gg.

        /*
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTask.newTask().show(getSupportFragmentManager(), "ActionBottomDialog");
            }
        });
        */
    }


    @Override
    public void handleDialogClose(DialogInterface dialogInterface)
    {
//        list = database.getList();
        Collections.reverse(list);
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }

}