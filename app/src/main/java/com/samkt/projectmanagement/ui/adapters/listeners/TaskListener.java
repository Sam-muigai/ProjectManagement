package com.samkt.projectmanagement.ui.adapters.listeners;

public interface TaskListener {
    void onDeleteClicked(String id);
    void onEditClicked(String id,String taskName,String taskDeadline);
}
