package ru.dan1l0s.project.task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import ru.dan1l0s.project.Constants;
import ru.dan1l0s.project.R;

public class UpdateTask extends AppCompatActivity {
    private EditText nameText, descText, timeText, dateText;
    private DatabaseReference database;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatetask);
        database = FirebaseDatabase.getInstance("https://to-do-list-project-data-ba" +
                "se-default-rtdb.europe-west1.firebasedatabase.app/").getReference(Constants.USERS_KEY).child(Constants.USER_UID);
        initElem();
    }

    private void initElem()
    {
        Objects.requireNonNull(getSupportActionBar()).hide();
        nameText = findViewById(R.id.updateNameText);
        descText = findViewById(R.id.updateDescText);
        timeText = findViewById(R.id.updateTimeText);
        dateText = findViewById(R.id.updateDateText);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        nameText.setText(intent.getStringExtra("name"));
        descText.setText(intent.getStringExtra("desc"));
        timeText.setText(intent.getStringExtra("time"));
        dateText.setText(intent.getStringExtra("date"));

        task = new Task(intent.getStringExtra("id"), intent.getStringExtra("name"), intent.getStringExtra("desc"),
                intent.getStringExtra("time"), intent.getStringExtra("date"));

        TextWatcher dateWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1)
                {
                    if (s.charAt(0)-'0' > 3) s.replace(0, 1, "");
                }
                else if (s.length() == 2)
                {
                    if (s.charAt(0)-'0' == 3)
                    {
                        if (s.charAt(1)-'0' > 1) s.replace(1, 2, "");
                    }
                }
                else if (s.length() == 3)
                {
                    if (s.charAt(2) == '/') s.replace(2, 3, "");
                    else if (s.charAt(2)-'0' > 1)
                    {
                        s.replace(2, 3, "");
                    }
                    else
                    {
                        s.insert(2,"/");
                    }
                }
                else if (s.length() == 5)
                {
                    if (s.charAt(3) == '1')
                    {
                        if (s.charAt(4)-'0' > 2) s.replace(4, 5, "");
                    }
                }
                else if (s.length() == 6)
                {
                    if (s.charAt(5) == '/') s.replace(4, 6, "");
                    else
                    {
                        s.insert(5, "/202");
                    }
                }
            }
        };

        TextWatcher timeWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1)
                {
                    if (s.charAt(0)-'0' > 2)   s.replace(0, 1, "");
                }
                else if (s.length() == 2)
                {
                    if (s.charAt(0)-'0' == 2)
                    {
                        if (s.charAt(1)-'0' > 3) s.replace(1, 2, "");
                    }
                }
                else if (s.length() == 3)
                {
                    if (s.charAt(2) == ':') s.replace(2, 3, "");
                    else if (s.charAt(2)-'0' > 5)
                    {
                        s.replace(2, 3, "");
                    }
                    else {
                        s.insert(2,":");
                    }
                }
            }
        };

        dateText.addTextChangedListener(dateWatcher);
        timeText.addTextChangedListener(timeWatcher);
    }

    public void onClickSaveButt(View view)
    {
        String name = nameText.getText().toString();
        String desc = descText.getText().toString();
        String time = timeText.getText().toString();
        String date = dateText.getText().toString();

        if (date.length() == 0) date = "31/12/2099";
        if (time.length() == 0) time = "23:59";
        if (time.length() == 1) time+="0:00";
        if (time.length() == 2) time+=":00";
        if (time.length() == 4) time+="0";


        if (TextUtils.isEmpty(name)) nameText.setError(getString(R.string.edit_text_empty));
        if (TextUtils.isEmpty(desc)) descText.setError(getString(R.string.edit_text_empty));
        if (!TextUtils.isEmpty(date) && date.length() != 10) dateText.setError(getString(R.string.edit_text_empty));


        if (TextUtils.isEmpty(name))
            nameText.requestFocus();
        else if (TextUtils.isEmpty(desc))
            descText.requestFocus();
        else if (date.length() != 0 && date.length() < 10)
            dateText.requestFocus();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(desc)
                && (date.length() == 10 || date.length() == 0))
        {
            if (time.isEmpty()) time = "23:59";
            task = new Task(task.getId(), name, desc, time, date);
            database.child(task.getId()).setValue(task);
            Toast.makeText(this, getString(R.string.save_task_succ), Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            Toast.makeText(this, getString(R.string.save_task_error), Toast.LENGTH_SHORT).show();
        }
    }
}