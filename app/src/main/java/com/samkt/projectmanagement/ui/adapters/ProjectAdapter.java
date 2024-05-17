package com.samkt.projectmanagement.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samkt.projectmanagement.R;
import com.samkt.projectmanagement.data.model.response.Project;
import com.samkt.projectmanagement.ui.adapters.listeners.ProjectsListeners;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>{

    List<Project> projects;
    ProjectsListeners listener;

    public ProjectAdapter(List<Project> projects,ProjectsListeners listener){
        this.projects = projects;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item,parent,false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        Project project = projects.get(position);
        holder.tvProjectName.setText(project.getName());
        holder.deleteIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onDeleteClicked(project.getUuid());
                    }
                }
        );

        holder.editIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     listener.onEditClicked(project.getUuid(),project.getName(),project.getDescription());
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public static class ProjectViewHolder extends RecyclerView.ViewHolder{
        TextView tvProjectName;
        ImageView deleteIcon;
        ImageView editIcon;
        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProjectName = itemView.findViewById(R.id.tvProjectName);
            deleteIcon = itemView.findViewById(R.id.icDelete);
            editIcon = itemView.findViewById(R.id.icEdit);
        }
    }
}
