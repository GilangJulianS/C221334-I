package com.cyclone.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.Utils.ServerUrl;
import com.cyclone.Utils.UtilUser;
import com.cyclone.loopback.UserClass;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
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

	CallbackManager callbackManager;
	LoginButton loginButton ;
	static LoginFragment fragment;

	public LoginFragment(){}

	public static LoginFragment newInstance(){
		fragment = new LoginFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_login, parent, false);

		//callbackManager = CallbackManager.Factory.create();

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
			}
		});

		btnForget.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Forget Password Clicked", Toast.LENGTH_SHORT).show();
			}
		});

		/*loginButton = (LoginButton) v.findViewById(R.id.login_button);
		loginButton.setReadPermissions("user_friends");
		// If using in a fragment
		loginButton.setFragment(this);
		LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));
		loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult loginResult) {
				Profile profile = Profile.getCurrentProfile();
			}

			@Override
			public void onCancel() {

			}

			@Override
			public void onError(FacebookException error) {

			}
		});*/

		return v;
	}

	private void hidekeyboard(){
		View view = getActivity().getCurrentFocus();
		if (view != null) {
			InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	private void login(){
		hidekeyboard();

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
		userRepo.createUser(email, password);
		userRepo.loginUser(email, password,
				new UserClass.UserRepository.LoginCallback() {
					@Override
					public void onSuccess(AccessToken token, UserClass.User user) {
						// customer was logged in
						UtilUser.currentUser = user;
						UtilUser.currentToken = token;
						System.out.println("success");
						System.out.println("user : " + token.getUserId());
						System.out.println("username : ");
						Intent i = new Intent(getContext(), DrawerActivity.class);
						startActivity(i);
						getActivity().finish();

					}

					@Override
					public void onError(Throwable t) {
						// login failed

						if (cntLogin == 0) {
							ServerUrl.API_URL = ServerUrl.API_URL_local;
							cntLogin++;
							Snackbar.make(getView(), "Connecting to local", Snackbar.LENGTH_SHORT).show();
							login();
						} else {
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
