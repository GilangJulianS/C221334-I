package com.cyclone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.Utils.ServerUrl;
import com.cyclone.Utils.UtilUser;
import com.cyclone.loopback.UserClass;
import com.strongloop.android.loopback.AccessToken;
import com.strongloop.android.loopback.RestAdapter;

/**
 * Created by gilang on 09/10/2015.
 */
public class LoginFragment extends Fragment {

	private Button btnLogin;
	private Button btnForget;
	private ProgressBar progressBar;
	int cntLogin = 0;

	EditText editTextEmail, editTextPassword;

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

		editTextEmail = (EditText)v.findViewById(R.id.form_user);
		editTextPassword = (EditText)v.findViewById(R.id.form_password);

		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			progressBar.setVisibility(View.VISIBLE);
			btnLogin.setVisibility(View.GONE);
				login();
			/*new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent i = new Intent(getContext(), DrawerActivity.class);
					startActivity(i);
					getActivity().finish();
				}
			}, 2);*/
			}
		});

		return v;
	}

	private void login(){
		String email, password;

		if(editTextEmail.getText().length() < 1 || editTextEmail.getText().equals(" ")){
			Snackbar.make(getView(), "Email is Empty", Snackbar.LENGTH_SHORT).show();
			progressBar.setVisibility(View.GONE);
			btnLogin.setVisibility(View.VISIBLE);
			return;
		}
		if(editTextPassword.getText().length() < 1 || editTextEmail.getText().equals(" ")){
			Snackbar.make(getView(),"Password is Empty", Snackbar.LENGTH_SHORT).show();
			progressBar.setVisibility(View.GONE);
			btnLogin.setVisibility(View.VISIBLE);
			return;
		}

		email = editTextEmail.getText().toString();
		password = editTextPassword.getText().toString();

		final RestAdapter restAdapter = new RestAdapter(getContext(), ServerUrl.API_URL);
		final UserClass.UserRepository userRepo = restAdapter.createRepository(UserClass.UserRepository.class);
		userRepo.createUser(email,password);
		userRepo.loginUser(email, password,
				new UserClass.UserRepository.LoginCallback() {
					@Override
					public void onSuccess(AccessToken token, UserClass.User user) {
						// customer was logged in
						UtilUser.currentUser = user;
						UtilUser.currentToken = token;
						System.out.println("success");
						System.out.println("user : " + token.getUserId() );
						System.out.println("username : ");
						Intent i = new Intent(getContext(), DrawerActivity.class);
						startActivity(i);
						getActivity().finish();

					}

					@Override
					public void onError(Throwable t) {
						// login failed

						if(cntLogin == 0){
							ServerUrl.API_URL = ServerUrl.API_URL_local;
							cntLogin++;
							Snackbar.make(getView(), "Connecting to local", Snackbar.LENGTH_SHORT).show();
							login();
						}
						else {
							progressBar.setVisibility(View.GONE);
							btnLogin.setVisibility(View.VISIBLE);
							Snackbar.make(getView(), "Error Login", Snackbar.LENGTH_LONG).show();
							System.out.println("gagal " + t);
						}
					}
				}
		);
	}
}
