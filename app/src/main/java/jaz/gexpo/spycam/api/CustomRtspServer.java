package jaz.gexpo.spycam.api;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;

import jaz.gexpo.streaming.SessionBuilder;
import jaz.gexpo.streaming.rtsp.RtspServer;

public class CustomRtspServer extends RtspServer
{
    public CustomRtspServer()
    {
        super();
        // RTSP server disabled by default
        mEnabled = false;
    }

    private WindowManager                      windowManager;
    private jaz.gexpo.streaming.gl.SurfaceView surfaceView;

    @Override
    public void onCreate()
    {
        super.onCreate();

        windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        surfaceView = new jaz.gexpo.streaming.gl.SurfaceView(CustomRtspServer.this);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                1, 1,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT
        );
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        windowManager.addView(surfaceView, layoutParams);

        SessionBuilder.getInstance().setSurfaceView(surfaceView);
    }

    @Override
    public void onDestroy()
    {
        windowManager.removeView(surfaceView);
    }

}

