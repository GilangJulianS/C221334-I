package com.cyclone.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Created by gilang on 09/10/2015.
 */
public class LoginFragment extends Fragment implements
		GoogleApiClient.OnConnectionFailedListener,
		View.OnClickListener {
	CallbackManager callbackManager;
	LoginButton loginButton;
	private Button btnLogin;
	private Button btnForget;
	private ProgressBar progressBar;

	private Context context;

	private View view;
	private static final String TAG = "SignInActivity";
	private static final int RC_SIGN_IN = 9001;

	private GoogleApiClient mGoogleApiClient;
	private TextView mStatusTextView;
	private ProgressDialog mProgressDialog;

	private SharedPreferences mSettings;

	private int redirect = 0;

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

		context = getContext();

		mSettings = PreferenceManager.getDefaultSharedPreferences(context);
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

		// Views
		mStatusTextView = (TextView) v.findViewById(R.id.status);

		// Button listeners
		v.findViewById(R.id.sign_in_button).setOnClickListener(this);
		v.findViewById(R.id.sign_out_button).setOnClickListener(this);
		v.findViewById(R.id.disconnect_button).setOnClickListener(this);

		// [START configure_signin]
		// Configure sign-in to request the user's ID, email address, and basic
		// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestEmail()
				.build();
		// [END configure_signin]

		// [START build_client]

		mGoogleApiClient = new GoogleApiClient.Builder(context)
				.enableAutoManage((FragmentActivity) context /* FragmentActivity */, this /* OnConnectionFailedListener */)
				.addApi(Auth.GOOGLE_SIGN_IN_API, gso)
				.build();
		// [END build_client]

		// [START customize_button]
		// Customize sign-in button. The sign-in button can be displayed in
		// multiple sizes and color schemes. It can also be contextually
		// rendered based on the requested scopes. For example. a red button may
		// be displayed when Google+ scopes are requested, but a white button
		// may be displayed when only basic profile is requested. Try adding the
		// Scopes.PLUS_LOGIN scope to the GoogleSignInOptions to see the
		// difference.
		SignInButton signInButton = (SignInButton) v.findViewById(R.id.sign_in_button);
		signInButton.setSize(SignInButton.SIZE_STANDARD);
		signInButton.setScopes(gso.getScopeArray());
		// [END customize_button]
		view = v;

		loginButton = (LoginButton) view.findViewById(R.id.login_button);
		loginButton.setReadPermissions("public_profile");
		// If using in a fragment
		loginButton.setFragment(this);
		// Other app specific specialization

		// Callback registration
		loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult loginResult) {
				// App code
				System.out.println("+++++++++++++++ atas");
				//System.out.println(loginResult.getAccessToken().getUserId());
				Profile profile;

				profile = Profile.getCurrentProfile();

				System.out.println(profile.getName());
			}

			@Override
			public void onCancel() {
				// App code
				System.out.println("+++++++++++++++ atas cancel");
			}

			@Override
			public void onError(FacebookException exception) {
				// App code
				System.out.println("+++++++++++++++ atas error");
			}
		});

		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FacebookSdk.sdkInitialize(this.getContext());

		callbackManager = CallbackManager.Factory.create();

		LoginManager.getInstance().registerCallback(callbackManager,
				new FacebookCallback<LoginResult>() {
					@Override
					public void onSuccess(LoginResult loginResult) {
						// App code
						System.out.println("+++++++++++++++ bawah");
					}

					@Override
					public void onCancel() {
						// App code
						System.out.println("+++++++++++++++ bawah cancel");
					}

					@Override
					public void onError(FacebookException exception) {
						// App code
						System.out.println("+++++++++++++++ bawah error");
					}
				});
	}

	@Override
	public void onStart() {
		super.onStart();

		OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
		if (opr.isDone()) {
			// If the user's cached credentials are valid, the OptionalPendingResult will be "done"
			// and the GoogleSignInResult will be available instantly.
			Log.d(TAG, "Got cached sign-in");
			GoogleSignInResult result = opr.get();
			handleSignInResult(result);
		} else {
			// If the user has not previously signed in on this device or the sign-in has expired,
			// this asynchronous branch will attempt to sign in the user silently.  Cross-device
			// single sign-on will occur in this branch.
			showProgressDialog();
			opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
				@Override
				public void onResult(GoogleSignInResult googleSignInResult) {
					hideProgressDialog();
					handleSignInResult(googleSignInResult);
				}
			});
		}
	}

	// [START onActivityResult]
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
		if (requestCode == RC_SIGN_IN) {
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			handleSignInResult(result);
		}

		callbackManager.onActivityResult(requestCode, resultCode, data);
	}
	// [END onActivityResult]

	// [START handleSignInResult]
	private void handleSignInResult(GoogleSignInResult result) {
		Log.d(TAG, "handleSignInResult:" + result.isSuccess());
		if (result.isSuccess()) {
			// Signed in successfully, show authenticated UI.
			GoogleSignInAccount acct = result.getSignInAccount();
			SharedPreferences.Editor editor = mSettings.edit();
			editor.putBoolean(DrawerActivity.PREF_GOOGLE, true);
			editor.putString(DrawerActivity.PREF_GOOGLE_USR, acct.getDisplayName());
			editor.putString(DrawerActivity.PREF_GOOGLE_EMAIL, acct.getEmail());
			editor.commit();

			mStatusTextView.setText(acct.getDisplayName());
			updateUI(true);

			if(redirect == 1){
				Intent intent = new Intent(context, DrawerActivity.class);
				intent.putExtra("parent", true);
				intent.putExtra("layout", MasterActivity.LAYOUT_HOME);
				intent.putExtra("activity", R.layout.activity_drawer);
				startActivity(intent);
			}

		} else {
			// Signed out, show unauthenticated UI.
			updateUI(false);
		}
	}
	// [END handleSignInResult]

	// [START signIn]
	private void signIn() {

		Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
		startActivityForResult(signInIntent, RC_SIGN_IN);

	}
	// [END signIn]

	// [START signOut]
	private void signOut() {
		Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
				new ResultCallback<Status>() {
					@Override
					public void onResult(Status status) {
						// [START_EXCLUDE]
						updateUI(false);
						// [END_EXCLUDE]

						SharedPreferences.Editor editor = mSettings.edit();
						editor.putBoolean(DrawerActivity.PREF_GOOGLE, false);
						editor.putString(DrawerActivity.PREF_GOOGLE_USR, "");
						editor.putString(DrawerActivity.PREF_GOOGLE_EMAIL, "");
						editor.commit();
					}
				});
	}
	// [END signOut]

	// [START revokeAccess]
	private void revokeAccess() {
		Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
				new ResultCallback<Status>() {
					@Override
					public void onResult(Status status) {
						// [START_EXCLUDE]
						updateUI(false);
						// [END_EXCLUDE]
					}
				});
	}
	// [END revokeAccess]

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		// An unresolvable error has occurred and Google APIs (including Sign-In) will not
		// be available.
		Log.d(TAG, "onConnectionFailed:" + connectionResult);
	}

	private void showProgressDialog() {
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(context);
			mProgressDialog.setMessage(getString(R.string.loading));
			mProgressDialog.setIndeterminate(true);
		}

		mProgressDialog.show();
	}

	private void hideProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.hide();
		}
	}

	private void updateUI(boolean signedIn) {
		if (signedIn) {
			view.findViewById(R.id.sign_in_button).setVisibility(View.GONE);
			view.findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
		} else {
			mStatusTextView.setText("Sign Out");

			view.findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
			view.findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.sign_in_button:
				redirect = 1;
				signIn();
				break;
			case R.id.sign_out_button:
				signOut();
				break;
			case R.id.disconnect_button:
				revokeAccess();
				break;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		// Logs 'install' and 'app activate' App Events.
		AppEventsLogger.activateApp(this.context);
	}

	@Override
	public void onPause() {
		super.onPause();
		// Logs 'app deactivate' App Event.
		//AppEventsLogger.deactivateApp(this.context);
	}


}

