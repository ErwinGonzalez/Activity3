package com.galileo.jsa.jobscheduleractivity;

import android.app.job.JobInfo;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
                // you can use setOverrideDeadline to force the job execution after some time
                //.setOverrideDeadline(400L)
                .build();
    }

}