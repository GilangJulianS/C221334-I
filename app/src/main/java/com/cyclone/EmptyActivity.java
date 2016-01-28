package com.cyclone;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_empty);
		FacebookSdk.sdkInitialize(getApplicationContext());
		fragmentType = getIntent().getExtras().getInt("fragmentType");
		FragmentManager manager = getSupportFragmentManager();
		switch (fragmentType) {
			case FRAGMENT_GET_STARTED :
				manager.beginTransaction().replace(R.id.container, GetStartedFragment.newInstance()).commit();
				break;
			case FRAGMENT_TOC:
				manager.beginTransaction().replace(R.id.container, TocFragment.newInstance()).commit();
				break;
		}
	}
}
