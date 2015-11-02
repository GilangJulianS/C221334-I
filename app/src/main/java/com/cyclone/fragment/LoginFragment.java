package com.cyclone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.cyclone.DrawerActivity;
import com.cyclone.R;

/**
 * Created by gilang on 09/10/2015.
 */
public class LoginFragment extends Fragment {

	private Button btnLogin;
	private Button btnForget;
	private ProgressBar progressBar;

	public LoginFragment(){}

	public static LoginFragment newInstance(){
		LoginFragment fragment = new LoginFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_login, parent, false);
		btnLogin = (Button) v.findViewById(R.id.btn_login);
		btnForget = (Button) v.findViewById(R.id.btn_forget_password);
		progressBar = (ProgressBar) v.findViewById(R.id.progressbar);

		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			progressBar.setVisibility(View.VISIBLE);
			btnLogin.setVisibility(View.GONE);
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent i = new Intent(getContext(), DrawerActivity.class);
					startActivity(i);
					getActivity().finish();
				}
			}, 2);
			}
		});

		return v;
	}
}
