package ru.dan1l0s.project.recycler_view_adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.dan1l0s.project.MainActivity;
import ru.dan1l0s.project.R;
import ru.dan1l0s.project.task.Task;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private OnTaskListener onTaskListener;
    private MainActivity activity;
    private List<Task> list;
    private ImageView deleteImage;

    public Adapter(MainActivity activity, List<Task> list, OnTaskListener onTaskListener) {
        this.onTaskListener = onTaskListener;
        this.list = list;
        this.activity = activity;
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


    public void onBindViewHolder(ViewHolder holder, int position) {
        Task item = list.get(position);
        holder.name.setText(item.getName());
        holder.desc.setText(item.getDesc());
        holder.date.setText(item.getDate());
        holder.time.setText(item.getTime());
        holder.relativeLayout.setBackgroundColor(Color.WHITE);
        if (item.compareToDate())
        {
            holder.relativeLayout.setBackgroundColor(activity.getResources().getColor(R.color.light_red));
        }
        else
        {
            holder.relativeLayout.setBackgroundColor(Color.WHITE);
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkBox.isChecked())
                {
                    holder.relativeLayout.setBackgroundColor(activity.getResources().getColor(R.color.light_green));
                }
                else
                {
                    if (item.compareToDate())
                    {
                        holder.relativeLayout.setBackgroundColor(activity.getResources().getColor(R.color.light_red));
                    }
                    else
                    {
                        holder.relativeLayout.setBackgroundColor(Color.WHITE);
                    }
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