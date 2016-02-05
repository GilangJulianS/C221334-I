package com.cyclone.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.DrawerActivity;
import com.cyclone.EmptyActivity;
import com.cyclone.R;
import com.cyclone.Utils.ServerUrl;
import com.cyclone.Utils.UtilUser;
import com.cyclone.loopback.UserClass;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.VoidCallback;

/**
 * Created by gilang on 26/10/2015.
 */
public class AccountSettingFragment extends Fragment {

	private static int LOAD_IMAGE = 0;
	private ImageView imgUser;
	static AccountSettingFragment fragment;
	Context mContext;
	Activity mActivity;
	ViewGroup logout;
	EditText formUsername;
	TextView txtName;

	public AccountSettingFragment(){}

	public static AccountSettingFragment newInstance(){
		fragment = new AccountSettingFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this.getContext();
		mActivity = this.getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_setting_account, parent, false);

		formUsername = (EditText) v.findViewById(R.id.txt_username);
		txtName = (TextView) v.findViewById(R.id.txt_name);
		imgUser = (ImageView) v.findViewById(R.id.img_user);
		logout = (ViewGroup) v.findViewById(R.id.logout);

		txtName.setText(UtilUser.currentUser.getUsername());
		formUsername.setText("@" + UtilUser.currentUser.getUsername());

		imgUser.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images
						.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, LOAD_IMAGE);
			}
		});

		logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RestAdapter restAdapter = new RestAdapter(getContext(), ServerUrl.API_URL);
				UserClass.UserRepository userRepo = restAdapter.createRepository(UserClass.UserRepository.class);

				userRepo.logout(new VoidCallback() {
					@Override
					public void onSuccess() {
						Intent i = new Intent(mContext, EmptyActivity.class);
						i.putExtra("fragmentType", EmptyActivity.FRAGMENT_GET_STARTED);
						mContext.startActivity(i);
						DrawerActivity.getmActivity().finish();
					}

					@Override
					public void onError(Throwable t) {
						System.out.println("error : " + t);
					}
				});
			}
		});

		return v;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == LOAD_IMAGE && resultCode == Activity.RESULT_OK && data != null){
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getActivity().getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
			imgUser.setImageBitmap(bitmap);
		}
	}
}
