package ru.dan1l0s.project.task;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ru.dan1l0s.project.DBClient;
import ru.dan1l0s.project.R;

public class AddTask extends BottomSheetDialogFragment {


    private EditText newTaskName;
   // private EditText newTaskDesc;
    private Button newTaskSave;

    private DBClient database;

    public static AddTask newTask()
    {
        return new AddTask();
    }

    @Override
    public void onCreate(Bundle lastInstance)
    {
        super.onCreate(lastInstance);
        setStyle(STYLE_NORMAL, R.style.AddTaskStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle lastInstance)
    {
        View view = inflater.inflate(R.layout.add_new_task, group, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle lastInstance)
    {
        super.onViewCreated(view, lastInstance);
        newTaskName = getView().findViewById(R.id.addTaskName);
        //newTaskDesc = getView().findViewById(R.id.addTaskDesc);
        newTaskSave = getView().findViewById(R.id.addTaskButton);

        boolean ff1 = false;

        final Bundle bundle = getArguments();
        if (bundle != null)
        {
            ff1 = true;
            String name = bundle.getString("task");
            //String desc = bundle.getString("desc");
            newTaskName.setText(name);
            //newTaskDesc.setText(desc);
            if (name.length() > 0)
            {
                newTaskSave.setTextColor(ContextCompat.getColor(getContext(), R.color.teal_200));
            }
        }

        database = new DBClient(getActivity());
        database.openDataBase();

        newTaskName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(""))
                {
                    newTaskSave.setEnabled(false);
                    newTaskSave.setTextColor(Color.GRAY);
                }
                else
                {
                    newTaskSave.setEnabled(true);
                    newTaskSave.setTextColor(ContextCompat.getColor(getContext(), R.color.teal_200));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final boolean updated = ff1;

        newTaskSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = newTaskName.getText().toString();
                if (updated)
                {
                    database.updateTask(bundle.getInt("id"), name1);
                }
                else
                {
                    Task task = new Task();
                    task.setName(name1);
                    task.setStatus(0); //TODO: status needed
                    database.insertTask(task);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface)
    {
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener)
        {
            ((DialogCloseListener)activity).handleDialogClose(dialogInterface);
        }
    }

}
