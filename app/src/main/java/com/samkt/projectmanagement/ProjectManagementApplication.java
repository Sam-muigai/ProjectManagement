package com.samkt.projectmanagement;

import android.app.Application;

import timber.log.Timber;

public class ProjectManagementApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
