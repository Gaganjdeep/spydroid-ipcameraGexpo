
package jaz.gexpo.spycam.ui;

import jaz.gexpo.http.TinyHttpServer;
import jaz.gexpo.spycam.R;
import jaz.gexpo.spycam.api.CustomHttpServer;
import jaz.gexpo.spycam.api.CustomRtspServer;
import jaz.gexpo.streaming.SessionBuilder;
import jaz.gexpo.streaming.gl.SurfaceView;
import jaz.gexpo.streaming.rtsp.RtspServer;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PreviewFragment extends Fragment {

	public final static String TAG = "PreviewFragment";

	private SurfaceView mSurfaceView;
	private TextView mTextView;
    private CustomHttpServer mHttpServer;
    private RtspServer mRtspServer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}



	@Override
	public void onDestroy()
	{
		getActivity().unbindService(mHttpServiceConnection);
		getActivity().unbindService(mRtspServiceConnection);
		super.onDestroy();
	}

	@Override
    public void onResume() {
    	super.onResume();

    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.preview,container,false);

		getActivity().bindService(new Intent(getActivity(),CustomHttpServer.class), mHttpServiceConnection, Context.BIND_AUTO_CREATE);
		getActivity().bindService(new Intent(getActivity(),CustomRtspServer.class), mRtspServiceConnection, Context.BIND_AUTO_CREATE);

		mTextView = (TextView)rootView.findViewById(R.id.tooltip);
		
		if (((SpydroidActivity)getActivity()).device == ((SpydroidActivity)getActivity()).TABLET) {

			mSurfaceView = (SurfaceView)rootView.findViewById(R.id.tablet_camera_view);
			SessionBuilder.getInstance().setSurfaceView(mSurfaceView);

		} 
		
		return rootView;
	}
	
	public void update() {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (mTextView != null) {
					if ((mRtspServer != null && mRtspServer.isStreaming()) || (mHttpServer != null && mHttpServer.isStreaming()))
						mTextView.setVisibility(View.INVISIBLE);
					else 
						mTextView.setVisibility(View.VISIBLE);
				}
			}
		});
	}
	
    private final ServiceConnection mRtspServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mRtspServer = (RtspServer) ((RtspServer.LocalBinder)service).getService();
			update();
		}
		@Override
		public void onServiceDisconnected(ComponentName name) {}
	};
    
    private final ServiceConnection mHttpServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mHttpServer = (CustomHttpServer) ((TinyHttpServer.LocalBinder)service).getService();
			update();
		}
		@Override
		public void onServiceDisconnected(ComponentName name) {}
	};
	
}
