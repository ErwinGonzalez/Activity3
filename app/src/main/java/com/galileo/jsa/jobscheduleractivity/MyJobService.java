package com.galileo.jsa.jobscheduleractivity;

/**
 * Created by EEVGG on 08/06/2017.
 */

import android.app.DownloadManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.app.DownloadManager.Request;
import android.widget.Toast;

public class MyJobService extends JobService {
    static final String TAG = MyJobService.class.getSimpleName();
    UpdateAppsAsyncTask updateTask = new UpdateAppsAsyncTask();
    DownloadManager dm;
    @Override
    public boolean onStartJob(JobParameters params) {
        // Note: this is preformed on the main thread.
        Log.d(TAG, "onStartJob id=" + params.getJobId());
        updateTask.execute(params);
        return true;
    }

    // Stopping jobs if our job requires change.

    @Override
    public boolean onStopJob(JobParameters params) {
        // Note: return true to reschedule this job.
        Log.d(TAG, "onStopJob id=" + params.getJobId());
        return true;
    }
    public void startDownload(){
        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        Request request = new Request(
                Uri.parse("https://www.edx.org/sites/default/files/mediakit/image/thumb/edx_logo_200x200.png"));
        request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        dm.enqueue(request);
    }


    private class UpdateAppsAsyncTask extends AsyncTask<JobParameters, Void, JobParameters[]> {

        @Override
        protected JobParameters[] doInBackground(JobParameters... params) {
            startDownload();
            // Do updating and stopping logical here.
            Log.d(TAG, "Updating apps in the background");
            return params;
        }

        @Override
        protected void onPostExecute(JobParameters[] result) {
            for (JobParameters params : result) {
                Toast.makeText(getApplicationContext(),"DownloadFinished",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "finishing job with id=" + params.getJobId());
                jobFinished(params, false);
            }
        }
    }
}
