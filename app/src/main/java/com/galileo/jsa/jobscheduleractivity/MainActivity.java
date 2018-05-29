package com.galileo.jsa.jobscheduleractivity;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int JOB_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_schedule_job).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleJob();
            }
        });
    }

    private void scheduleJob() {
        ComponentName serviceName = new ComponentName(this, MyJobService.class);
        //Job needs at least one parameter, otherwise it'll crash
        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, serviceName)
                .setRequiresDeviceIdle(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresCharging(true)
                .setOverrideDeadline(400L)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result = scheduler.schedule(jobInfo);
        if (result == JobScheduler.RESULT_SUCCESS) {
            Toast.makeText(this, R.string.job_scheduled_successfully, Toast.LENGTH_LONG).show();
        }
    }
}