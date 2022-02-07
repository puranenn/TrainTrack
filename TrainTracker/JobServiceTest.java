package com.example.puranenn.traintracker;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by puranenn on 7.5.2019.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobServiceTest extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
