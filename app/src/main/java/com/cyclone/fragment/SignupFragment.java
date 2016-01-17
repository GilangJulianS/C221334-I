package com.cyclone.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cyclone.R;
import com.cyclone.Utils.ServerUrl;
import com.cyclone.loopback.repository.CreatUserRepository;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.remoting.adapters.Adapter;

/**
 * Created by gilang on 22/10/2015.
 */
public class SignupFragment extends Fragment {
	ProgressDialog progressDialog;
	private Button btnSignup;
	EditText editTextUsername, editTextFulName, editTextPassword, editTextEmail;
	int cntLogin = 0;
	public SignupFragment(){}

	public static SignupFragment newInstance(){
		SignupFragment fragment = new SignupFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_signup, parent, false);
		btnSignup = (Button) v.findViewById(R.id.btn_signup);
		editTextEmail = (EditText)v.findViewById(R.id.form_email);
		editTextFulName = (EditText)v.findViewById(R.id.form_full_name);
		editTextPassword = (EditText)v.findViewById(R.id.form_password);
		editTextUsername = (EditText)v.findViewById(R.id.form_user);



		btnSignup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/*FragmentManager manager = getActivity().getSupportFragmentManager();
				manager.popBackStack();*/


				progressDialog = new ProgressDialog(getActivity(),R.style.transparentProgesDialog);
				progressDialog.setCancelable(false);
				progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large_Inverse);
				progressDialog.show();
				register();
			}
		});
		return v;
	}

	private void register(){
		String male = "true";
		String email, fullname, user, password;

		if(editTextUsername.getText().length() < 1 || editTextUsername.getText().equals(" ")){
			progressDialog.dismiss();
			Snackbar.make(getView(),"Username is Empty", Snackbar.LENGTH_SHORT).show();
			return;
		}
		if(editTextEmail.getText().length() < 1 || editTextEmail.getText().equals(" ")){
			progressDialog.dismiss();
			Snackbar.make(getView(),"Email is Empty", Snackbar.LENGTH_SHORT).show();
			return;
		}
		if(editTextPassword.getText().length() < 1 || editTextPassword.getText().equals(" ")){
			progressDialog.dismiss();
			Snackbar.make(getView(),"Password is Empty", Snackbar.LENGTH_SHORT).show();
			return;
		}

		email = editTextEmail.getText().toString();
		fullname = editTextFulName.getText().toString();
		user = editTextUsername.getText().toString();
		password = editTextPassword.getText().toString();

		final RestAdapter restAdapter = new RestAdapter(getContext(), ServerUrl.API_URL);
		CreatUserRepository creatUserRepository = restAdapter.createRepository(CreatUserRepository.class);

		creatUserRepository.createContract();
		creatUserRepository.creat(user, email, "true", password, new Adapter.Callback() {
			@Override
			public void onSuccess(String response) {
				progressDialog.dismiss();
				FragmentManager manager = getActivity().getSupportFragmentManager();
				manager.popBackStack();
			}

			@Override
			public void onError(Throwable t) {
				if(cntLogin == 0){
					ServerUrl.API_URL = ServerUrl.API_URL_local;
					cntLogin++;
					Snackbar.make(getView(), "Connecting to local", Snackbar.LENGTH_SHORT).show();
					register();
				}
				else {
					progressDialog.dismiss();
					Snackbar.make(getView(), "Error SignUp", Snackbar.LENGTH_LONG).show();
				}
			}
		});

	}
}
