package ru.dan1l0s.project.recycler_view_adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.dan1l0s.project.DBClient;
import ru.dan1l0s.project.MainActivity;
import ru.dan1l0s.project.R;
import ru.dan1l0s.project.task.AddTask;
import ru.dan1l0s.project.task.Task;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private MainActivity activity;
    private List<Task> list;
    private DBClient database;

    public Adapter(DBClient database,MainActivity activity)
    {
        this.database = database;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_task, parent, false);
        return new ViewHolder(itemView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        CheckBox task;
        TextView name, desc;

        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.taskName);
            task = view.findViewById(R.id.checkbox);
            desc = view.findViewById(R.id.taskDesc);
        }
    }


    public void editItem(int pos)
    {
        Task task = list.get(pos);
        Bundle bundle = new Bundle();
        bundle.putInt("id",task.getId());
        bundle.putString("name", task.getName());
        AddTask addTask = new AddTask();
        addTask.setArguments(bundle);
        addTask.show(activity.getSupportFragmentManager(), "ActionBottomDialog");
    }

    public void onBindViewHolder(ViewHolder holder, int position)
    {
        database.openDataBase();
        Task item = list.get(position);
        holder.name.setText(item.getName());
//        holder.task.setChecked(!IntToBool(item.getStatus())); // FIXME no anymore status variable
        holder.desc.setText(item.getDesc());
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    database.updateStatus(item.getId(), 0);
                }
                else
                {
                    database.updateStatus(item.getId(), 1);
                }
            }
        });
    }

    private boolean IntToBool(int a)
    {
        return a != 0;
    }

    public int getItemCount()
    {
        return list.size();
    }

    public void setList(List<Task> list)
    {
        this.list = list;
    }

    public Context getContext()
    {
        return activity;
    }

}
