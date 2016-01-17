package com.cyclone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;

import com.cyclone.Utils.ServerUrl;
import com.cyclone.Utils.UtilUser;
import com.cyclone.loopback.UserClass;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ObjectCallback;

public class SplashActivity extends Activity {
	protected SharedPreferences mSettings;
	private final String USER_PREF_LOGIN = "com.cyclone.user_pref";
	private final String USER_PREF_EMAIL = "com.cyclone.user_pref";
	boolean isLogin = false;
	RestAdapter restAdapter ;
	UserClass.UserRepository userRepo ;
	Context mContext;
	Activity mActivity;
	View view;
	static SplashActivity splashActivity;

	int cntLogin = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		mContext = this;
		mActivity = this;
		splashActivity = new SplashActivity();
		mSettings = PreferenceManager.getDefaultSharedPreferences(this);
		//isLogin = mSettings.getBoolean(USER_PREF_LOGIN, false);

		connect();
		/*new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent i = new Intent(getApplicationContext(), EmptyActivity.class);
				startActivity(i);
				finish();
			}
		}, 3000);*/
	}

	@Override
	public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
		view = parent;
		return super.onCreateView(parent, name, context, attrs);
	}

	void connect(){
		restAdapter = new RestAdapter(this, ServerUrl.API_URL);
		userRepo = restAdapter.createRepository(UserClass.UserRepository.class);

		userRepo.findCurrentUser(new ObjectCallback<UserClass.User>() {
			@Override
			public void onSuccess(UserClass.User object) {
				if (object != null) {
					// logged in
					UtilUser.currentUser = object;
					System.out.println("LOGINED");
					System.out.println("Username : " + object.getUsername());

					Intent i = new Intent(mContext, DrawerActivity.class);
					mActivity.startActivity(i);
					mActivity.finish();

				} else {
					// anonymous user
					System.out.println("not LOGIN");
					Intent i = new Intent(mContext, EmptyActivity.class);
					mActivity.startActivity(i);
					mActivity.finish();
				}
			}

			@Override
			public void onError(Throwable t) {
				if(cntLogin == 0){
					ServerUrl.API_URL = ServerUrl.API_URL_local;
					cntLogin++;
					Snackbar.make(view, "Connecting to local", Snackbar.LENGTH_SHORT).show();
					connect();
				}
				else{
					Snackbar.make(view, "Connection failed. Check your internet connection.", Snackbar.LENGTH_SHORT).show();
					Intent i = new Intent(mContext, EmptyActivity.class);
					mActivity.startActivity(i);
					mActivity.finish();
				}

			}

		});
	}
}
