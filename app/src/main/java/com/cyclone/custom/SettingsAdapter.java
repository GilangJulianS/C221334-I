package com.cyclone.custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.DrawerActivity;
import com.cyclone.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 26/10/2015.
 */
public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

	public List<String> menus;
	public List<Integer> icons;
	private Activity activity;
	private Context context;

	public SettingsAdapter(Activity activity){
		this.activity = activity;
		this.context = (Context) activity;
		menus = new ArrayList<>();
		menus.add("App");
		menus.add("Account");
		menus.add("Notification");
		menus.add("Help");
		menus.add("Rate this App");
		menus.add("About");
		icons = new ArrayList<>();
		icons.add(R.drawable.ic_settings_black_48dp);
		icons.add(R.drawable.ic_person_black_48dp);
		icons.add(R.drawable.ic_notifications_black_48dp);
		icons.add(R.drawable.ic_help_black_48dp);
		icons.add(R.drawable.ic_stars_black_48dp);
		icons.add(R.drawable.ic_info_black_48dp);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(context).inflate(R.layout.card_settings, parent, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		final int pos = position;
		holder.imgIcon.setImageResource(icons.get(position));
		holder.txtMenu.setText(menus.get(position));
		holder.card.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(context, DrawerActivity.class);
				i.putExtra("title", getTitle(pos));
				i.putExtra("fragmentType", getLayout(pos));
				activity.startActivity(i);
			}
		});
	}

	@Override
	public int getItemCount() {
		return menus.size();
	}

	public int getLayout(int position){
		switch (position){
			case 0: return DrawerActivity.FRAGMENT_APP_SETTINGS;
			case 1: return DrawerActivity.FRAGMENT_ACCOUNT_SETTINGS;
			case 2: return DrawerActivity.FRAGMENT_NOTIFICATION_SETTINGS;
			case 5: return DrawerActivity.FRAGMENT_ABOUT;
		}
		return DrawerActivity.FRAGMENT_ACCOUNT_SETTINGS;
	}

	public String getTitle(int position){
		switch (position){
			case 0: return "App Settings";
			case 1: return "Account Settings";
			case 2: return "Notification Settings";
			case 5: return "About";
		}
		return "Account Settings";
	}

	public class ViewHolder extends RecyclerView.ViewHolder{

		public ImageView imgIcon;
		public TextView txtMenu;
		public ViewGroup card;

		public ViewHolder(View v) {
			super(v);
			imgIcon = (ImageView) v.findViewById(R.id.img_icon);
			txtMenu = (TextView) v.findViewById(R.id.txt_menu);
			card = (ViewGroup) v.findViewById(R.id.card_setting);
		}
	}
}
