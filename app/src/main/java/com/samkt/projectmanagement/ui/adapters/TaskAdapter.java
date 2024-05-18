package com.samkt.projectmanagement.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.samkt.projectmanagement.R;
import com.samkt.projectmanagement.data.model.response.Task;
import com.samkt.projectmanagement.utils.Utils;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{

    private final List<Task> tasks;

    public TaskAdapter(List<Task> tasks){
        this.tasks = tasks;
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item,parent,false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        String deadline = "Deadline: " + Utils.extractDate(task.getDeadline());
        holder.tvTaskName.setText(task.getName());
        holder.tvDeadline.setText(deadline);

        holder.deleteIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );

        holder.editIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView tvTaskName,tvDeadline;
        ImageView deleteIcon;
        ImageView editIcon;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskName = itemView.findViewById(R.id.tvTaskName);
            tvDeadline = itemView.findViewById(R.id.tvTaskDeadline);
            deleteIcon = itemView.findViewById(R.id.icDelete);
            editIcon = itemView.findViewById(R.id.icEdit);
        }
    }
}
