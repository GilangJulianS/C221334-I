package com.cyclone.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cyclone.EmptyActivity;
import com.cyclone.R;

/**
 * Created by gilang on 09/10/2015.
 */
public class GetStartedFragment extends Fragment{

	private Button btnLogin;
	private Button btnSignup;
	boolean isLogin;

	public GetStartedFragment(){}

	public static GetStartedFragment newInstance(boolean isLogin) {
		GetStartedFragment fragment = new GetStartedFragment();
		fragment.isLogin = isLogin;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_get_started, parent, false);
		btnLogin = (Button) v.findViewById(R.id.btn_login);
		btnSignup = (Button) v.findViewById(R.id.btn_signup);
		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager manager = getActivity().getSupportFragmentManager();
				manager.beginTransaction().replace(R.id.container, LoginFragment.newInstance())
						.addToBackStack(null).commit();
			}
		});
		btnSignup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager manager = getActivity().getSupportFragmentManager();
				manager.beginTransaction().replace(R.id.container, SignupFragment.newInstance())
						.addToBackStack(null).commit();
			}
		});

		if (!isLogin) {
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					EmptyActivity.getEmptyActivity().clearPreferences();
				}
			}, 1000);

		}

		return v;
	}
}
