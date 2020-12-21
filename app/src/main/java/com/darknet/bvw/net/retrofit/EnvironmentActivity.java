package com.darknet.bvw.net.retrofit;

import android.app.Application;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.darknet.bvw.BuildConfig;
import com.darknet.bvw.R;


public class EnvironmentActivity extends AppCompatActivity {

    private String isCurrentEnv = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!isCurrentEnv.equalsIgnoreCase("")) {
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            finish();
        }
    }

    public static boolean isOfficialEnvironment(Application application) {
        return "line".equalsIgnoreCase(BuildConfig.WORK);
    }
}
