package ru.dan1l0s.project.recycler_view_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ru.dan1l0s.project.Constants;
import ru.dan1l0s.project.MainActivity;
import ru.dan1l0s.project.R;
import ru.dan1l0s.project.task.Task;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private OnTaskListener onTaskListener;
    private MainActivity activity;
    private List<Task> list;
    private DatabaseReference database;
    private ImageView deleteImage;

    public Adapter(MainActivity activity, List<Task> list, OnTaskListener onTaskListener) {
        this.onTaskListener = onTaskListener;
        this.list = list;
        this.activity = activity;
        database = FirebaseDatabase.getInstance("https://to-do-list-project-data-ba" +
                "se-default-rtdb.europe-west1.firebasedatabase.app/").getReference(Constants.USERS_KEY).child(Constants.USER_UID);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_task, parent, false);
        return new ViewHolder(itemView, onTaskListener);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout relativeLayout;
        CheckBox checkBox;
        TextView name, desc, date, time;
        OnTaskListener onTaskListener;

        public ViewHolder(View view, OnTaskListener onTaskListener) {
            super(view);
            relativeLayout = view.findViewById(R.id.cardLayout);
            checkBox = view.findViewById(R.id.checkbox);
            name = view.findViewById(R.id.taskName);
            desc = view.findViewById(R.id.taskDesc);
            date = view.findViewById(R.id.taskDate);
            time = view.findViewById(R.id.taskTime);
            deleteImage = view.findViewById(R.id.deleteImage);
            this.onTaskListener = onTaskListener;

            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTaskListener != null)
                    {
                        int pos = getAbsoluteAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION)
                        {
                            onTaskListener.onDeleteClick(pos);
                        }
                    }
                }
            });
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTaskListener.onTaskClick(getAbsoluteAdapterPosition());
        }
    }


    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Task item = list.get(position);
        holder.name.setText(item.getName());
        holder.desc.setText(item.getDesc());
        holder.date.setText(item.getDate());
        holder.time.setText(item.getTime());
        if (item.getStatus() == 0)
        {
            if (item.compareToDate())
            {
                holder.relativeLayout.setBackgroundColor(activity.getResources().getColor(R.color.light_red));
            }
            else
            {
                holder.relativeLayout.setBackgroundColor(Color.WHITE);
            }
            holder.checkBox.setChecked(false);
        }
        else
        {
            holder.checkBox.setChecked(true);
            holder.relativeLayout.setBackgroundColor(activity.getResources().getColor(R.color.light_green));
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked())
                {
                    database.child(item.getId()).child("status").setValue(1);
                }
                else
                {
                    database.child(item.getId()).child("status").setValue(0);
                }
            }
        });
    }

    private boolean IntToBool(int a) { return a != 0; }

    public int getItemCount() { return list.size(); }

    public void setList(List<Task> list) { this.list = list; }

    public Context getContext() { return activity; }

    public interface OnTaskListener {
        void onTaskClick(int pos);
        void onDeleteClick(int pos);
    }

}