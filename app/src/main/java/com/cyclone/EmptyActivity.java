package com.cyclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.cyclone.fragment.GetStartedFragment;
import com.cyclone.fragment.TocFragment;
import com.facebook.FacebookSdk;

public class EmptyActivity extends AppCompatActivity {

	public static final int FRAGMENT_GET_STARTED = 100;
	public static final int FRAGMENT_TOC = 101;
	private int fragmentType;
	Intent intent;

	private static EmptyActivity emptyActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_empty);
		FacebookSdk.sdkInitialize(getApplicationContext());
		fragmentType = getIntent().getExtras().getInt("fragmentType");
		intent = getIntent();
		FragmentManager manager = getSupportFragmentManager();
		switch (fragmentType) {
			case FRAGMENT_GET_STARTED :
				manager.beginTransaction().replace(R.id.container, GetStartedFragment.newInstance(getIntent().getExtras().getBoolean("login", true))).commit();
				break;
			case FRAGMENT_TOC:
				manager.beginTransaction().replace(R.id.container, TocFragment.newInstance()).commit();
				break;
		}

		emptyActivity = this;
	}

	public static EmptyActivity getEmptyActivity() {
		return emptyActivity;
	}

	public void clearPreferences() {
		try {
			// clearing app data
			Runtime runtime = Runtime.getRuntime();
			runtime.exec("pm clear com.cyclone");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
