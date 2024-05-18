package com.samkt.projectmanagement.ui.adapters.listeners;

public interface ProjectsListeners {
    void onEditClicked(String id,String name,String description);
    void onDeleteClicked(String id);
    void onProjectClicked(String id,String name);
}
