package com.cyclone;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.cyclone.fragment.GetStartedFragment;
import com.cyclone.fragment.LoginFragment;

public class EmptyActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_empty);

		FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction().replace(R.id.container, GetStartedFragment.newInstance()).commit();
	}
}
