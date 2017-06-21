
package jaz.gexpo.spycam.ui;

import jaz.gexpo.spycam.R;
import jaz.gexpo.spycam.SpydroidApplication;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class AboutFragment extends Fragment {

	private Button mButtonVisit;
	private Button mButtonRate;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.about,container,false);

		mButtonVisit = (Button)rootView.findViewById(R.id.visit);
		mButtonRate = (Button)rootView.findViewById(R.id.rate);

		mButtonVisit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://code.google.com/p/spycam-ipcamera/"));
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
				startActivity(intent);
			}
		});

		mButtonRate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String appPackageName=SpydroidApplication.getInstance().getApplicationContext().getPackageName();
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appPackageName));
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
				startActivity(intent);
			}
		});



		return rootView ;
	}

}
