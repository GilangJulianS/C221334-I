package com.cyclone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cyclone.R;

/**
 * Created by gilang on 22/10/2015.
 */
public class SignupFragment extends Fragment {

	private Button btnSignup;

	public SignupFragment(){}

	public static SignupFragment newInstance(){
		SignupFragment fragment = new SignupFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_signup, parent, false);
		btnSignup = (Button) v.findViewById(R.id.btn_signup);
		btnSignup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager manager = getActivity().getSupportFragmentManager();
				manager.popBackStack();
			}
		});
		return v;
	}
}
