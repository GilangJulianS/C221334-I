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
import android.widget.ImageView;

import com.cyclone.Utils.ServerUrl;
import com.cyclone.Utils.UtilUser;
import com.cyclone.loopback.UserClass;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ObjectCallback;

import java.util.Random;

public class SplashActivity extends Activity {

	private ImageView imgSplash;

	// daftar gambar buat di splash screen
	private int[] images = {R.drawable.background_login, R.drawable.wallpaper};
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

		// milih gambar random dari daftar gambar
		imgSplash = (ImageView) findViewById(R.id.img_splash);
		int counter = images.length;
		Random random = new Random();
		int selectedImage = random.nextInt(counter);
		imgSplash.setImageResource(images[selectedImage]);


		/*// play sound effect
		MediaPlayer mp = MediaPlayer.create(this, R.raw.sound_effect);
		mp.start();

		// buat start ke aplikasi setelah splash screen
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent i = new Intent(getApplicationContext(), EmptyActivity.class);
				i.putExtra("fragmentType", EmptyActivity.FRAGMENT_GET_STARTED);
				startActivity(i);
				finish();
			}
		}, 500*//*mp.getDuration()*//*); // pake mp.getDuration kalo mau dengerin sound effect sampe abis*/

		mContext = this;
		mActivity = this;
		splashActivity = new SplashActivity();
		mSettings = PreferenceManager.getDefaultSharedPreferences(this);
		//isLogin = mSettings.getBoolean(USER_PREF_LOGIN, false);

		connect();
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
					i.putExtra("fragmentType", EmptyActivity.FRAGMENT_GET_STARTED);
					mActivity.startActivity(i);
					mActivity.finish();
				}
			}

			@Override
			public void onError(Throwable t) {
				if (cntLogin == 0) {
					ServerUrl.API_URL = ServerUrl.API_URL_local;
					cntLogin++;
					Snackbar.make(view, "Connecting to local", Snackbar.LENGTH_SHORT).show();
					connect();
				} else {
					Snackbar.make(view, "Connection failed. Check your internet connection.", Snackbar.LENGTH_SHORT).show();
					Intent i = new Intent(mContext, EmptyActivity.class);
					i.putExtra("fragmentType", EmptyActivity.FRAGMENT_GET_STARTED);
					mActivity.startActivity(i);
					mActivity.finish();
				}

			}

		});
	}
}
