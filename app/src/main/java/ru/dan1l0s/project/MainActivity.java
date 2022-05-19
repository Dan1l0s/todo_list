package ru.dan1l0s.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import ru.dan1l0s.project.recycler_view_adapter.Adapter;
import ru.dan1l0s.project.task.AddTask;
import ru.dan1l0s.project.task.Task;
import ru.dan1l0s.project.task.UpdateTask;

public class  MainActivity extends AppCompatActivity implements Adapter.OnTaskListener
{
    private RecyclerView ListRecyclerView;
    private TextView textView;
    private Adapter adapter;
    private List<Task> list;

    private DatabaseReference database;
    private String TASK_KEY = "Tasks";
    private FloatingActionButton floatingActionButton;

    private FirebaseAuth mAuth;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.userTitle);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null)
        {
            finish();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        else
        {
            Constants.USER_UID = user.getUid();
            textView.setText("Signed in as " + mAuth.getCurrentUser().getEmail());
        }

        ListRecyclerView = findViewById(R.id.listRecyclerView);
        floatingActionButton = findViewById(R.id.floating_action_button);


        Objects.requireNonNull(getSupportActionBar()).hide();
        database = FirebaseDatabase.getInstance("https://to-do-list-project-data-ba" +
                "se-default-rtdb.europe-west1.firebasedatabase.app/").getReference(TASK_KEY).child(Constants.USER_UID);


        getDataFromDB();
        initialisation();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddTask.class);
                startActivityForResult(intent, 0);

            }
        });

        btnLogout = findViewById(R.id.logoutButton);
        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private void getDataFromDB() {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (list.size() > 0) list.clear();
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    Task task = ds.getValue(Task.class);
                    if (task == null)
                    {
                        Toast.makeText(MainActivity.this, "Было получено пустое задание", Toast.LENGTH_SHORT).show();
                        continue;
                    }
                    list.add(task);
                }
                Collections.sort(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        database.addValueEventListener(vListener);
    }


    private void initialisation()
    {
        ListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Task>();
        adapter = new Adapter(this, list, this);
        ListRecyclerView.setAdapter(adapter);
    }


    @Override
    public void onDeleteClick(int pos)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Вы точно хотите удалить задание " + list.get(pos).getName() + "?").setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Task tmp = list.get(pos);
                        System.out.println(tmp.getName() + " " + tmp.getId());
                        Query query = FirebaseDatabase.getInstance("https://to-do-list-project-data-ba" +
                                "se-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child(TASK_KEY).child(Constants.USER_UID).orderByChild("id").equalTo(tmp.getId());
                        query.addListenerForSingleValueEvent(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot: dataSnapshot.getChildren())
                                {
                                    snapshot.getRef().removeValue();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }

                        });
                    }
                }).setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(MainActivity.this, "ладно", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onTaskClick(int pos) {
        Task task = list.get(pos);
        Intent intent = new Intent(MainActivity.this, UpdateTask.class);
        intent.putExtra("id", task.getId());
        intent.putExtra("name",task.getName());
        intent.putExtra("desc",task.getDesc());
        intent.putExtra("date",task.getDate());
        intent.putExtra("time",task.getTime());
        startActivity(intent);
    }
}