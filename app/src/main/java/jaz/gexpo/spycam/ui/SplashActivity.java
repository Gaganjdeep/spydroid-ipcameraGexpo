package jaz.gexpo.spycam.ui;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import jaz.gexpo.spycam.R;

public class SplashActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            startUp();

        }
        else {
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    startActivity(new Intent(SplashActivity.this, SpydroidActivity.class));
                }
            }, 1500);
        }
    }

    private void startUp()
    {
        if (ContextCompat.checkSelfPermission(SplashActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


// Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))

            {

// Show an expanation to the user *asynchronously* -- don't block
// this thread waiting for the user's response! After the user
// sees the explanation, try again to request the permission.
                final AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                builder.setMessage("G-Expo Spycam wants to access your camera & network.")
                        .setCancelable(false)
                        .setPositiveButton("Allow", new DialogInterface.OnClickListener()
                        {
                            public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id)
                            {
                                ActivityCompat.requestPermissions(SplashActivity.this,
                                        new String[]{Manifest.permission.ACCESS_NETWORK_STATE,
                                                Manifest.permission.ACCESS_WIFI_STATE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                Manifest.permission.RECORD_AUDIO},
                                        11);

                            }
                        })
                        .setNegativeButton("Exit", new DialogInterface.OnClickListener()
                        {
                            public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id)
                            {
                                dialog.cancel();
                                finish();
                            }
                        });
                final AlertDialog alert = builder.create();
                alert.show();

            }
            else {
// No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(SplashActivity.this,
                        new String[]{Manifest.permission.ACCESS_NETWORK_STATE,
                                Manifest.permission.ACCESS_WIFI_STATE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.RECORD_AUDIO},
                        11);

            }

        }
        else {
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    startActivity(new Intent(SplashActivity.this, SpydroidActivity.class));
                }
            }, 1500);

        }
    }

}
