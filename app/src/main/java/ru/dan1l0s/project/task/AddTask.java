package ru.dan1l0s.project.task;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import ru.dan1l0s.project.Constants;
import ru.dan1l0s.project.MainActivity;
import ru.dan1l0s.project.R;

public class AddTask extends AppCompatActivity {
    private EditText nameText, descText, timeText, dateText;
    private DatabaseReference database;
    private String TASK_KEY = "Tasks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);
        initElem();
        database = FirebaseDatabase.getInstance("https://to-do-list-project-data-ba" +
                "se-default-rtdb.europe-west1.firebasedatabase.app/").getReference(TASK_KEY).child(Constants.USER_UID);
    }

    private void initElem()
    {
        nameText = findViewById(R.id.addNameText);
        descText = findViewById(R.id.addDescText);
        timeText = findViewById(R.id.addTimeText);
        dateText = findViewById(R.id.addDateText);

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
                    if (s.charAt(0)-'0' > 3)   s.replace(0, 1, "");
                }
                else if (s.length() == 2) {
                    if (s.charAt(0)-'0' == 3) {
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
                    else {
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
                else if (s.length() == 2) {
                    if (s.charAt(0)-'0' == 2) {
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

        if (TextUtils.isEmpty(name)) nameText.setError("Поле не может быть пустым");
        if (TextUtils.isEmpty(desc)) descText.setError("Поле не может быть пустым");
        if (TextUtils.isEmpty(date)) dateText.setError("Поле не может быть пустым");
        if (TextUtils.isEmpty(name))
            nameText.requestFocus();
        else if (TextUtils.isEmpty(desc))
            descText.requestFocus();
        else if (TextUtils.isEmpty(date))
            dateText.requestFocus();
        if (time.length() == 1) time+="0:00";
        if (time.length() == 2) time+=":00";
        if (time.length() == 4) time+="0";

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(desc)
                && !TextUtils.isEmpty(date))
        {
            if (time.isEmpty()) time = "23:59";
            String id = database.push().getKey();
            Task newTask = new Task(id, name, desc, time, date);;
            database.child(id).setValue(newTask);
            Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            Toast.makeText(this, "Одно из полей было пропущено", Toast.LENGTH_SHORT).show();
        }
    }
}