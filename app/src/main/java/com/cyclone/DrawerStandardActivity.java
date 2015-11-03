package com.cyclone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cyclone.fragment.ClubRadioFragment;
import com.cyclone.fragment.NotificationFragment;
import com.cyclone.fragment.PlayerFragment;
import com.cyclone.fragment.SettingsFragment;

public class DrawerStandardActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	public static final int LAYOUT_CLUB = 101;
	public static final int LAYOUT_NOTIFICATION = 102;
	public static final int LAYOUT_SETTINGS = 103;

	private View miniPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawer_standard);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

//		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//		fab.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//						.setAction("Action", null).show();
//			}
//		});

		miniPlayer = (View) findViewById(R.id.minimized_player);
		SharedPreferences pref = getSharedPreferences(getString(R.string.preference_key), Context
				.MODE_PRIVATE);
		if(pref.getInt("state", PlayerFragment.STATE_STOP) == PlayerFragment.STATE_STOP){
			miniPlayer.setVisibility(View.GONE);
		}else{
			miniPlayer.setVisibility(View.VISIBLE);
		}


		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);



		Intent caller = getIntent();
		if(caller != null) {
			String title = caller.getExtras().getString("title", "");
			int layout = caller.getExtras().getInt("layout", -1);
			if(!title.equals("")){
				getSupportActionBar().setTitle(title);
			}
			if(layout == LAYOUT_CLUB){
				FragmentManager manager = getSupportFragmentManager();
				manager.beginTransaction().replace(R.id.container, ClubRadioFragment.newInstance()).commit();
			}else if(layout == LAYOUT_NOTIFICATION){
				FragmentManager manager = getSupportFragmentManager();
				manager.beginTransaction().replace(R.id.container, NotificationFragment.newInstance()).commit();
			}else if(layout == LAYOUT_SETTINGS){
				FragmentManager manager = getSupportFragmentManager();
				manager.beginTransaction().replace(R.id.container, SettingsFragment.newInstance()).commit();
			}
		}

	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();


		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();
		Intent intent;
		switch (id){
			case R.id.nav_home:
				intent = new Intent(this, DrawerActivity.class);
				intent.putExtra("layout", DrawerActivity.LAYOUT_HOME);
				startActivity(intent);
				finish();
				break;
			case R.id.nav_klub:
				intent = new Intent(this, DrawerStandardActivity.class);
				intent.putExtra("title", "Imam Darto");
				intent.putExtra("layout", DrawerStandardActivity.LAYOUT_CLUB);
				startActivity(intent);
				break;
			case R.id.nav_profile:
				intent = new Intent(this, CollapseActivity.class);
				intent.putExtra("layout", CollapseActivity.LAYOUT_PERSON_PROFILE);
				intent.putExtra("title", "Dimas Danang");
				startActivity(intent);
				break;
			case R.id.nav_notification:
				intent = new Intent(this, DrawerStandardActivity.class);
				intent.putExtra("title", "Notifications");
				intent.putExtra("layout", DrawerStandardActivity.LAYOUT_NOTIFICATION);
				startActivity(intent);
				break;
			case R.id.nav_virtual_card:
				intent = new Intent(this, DrawerActivity.class);
				intent.putExtra("title", "Virtual Card");
				intent.putExtra("layout", DrawerActivity.LAYOUT_VIRTUAL_CARD);
				startActivity(intent);
				break;
			case R.id.nav_setting:
				intent = new Intent(this, DrawerStandardActivity.class);
				intent.putExtra("title", "Settings");
				intent.putExtra("layout", DrawerStandardActivity.LAYOUT_SETTINGS);
				startActivity(intent);
				break;
			case R.id.nav_player:
				intent = new Intent(this, CollapseActivity.class);
				intent.putExtra("title", "Player");
				intent.putExtra("layout", CollapseActivity.LAYOUT_PLAYER);
				startActivity(intent);
				break;
		}
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
