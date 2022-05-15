package ru.dan1l0s.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ru.dan1l0s.project.task.Task;

public class DBTestActivity extends AppCompatActivity {
    private EditText nameText, descText, timeText, dateText;
    private DatabaseReference database;
    private String TASK_KEY = "Tasks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bdtest);
        bindEditText();
        database = FirebaseDatabase.getInstance("https://to-do-list-project-data-ba" +
                "se-default-rtdb.europe-west1.firebasedatabase.app/").getReference(TASK_KEY);
    }

    private void bindEditText() {
        nameText = findViewById(R.id.nameText);
        descText = findViewById(R.id.descText);
        timeText = findViewById(R.id.timeText);
        dateText = findViewById(R.id.dateText);
    }

    public void onClickSaveButt(View view) {
        String id = database.getKey();
        String name = nameText.getText().toString();
        String desc = descText.getText().toString();
        String time = timeText.getText().toString();
        String date = dateText.getText().toString();
        Task newTask = new Task(id, name, desc, time, date);
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(desc)
                && !TextUtils.isEmpty(time) && !TextUtils.isEmpty(date)) {
            database.push().setValue(newTask);
            Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Одно из полей было пропущено", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickLoadButt(View view) {}
}